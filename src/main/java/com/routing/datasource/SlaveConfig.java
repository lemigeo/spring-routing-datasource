package com.routing.datasource;

import com.zaxxer.hikari.HikariConfig;

public class SlaveConfig extends HikariConfig {
	
	private int key;
	private HikariConfig hikari;
	
	public SlaveConfig() {
		super();
	}
	
	public int getKey() {
		return key;
	}
	
	public void setKey(int key) {
		this.key = key;
	}

	public HikariConfig getHikari() {
		return hikari;
	}

	public void setHikari(HikariConfig hikari) {
		this.hikari = hikari;
	}
}