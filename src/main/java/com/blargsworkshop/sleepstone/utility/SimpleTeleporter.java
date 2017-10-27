package com.blargsworkshop.sleepstone.utility;

import java.util.Iterator;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import io.netty.channel.ChannelFutureListener;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.network.ForgeMessage;

public class SimpleTeleporter {

	static void sendDimensionRegister(EntityPlayerMP player, int dimensionID) {
        int providerID = DimensionManager.getProviderType(dimensionID);
        ForgeMessage msg = new ForgeMessage.DimensionRegisterMessage(dimensionID, providerID);
        FMLEmbeddedChannel channel = NetworkRegistry.INSTANCE.getChannel("FORGE", Side.SERVER);
        channel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
        channel.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
        channel.writeAndFlush(msg).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }
	
	public static void teleportPlayerToDimension(EntityPlayerMP player, int newDimension, ChunkCoordinates p) {
        //System.out.printf("SGBaseTE.transferPlayerToDimension: %s to dimension %d\n", repr(player), newDimension);
        MinecraftServer server = MinecraftServer.getServer();
        ServerConfigurationManager scm = server.getConfigurationManager();
        int oldDimension = player.dimension;
        player.dimension = newDimension;
        WorldServer oldWorld = server.worldServerForDimension(oldDimension);
        WorldServer newWorld = server.worldServerForDimension(newDimension);
        //System.out.printf("SGBaseTE.transferPlayerToDimension: %s with %s\n", newWorld, newWorld.getEntityTracker());
        // <<< Fix for MCPC+
        // -- Is this still necessary now that we are calling firePlayerChangedDimensionEvent?
        // -- Yes, apparently it is.
        sendDimensionRegister(player, newDimension);
        // >>>
        player.closeScreen();
        player.playerNetServerHandler.sendPacket(new S07PacketRespawn(player.dimension,
        		player.worldObj.difficultySetting, newWorld.getWorldInfo().getTerrainType(), 
        		player.theItemInWorldManager.getGameType()));
        oldWorld.removePlayerEntityDangerously(player); // Removes player right now instead of waiting for next tick
        player.isDead = false;
        player.setLocationAndAngles(p.posX, p.posY, p.posZ, player.rotationYaw, player.rotationPitch);
        newWorld.spawnEntityInWorld(player);
        player.setWorld(newWorld);
        scm.func_72375_a(player, oldWorld); // scmPreparePlayer(scm, player, oldWorld);
        player.playerNetServerHandler.setPlayerLocation(p.posX, p.posY, p.posZ, player.rotationYaw, player.rotationPitch);
        player.theItemInWorldManager.setWorld(newWorld);
        scm.updateTimeAndWeatherForPlayer(player, newWorld);
        scm.syncPlayerInventory(player);
        Iterator<?> var6 = player.getActivePotionEffects().iterator();
        while (var6.hasNext()) {
            PotionEffect effect = (PotionEffect)var6.next();
            player.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(player.getEntityId(), effect));
        }
        player.playerNetServerHandler.sendPacket(new S1FPacketSetExperience(player.experience, player.experienceTotal, player.experienceLevel));
        FMLCommonHandler.instance().firePlayerChangedDimensionEvent(player, oldDimension, newDimension);
	}
	
	public static void teleportPlayerWithinDimension(EntityPlayerMP player, ChunkCoordinates p) {
        player.setPositionAndUpdate(p.posX, p.posY, p.posZ);
        player.worldObj.updateEntityWithOptionalForce(player, false);
    }
	
}
