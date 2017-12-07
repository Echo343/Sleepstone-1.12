package com.blargsworkshop.sleepstone.network;

import java.util.LinkedList;

public class NetworkOverlord {

	private static LinkedList<PacketDispatcher> dispatchers = new LinkedList<>();
	
	public static PacketDispatcher register(String modId) {
		if (checkForExisting(modId)) {
			PacketDispatcher dispatch = new PacketDispatcher(modId);
			dispatchers.add(dispatch);
			return dispatch;
		}
		else {
			// TODO throw error.
			return null;
		}
	}

	private static boolean checkForExisting(String modId) {
		return true;
	}
}
