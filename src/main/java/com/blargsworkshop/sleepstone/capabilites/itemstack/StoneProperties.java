package com.blargsworkshop.sleepstone.capabilites.itemstack;

import java.util.UUID;

public class StoneProperties implements IStoneProperties {

	private String uniqueId = "";
	
	public StoneProperties() {
		uniqueId = UUID.randomUUID().toString();
	}

	@Override
	public String getUniqueId() {
		return uniqueId;
	}
	
	@Override
	public void setUniqueId(String uuid) {
		uniqueId = uuid;
	}

}
