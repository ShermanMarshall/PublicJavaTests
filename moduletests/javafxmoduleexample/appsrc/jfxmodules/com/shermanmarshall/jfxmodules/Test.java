package com.shermanmarshall.jfxmodules;

import java.io.File;

public class Test {
	public static String baseURL() {
		String str = "file://" + new File("appsrc/webapp/test.html")
					.getAbsolutePath();
		System.out.println(str);
		return str;
	}
}
