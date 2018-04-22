package com.routing.datasource;

import com.zaxxer.hikari.HikariConfig;

public class ShardConfig extends HikariConfig {
	
	private String key;
	private HikariConfig hikari;
	
	public ShardConfig() {
		super();
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}

	public HikariConfig getHikari() {
		return hikari;
	}

	public void setHikari(HikariConfig hikari) {
		this.hikari = hikari;
	}
}