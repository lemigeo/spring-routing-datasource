package com.routing.datasource;

public enum DatabaseRole {
	Master(0),
	Slave1(1),
	Slave2(2);
	
	private int value;
	private DatabaseRole(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
}