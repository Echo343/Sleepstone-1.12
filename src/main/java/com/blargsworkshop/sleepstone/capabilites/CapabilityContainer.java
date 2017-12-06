package com.blargsworkshop.sleepstone.capabilites;

import net.minecraftforge.common.capabilities.Capability;

/**
 * I couldn't get this class to work.  Don't use it.
 *
 * @param <T>
 */
@Deprecated
public class CapabilityContainer <T> {
	
	
	private Class<T> type;
	private Capability.IStorage<T> storage;
	private Class<? extends T> implementation;

	public CapabilityContainer(Class<T> type, Capability.IStorage<T> storage, final Class<? extends T> implementation) {
		this.type = type;
		this.storage = storage;
		this.implementation = implementation;
	}

	public Class<? extends T> getImplementation() {
		return implementation;
	}

	public Capability.IStorage<T> getStorage() {
		return storage;
	}

	public Class<T> getType() {
		return type;
	}

}
