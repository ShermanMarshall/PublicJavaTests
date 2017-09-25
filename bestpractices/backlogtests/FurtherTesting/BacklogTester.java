package com.shermanmarshall.backlogtest.servlets;

/* Note
 * deployment of this file will be in accord with your test environment
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/root")
public class BacklogTester {
	static File root = new File("BacklogTest/");
	static {
		if (!root.isDirectory()) {
			if (!root.exists()) {
				root.mkdir();
			} else {
				root.delete();
				root.mkdir();
			}
		}		
	}
	static ArrayList<String> paths = new ArrayList();
	
	@GET
	@Path("/endpoint/{path}")
	public String path(@PathParam("path") String path) {
		new File(root.getPath() + "/" + path).createNewFile();
		return path;
	}
}
