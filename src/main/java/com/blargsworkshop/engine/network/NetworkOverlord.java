package com.blargsworkshop.engine.network;

import java.lang.reflect.MalformedParametersException;
import java.util.Iterator;
import java.util.LinkedList;

public class NetworkOverlord {

	private static LinkedList<PacketDispatcher> dispatchers = new LinkedList<>();
	
	public static PacketDispatcher register(String modId) {
		validateModId(modId);
		
		modId = modId.trim();
		if (hasNotBeenRegistered(modId)) {
			PacketDispatcher dispatch = new PacketDispatcher(modId);
			dispatchers.add(dispatch);
			return dispatch;
		}
		else {
			throw new RuntimeException("PacketDispatcher has already been registered for mod: " + modId);
		}
	}
	
	public static PacketDispatcher get(String modId) {
		Iterator<PacketDispatcher> iter = dispatchers.iterator();
		while (iter.hasNext()) {
			PacketDispatcher dispatch = iter.next();
			if (dispatch.getModId().equals(modId)) {
				return dispatch;
			}
		}
		return null;
	}

	private static boolean hasNotBeenRegistered(String modId) {
		boolean hasNotBeenRegistered = true;
		Iterator<PacketDispatcher> dispatchIterator = dispatchers.iterator();
		while (hasNotBeenRegistered && dispatchIterator.hasNext()) {
			if (dispatchIterator.next().getModId().equals(modId)) {
				hasNotBeenRegistered = false;
			}
		}
		return hasNotBeenRegistered;
	}
	
	private static void validateModId(String str) {
		if (str == null) {
			throw new NullPointerException();
		}
		else if (str.trim().equals("")) {
			throw new MalformedParametersException("modId can not be empty");
		}
	}
}
