package com.routing.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class ShardDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return ShardContextHolder.get();
	}
}