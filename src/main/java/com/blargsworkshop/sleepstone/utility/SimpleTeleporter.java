package com.blargsworkshop.sleepstone.utility;

import com.blargsworkshop.sleepstone.Log;

import io.netty.channel.ChannelFutureListener;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.network.ForgeMessage;
import net.minecraftforge.fml.common.network.FMLEmbeddedChannel;
import net.minecraftforge.fml.common.network.FMLOutboundHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class SimpleTeleporter {

	private static void sendDimensionRegister(EntityPlayerMP player, int dimensionID) {
		DimensionType providerID = DimensionManager.getProviderType(dimensionID);
        ForgeMessage msg = new ForgeMessage.DimensionRegisterMessage(dimensionID, providerID.toString());
        FMLEmbeddedChannel channel = NetworkRegistry.INSTANCE.getChannel("FORGE", Side.SERVER);
        channel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
        channel.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
        channel.writeAndFlush(msg).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }
	
	public static void teleportPlayerToDimension(EntityPlayerMP player, DimensionType destDimension, BlockPos p) {
		DimensionType oldDimensionType = DimensionType.getById(player.dimension);
//		int newDimension = destDimension.getValue();
//		//TODO find out how to get the server
//		MinecraftServer server = MinecraftServer.getMinecraftServer();
//        PlayerList scm = server.getPlayerList();
//        int oldDimension = player.dimension;
//        player.dimension = newDimension;
//        WorldServer oldWorld = server.worldServerForDimension(oldDimension);
//        WorldServer newWorld = server.worldServerForDimension(newDimension);
//        // <<< Fix for MCPC+
//        // -- Is this still necessary now that we are calling firePlayerChangedDimensionEvent?
//        // -- Yes, apparently it is.
//        sendDimensionRegister(player, newDimension);
//        // >>>
//        player.closeScreen();
//        player.connection.sendPacket(new SPacketRespawn(player.dimension,
//            player.getEntityWorld().getDifficulty(), newWorld.getWorldInfo().getTerrainType(),
//            player.interactionManager.getGameType()));
//        oldWorld.removeEntityDangerously(player); // Removes player right now instead of waiting for next tick
//        player.isDead = false;
//        player.setLocationAndAngles(p.getX(), p.getY(), p.getZ(), player.rotationYaw, player.rotationPitch);
//        newWorld.spawnEntityInWorld(player);
//        player.setWorld(newWorld);
//        scm.preparePlayer(player, oldWorld);
//        player.connection.setPlayerLocation(p.getX(), p.getY(), p.getZ(), player.rotationYaw, player.rotationPitch);
//        player.interactionManager.setWorld(newWorld);
//        scm.updateTimeAndWeatherForPlayer(player, newWorld);
//        scm.syncPlayerInventory(player);
//        Iterator<PotionEffect> var6 = player.getActivePotionEffects().iterator();
//        while (var6.hasNext()) {
//            PotionEffect effect = (PotionEffect)var6.next();
//            player.connection.sendPacket(new SPacketEntityEffect(player.getEntityId(), effect));
//        }
//        player.connection.sendPacket(new SPacketSetExperience(player.experience, player.experienceTotal, player.experienceLevel));
//        FMLCommonHandler.instance().firePlayerChangedDimensionEvent(player, oldDimension, newDimension);
		String logMessage = player.getDisplayNameString() + "teleported from " + oldDimensionType.getName() + " to " + destDimension.getName() + ".";
		Log.debug(logMessage, player);
	}
	
	public static void teleportPlayerWithinDimension(EntityPlayerMP player, BlockPos p) {
        player.setPositionAndUpdate(p.getX(), p.getY(), p.getZ());
        player.getEntityWorld().updateEntityWithOptionalForce(player, false);
    }
	
}
