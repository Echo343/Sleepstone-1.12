package com.blargsworkshop.engine.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Interface to implement in your CommonProxy.
 * For ease of use, instead have your CommonProxy extend BlargsCommonProxy.
 * Then use the helper methods for setup.
 */
public interface IProxy {
	
	/**
	 * Pre Init
	 * <br>
	 * Things that go in preInit: <none>
	 * @param e
	 */
	public void preInit(FMLPreInitializationEvent e);
	
	/**
	 * Init
	 * <br>
	 * Things that go in init:
	 * <ul>
	 * 		<li>Capability Registration</li>
	 * 		<li>EvenHandler Registration</li>
	 * 		<li>GuiHandler Registration</li>
	 * 		<li>Packet Registration</li>
	 * </ul>
	 * @param e
	 */
    public void init(FMLInitializationEvent e);
    
    /**
     * Post Init
     * <br>
     * Cross mod setups and configs go in postInit.
     * @param e
     */
    public void postInit(FMLPostInitializationEvent e);
    
    /**
     * This is used by the PacketDispatcher to grab the EntityPlayer from the appropriate side: Client, or Server.
     * @param ctx - MessageContext from a Network Packet.
     * @return player from the appropriate side: server or client
     */
    public EntityPlayer getPlayerEntity(MessageContext ctx);
    
    /**
     * Returns the current thread based on side during message handling,
     * used for ensuring that the message is being handled by the main thread.
     * req. 1.8+ only
     * @param ctx
     * @return main thread
     */
    public net.minecraft.util.IThreadListener getThreadFromContext(MessageContext ctx);
}
