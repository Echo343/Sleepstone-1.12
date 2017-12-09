package com.blargsworkshop.engine.network;

import java.lang.reflect.MalformedParametersException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Wrapper for sending packets
 * https://github.com/coolAlias/Tutorial-Demo/blob/master/src/main/java/tutorial/network/PacketDispatcher.java
 * @author Echo343
 *
 */
public class PacketDispatcher {
	
	private byte packetId = 0;
	private final String modId;
	private final SimpleNetworkWrapper dispatcher;
	
	public PacketDispatcher(String modId) {
		if (modId == null) {
			throw new NullPointerException();
		}
		else if (modId.trim().equals("")) {
			throw new MalformedParametersException("modId can not be empty");
		}
		
		this.modId = modId.trim();
		dispatcher = NetworkRegistry.INSTANCE.newSimpleChannel(this.modId);
	}
	
	public String getModId() {
		return this.modId;
	}
	
	
	/**
	 * Registers an {@link AbstractMessage} to the appropriate side(s)
	 * @param clazz
	 */
	public final <T extends AbstractMessage<T> & IMessageHandler<T, IMessage>> void registerMessage(Class<T> clazz) {
		if (AbstractMessage.AbstractClientMessage.class.isAssignableFrom(clazz)) {
			dispatcher.registerMessage(clazz, clazz, packetId++, Side.CLIENT);
		}
		else if (AbstractMessage.AbstractServerMessage.class.isAssignableFrom(clazz)) {
			dispatcher.registerMessage(clazz, clazz, packetId++, Side.SERVER);
		}
		else {
			dispatcher.registerMessage(clazz, clazz, packetId, Side.CLIENT);
			dispatcher.registerMessage(clazz, clazz, packetId++, Side.SERVER);
		}
	}
	
	/** Wrappers **/
	
	/**
	 * Send this message to the specified player's client-side counterpart.
	 * See {@link SimpleNetworkWrapper#sendTo(IMessage, EntityPlayerMP)}
	 * @param player
	 * @param message
	 */
	public final void sendToPlayer(EntityPlayerMP player, IMessage message) {
		dispatcher.sendTo(message, player);
	}
	
	/**
	 * Send this message to the specified player's client-side counterpart.
	 * See {@link SimpleNetworkWrapper#sendTo(IMessage, EntityPlayerMP)}
	 * @param player This will auto-downcast EntityPlayer to EntityPlayerMP
	 * @param message
	 */
	public final void sendToPlayer(EntityPlayer player, IMessage message) {
		sendToPlayer((EntityPlayerMP) player, message);
	}
	
	/**
	 * Send this message to everyone.
	 * See {@link SimpleNetworkWrapper#sendToAll(IMessage)}
	 * @param message
	 */
	public void sendToAll(IMessage message) {
		dispatcher.sendToAll(message);
	}
	
	/**
	 * Send this message to everyone within a certain range of a point.
	 * See {@link SimpleNetworkWrapper#sendToAllAround(IMessage, NetworkRegistry.TargetPoint)}
	 * @param message
	 * @param point
	 */
	public final void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {
		dispatcher.sendToAllAround(message, point);
	}
	
	/**
	 * Sends a message to everyone within a certain range of the coordinates in the same dimension.
	 * Shortcut to {@link SimpleNetworkWrapper#sendToAllAround(IMessage, NetworkRegistry.TargetPoint)}
	 * @param message
	 * @param dimension
	 * @param x
	 * @param y
	 * @param z
	 * @param range
	 */
	public final void sendToAllAround(IMessage message, int dimension, double x, double y, double z, double range) {
		dispatcher.sendToAllAround(message, new NetworkRegistry.TargetPoint(dimension, x, y, z, range));
	}
	
	/**
	 * Sends a message to everyone within a certain range of the player provided.
	 * Shortcut to {@link SimpleNetworkWrapper#sendToAllAround(IMessage, NetworkRegistry.TargetPoint)}
	 * @param message
	 * @param player
	 * @param range
	 */
	public final void sendToAllAround(IMessage message, EntityPlayer player, double range) {
		sendToAllAround(message, player.getEntityWorld().provider.getDimension(), player.posX, player.posY, player.posZ, range);
	}
	
	/**
	 * Send this message to everyone within the supplied dimension.
	 * See {@link SimpleNetworkWrapper#sendToDimension(IMessage, int)}
	 * @param message
	 * @param dimensionId
	 */
	public final void sendToDimension(IMessage message, int dimensionId) {
		dispatcher.sendToDimension(message, dimensionId);
	}
	
	/**
	 * Send this message to the server.
	 * See {@link SimpleNetworkWrapper#sendToServer(IMessage)}
	 * @param message
	 */
	public final void sendToServer(IMessage message) {
		dispatcher.sendToServer(message);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
