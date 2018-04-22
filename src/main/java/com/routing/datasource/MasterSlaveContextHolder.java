package com.routing.datasource;

public class MasterSlaveContextHolder {
	
	private static final ThreadLocal<DatabaseRole> contextHolder = new ThreadLocal<DatabaseRole>();
	
	public static void set(DatabaseRole role) {      
		contextHolder.set(role);
	}

	public static DatabaseRole get() {
		return (DatabaseRole) contextHolder.get();
	}

	public static void clear() {
		contextHolder.remove();
	}
}