package com.routing.datasource;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "datasource")
public class MasterSlaveProperty {
	
	private MasterConfig master;
	private List<SlaveConfig> slave;

	public MasterSlaveProperty() {
	}
	
	public MasterConfig getMaster() {
		return master;
	}
	
	public void setMaster(MasterConfig master) {
		this.master = master;
	}
	
	public List<SlaveConfig> getSlave() {
		return slave;
	}

	public void setSlave(List<SlaveConfig> slave) {
		this.slave = slave;
	}
}