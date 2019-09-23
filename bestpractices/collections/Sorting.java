package bestpractices.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sorting {
	
	public static void main(String...args) {
		Map<String, Integer> values = new HashMap();
		values.put("a",	(int) (100 * Math.random()));
		values.put("b", (int) (100 * Math.random()));
		values.put("c", (int) (100 * Math.random()));
		values.put("d", (int) (100 * Math.random()));
		values.put("e", (int) (100 * Math.random()));
		
		Map.Entry<String, Integer>[] arry = new Map.Entry[values.size()];
		values.entrySet().toArray(arry);
		
		for (String s : values.keySet()) {
			System.out.println(s + " : " + values.get(s));
		}
		
		System.out.println("==========");
	
		//Substitute Integer for {Object}
		Arrays.sort(arry, new Comparator<Map.Entry<String, Integer>> () {
			public int compare(Map.Entry a, Map.Entry b) {
				int aa = a.getValue(), bb = b.getValue();
				if (aa > bb) {
					return 1;
				}
				return aa == bb ? 0 : -1;
			}
		});
		
		for (Map.Entry<String, Integer> i : arry) {
			System.out.println(i.getKey() + " : " + i.getValue());
		}
	}
	
}
