package com.routing.datasource;

public class ShardContextHolder {
	
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
	
	public static void set(String key) {      
		contextHolder.set(key);
	}

	public static String get() {
		return (String) contextHolder.get();
	}

	public static void clear() {
		contextHolder.remove();
	}
}