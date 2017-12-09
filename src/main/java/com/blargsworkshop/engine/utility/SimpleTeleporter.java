package com.blargsworkshop.engine.utility;

import java.util.Iterator;

import com.blargsworkshop.engine.logger.Log;

import io.netty.channel.ChannelFutureListener;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketRespawn;
import net.minecraft.network.play.server.SPacketSetExperience;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.network.ForgeMessage;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.FMLEmbeddedChannel;
import net.minecraftforge.fml.common.network.FMLOutboundHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Static functions for teleporting a player.
 * Supports in world teleport and cross-dimension.
 * <br>
 * This might make sense to get moved to a player capability.
 */
public class SimpleTeleporter {

	/**
	 * Teleports a player to a new place in the current world.
	 * @param player - Player to teleport
	 * @param position - Target destination
	 */
	public static void teleportPlayerWithinDimension(EntityPlayerMP player, BlockPos position) {
		player.setPositionAndUpdate(position.getX(), position.getY(), position.getZ());
		player.getEntityWorld().updateEntityWithOptionalForce(player, false);
	}	
	
	/**
	 * Teleports a player from their current dimension to another.
	 * @param player - Player to teleport
	 * @param destDimension - Destination dimension
	 * @param position - Target location in destination dimension
	 */
	public static void teleportPlayerToDimension(EntityPlayerMP player, DimensionType destDimension, BlockPos position) {
		DimensionType sourceDimension = DimensionType.getById(player.dimension);
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        PlayerList scm = server.getPlayerList();
        player.dimension = destDimension.getId();
        WorldServer sourceWorld = player.getServerWorld();
        WorldServer destinationWorld = DimensionManager.getWorld(destDimension.getId());
        // <<< Fix for MCPC+
        // -- Is this still necessary now that we are calling firePlayerChangedDimensionEvent?
        // -- Yes, apparently it is.
        sendDimensionRegister(player, destDimension.getId());
        // >>>
        player.closeScreen();
        player.connection.sendPacket(new SPacketRespawn(player.dimension,
            player.getEntityWorld().getDifficulty(), destinationWorld.getWorldInfo().getTerrainType(),
            player.interactionManager.getGameType()));
        sourceWorld.removeEntityDangerously(player); // Removes player right now instead of waiting for next tick
        player.isDead = false;
        player.setLocationAndAngles(position.getX(), position.getY(), position.getZ(), player.rotationYaw, player.rotationPitch);
        destinationWorld.spawnEntity(player);
        player.setWorld(destinationWorld);
        scm.preparePlayer(player, sourceWorld);
        player.connection.setPlayerLocation(position.getX(), position.getY(), position.getZ(), player.rotationYaw, player.rotationPitch);
        player.interactionManager.setWorld(destinationWorld);
        scm.updateTimeAndWeatherForPlayer(player, destinationWorld);
        scm.syncPlayerInventory(player);
        Iterator<PotionEffect> var6 = player.getActivePotionEffects().iterator();
        while (var6.hasNext()) {
            PotionEffect effect = (PotionEffect)var6.next();
            player.connection.sendPacket(new SPacketEntityEffect(player.getEntityId(), effect));
        }
        player.connection.sendPacket(new SPacketSetExperience(player.experience, player.experienceTotal, player.experienceLevel));
        FMLCommonHandler.instance().firePlayerChangedDimensionEvent(player, sourceDimension.getId(), destDimension.getId());
		String logMessage = player.getDisplayNameString() + " teleported from " + sourceDimension.getName() + " to " + destDimension.getName() + ".";
		Log.debug(logMessage, player);
	}
	
	private static void sendDimensionRegister(EntityPlayerMP player, int dimensionID) {
		DimensionType providerID = DimensionManager.getProviderType(dimensionID);
		ForgeMessage forgeMsg = new ForgeMessage.DimensionRegisterMessage(dimensionID, providerID.toString());
		FMLEmbeddedChannel channel = NetworkRegistry.INSTANCE.getChannel("FORGE", Side.SERVER);
		channel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
		channel.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
		channel.writeAndFlush(forgeMsg).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
	}
}
