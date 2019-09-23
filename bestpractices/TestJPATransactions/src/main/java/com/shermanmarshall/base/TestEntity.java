package com.shermanmarshall.base;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Persistence;

@Entity(name="TestEntity")
public class TestEntity {
	
	static int numRecords = 100000;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="one")
	private int one;
	
	@Column(name="two")
	private String two;

	public static void populate(String[] args) {
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("testdb");			
			EntityManager em = emf.createEntityManager();
			
			em.getTransaction().begin();
			
			for (int x = 0; x < numRecords; x++) {
				
				TestEntity p = new TestEntity();
				p.two = UUID.randomUUID().toString();
				p.one = 0;
				
				em.persist(p);
			}
			
			em.getTransaction().commit();
			
			em.close();
			emf.close();
		} catch (Exception ioe) {
			System.out.println(ioe);
		}
	}

	public static void update100000Slow(String...args) {
		
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("testdb");			
			EntityManager em = emf.createEntityManager();
			
			long time = System.currentTimeMillis();
			
			for (int x = 1; x < numRecords; x++) {
				
				if (x % 1000 == 0) {
					System.out.print(x + " : iteration = ");
					System.out.println(((double)(System.currentTimeMillis() - time)) / 1000.0);
				}
				
				em.getTransaction().begin();
				
				TestEntity p;// = new TestEntity();
				//p.one = x;
				
				p = em.find(TestEntity.class, x);
				p.two = UUID.randomUUID().toString();
				
				em.persist(p);
				
				em.getTransaction().commit();
			}
			
			System.out.println(((double)(System.currentTimeMillis() - time)) / 1000.0);
			//Time: 
			em.close();
			emf.close();
		} catch (Exception ioe) {
			System.out.println(ioe);
		}
		
	}
	
	public static void main (String...args) {
		Map<Integer, String>  uniqueMap = new HashMap<Integer, String>();
		
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("testdb");			
			EntityManager em = emf.createEntityManager();
			
			long time = System.currentTimeMillis();
			
			em.getTransaction().begin();
			
			for (int x = 1; x < numRecords; x++) {
				
				if (x % 10000000 == 0) {
					System.out.print(x + " : iteration = ");
					System.out.println(((double)(System.currentTimeMillis() - time)) / 1000.0);
				}
				
				TestEntity p;// = new TestEntity();
				//p.one = x;
				
				p = em.find(TestEntity.class, x);
				
				String newUUID = UUID.randomUUID().toString();
				uniqueMap.put(p.one, p.two + ":" + newUUID);
				
				p.two = newUUID;
				
				em.persist(p);
			}
			
			em.getTransaction().commit();
			
			System.out.println(((double)(System.currentTimeMillis() - time)) / 1000.0);
			
			boolean isUnique = true;
			
			for (int x = 1; x < numRecords; x++) {
				TestEntity p = em.find(TestEntity.class, x);
				
				String content = uniqueMap.get(x);
				String[] beforeAfter = content.split(":");
				
				if (p.two.equals(beforeAfter[0])) {
					System.out.println("Entity: " + p.one + " unchanged");
				}
				
				if (!p.two.equals(beforeAfter[1])) {
					System.out.println("Entity: " + p.one + " does not have registered id");
				}
			}
			
			//Time: 
			em.close();
			emf.close();
		} catch (Exception ioe) {
			System.out.println(ioe);
		}
	}
}
