package com.blargsworkshop.engine.logger;

import com.blargsworkshop.engine.utility.Utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

/**
 * This class handles the Logging.
 * TODO - make these two fields accessible through a config file.
 * LogLevel - Enum determining what level to log at.  Similar to Log4j
 * LogChat - true causes the log message to also be passed to the player chat client if a player was passed to the method.
 */
public class Log {
	private static LogLevel level = LogLevel.DETAIL;
	private static boolean logChat = true;

	/**
	 * Off - turns logging off
	 * Error - Only logs error messages
	 * Info - Logs info & error messages
	 * Debug - Logs info, error, & debug messages
	 * Detail - Logs detail & all other messages.  Think of this level as a more precise debug.
	 */
	public static enum LogLevel {
		OFF,
		ERROR,
		INFO,
		DEBUG,
		DETAIL
	};
	
	/**
	 * Logs an error message
	 * @param message - message to log.
	 */
	public static void error(String message) {
		Log.error(message, null);
	}
	
	/**
	 * Logs an error message and sends the message to the player chat.
	 * The chat feature must be turned on by setting LogChat to true.
	 * @param message - message to log.
	 * @param player - player to send chat message to.
	 */
	public static void error(String message, EntityPlayer player) {
		Log.log("ERROR: " + message, LogLevel.ERROR, player);
	}
	
	/**
	 * Logs an information message.
	 * @param message - message to log.
	 */
	public static void info(String message) {
		Log.info(message, null);
	}
	
	/**
	 * Logs an information message and sends the message to the player chat.
	 * The chat feature must be turned on by setting LogChat to true.
	 * @param message - message to log.
	 * @param player - player to send chat message to.
	 */
	public static void info(String message, EntityPlayer player) {
		Log.log(message, LogLevel.INFO, player);
	}
	
	/**
	 * Logs a debug message.
	 * @param message - message to log.
	 */
	public static void debug(String message) {
		Log.debug(message, null);
	}
	
	/**
	 * Logs a debug message and sends the message to the player chat.
	 * The chat feature must be turned on by setting LogChat to true.
	 * @param message - message to log.
	 * @param player - player to send chat message to.
	 */
	public static void debug(String message, EntityPlayer player) {
		Log.log("DEBUG: " + message, LogLevel.DEBUG, player);
	}
	
	/**
	 * Logs a detail message.
	 * @param message - message to log.
	 */
	public static void detail(String message) {
		Log.detail(message, null);
	}
	
	/**
	 * Logs a detail message and sends the message to the player chat.
	 * The chat feature must be turned on by setting LogChat to true.
	 * @param message - message to log.
	 * @param player - player to send chat message to.
	 */
	public static void detail(String message, EntityPlayer player) {
		Log.log("DETAIL: " + message, LogLevel.DETAIL, player);
	}
	
	/**
	 * Gets the current log level.
	 * @return current log level.
	 */
	public static LogLevel getLevel() {
		return level;
	}
	
	/**
	 * Sets the current log level.
	 * @param level
	 */
	public static void setLevel(LogLevel level) {
		Log.level = level;
	}
	
	/**
	 * Compares the current log level against the desired level.
	 * This comparison returns true if the desired level would cause a log message to print.
	 * <br><br>
	 * <b>For example:</b> if current log level is DEBUG, then
	 * <ul><li>compareLevel(ERROR) == true</li><li>compareLevel(DEBUG) == true</li><li>compareLevel(DETAIL) == false</li></ul>
	 * @param level - Desired log level
	 * @return True if the desired log level would cause a print.
	 */
	public static boolean compare(LogLevel level) {
		return shouldLog(level);
	}
	
	/**
	 * Gets the status of the chat feature.
	 * When turned on, the chat feature will also send log messages to the player if specified.
	 * @return True indicates the chat feature is turned on.
	 */
	public static boolean isChatFeatureOn() {
		return logChat;
	}
	
	/**
	 * Sets the status of the chat feature.
	 * When turned on, the chat feature will also send log messages to the player if specified.
	 * @param isOn - True turns the feature on. False turns the feature off.
	 */
	public static void setChatFeature(boolean isOn) {
		logChat = isOn;
	}
	
	private static void log(String str, LogLevel level, EntityPlayer player) {
		if (shouldLog(level)) {
			print(str, player);
		}
	}
	
	private static boolean shouldLog(LogLevel level) {
		boolean shouldLog = false;
		switch (Log.level) {
		case OFF:
			break;
		case ERROR:
			switch (level) {
			case ERROR:
				shouldLog = true;
			default:
				break;
			}
		case INFO:
			switch (level) {
			case ERROR:
			case INFO:
				shouldLog = true;
			default:
				break;
			}
		case DEBUG:
			switch (level) {
			case ERROR:
			case INFO:
			case DEBUG:
				shouldLog = true;
			default:
				break;
			}
		case DETAIL:
			shouldLog = true;
			break;		
		}
		return shouldLog;
	}
	
	private static void print(String str, EntityPlayer player) {
		System.out.println(str);
		if (logChat && player != null) {
			Utils.addUnlocalizedChatMessage(player, str);
		}
	}
	
	/**
	 * Helper function to help debug what side (client or server) the code is calling from.
	 * @param entity - Uses the World attached to the entity for determining side.  If the entity is a player, a player chat will also be sent if turned on.
	 */
	public static void checkSide(Entity entity) {
		EntityPlayer player = entity instanceof EntityPlayer ? (EntityPlayer) entity : null;
		debug("This is on side: " + (Utils.isClient(entity.getEntityWorld())  ? "client" : "server"), player);
	}
}
