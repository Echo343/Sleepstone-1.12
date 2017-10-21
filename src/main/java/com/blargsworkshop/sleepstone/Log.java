package com.blargsworkshop.sleepstone;

import net.minecraft.entity.player.EntityPlayer;

public class Log {
	public static LogLevel Level = LogLevel.Detail;
	private static boolean LogChat = true;
	
	public static enum LogLevel {
		Off,
		Error,
		Info,
		Debug,
		Detail
	};
	
	public static void error(String message) {
		Log.error(message, null);
	}
	public static void error(String message, EntityPlayer player) {
		Log.log("ERROR: " + message, LogLevel.Error, player);
	}
	
	public static void info(String message) {
		Log.info(message, null);
	}
	public static void info(String message, EntityPlayer player) {
		Log.log(message, LogLevel.Info, player);
	}
	
	public static void debug(String message) {
		Log.debug(message, null);
	}
	public static void debug(String message, EntityPlayer player) {
		Log.log("DEBUG: " + message, LogLevel.Debug, player);
	}
	
	public static void detail(String message) {
		Log.detail(message, null);
	}
	public static void detail(String message, EntityPlayer player) {
		Log.log("DETAIL: " + message, LogLevel.Detail, player);
	}
	
	private static void log(String str, LogLevel level, EntityPlayer player) {
		switch (Log.Level) {
		case Off:
			break;
		case Error:
			switch (level) {
			case Error:
				print(str, player);
			default:
				break;
			}
		case Info:
			switch (level) {
			case Error:
			case Info:
				print(str, player);
			default:
				break;
			}
		case Debug:
			switch (level) {
			case Error:
			case Info:
			case Debug:
				print(str, player);
			default:
				break;
			}
		case Detail:
			print(str, player);
			break;		
		}
	}
	
	private static void print(String str, EntityPlayer player) {
		System.out.println(str);
		if (LogChat && player != null) {
			Utils.addUnlocalizedChatMessage(player, str);
		}
	}
	
	public static void checkSide(EntityPlayer player) {
		debug("This is on side: " + (player.worldObj.isRemote ? "client" : "server"), player);
	}
}
