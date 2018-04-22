package com.routing.datasource;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "datasource")
public class ShardProperty {
	
	private List<ShardConfig> shard;

	public ShardProperty() {
	}
	
	public List<ShardConfig> getShard() {
		return shard;
	}

	public void setShard(List<ShardConfig> shard) {
		this.shard = shard;
	}
}