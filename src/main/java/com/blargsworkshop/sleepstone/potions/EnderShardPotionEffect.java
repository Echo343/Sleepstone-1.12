package com.blargsworkshop.sleepstone.potions;

import com.blargsworkshop.sleepstone.Log;
import com.blargsworkshop.sleepstone.ModItems.Potions;
import com.blargsworkshop.sleepstone.utility.SimpleTeleporter;
import com.blargsworkshop.sleepstone.utility.SimpleTeleporter.Dimension;
import com.blargsworkshop.sleepstone.utility.Utils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;

public class EnderShardPotionEffect extends BlargsPotionEffect {
	private static final int ENDERWARP_DURATION = 20 * 30;
	
	private ChunkCoordinates returnPosition;
	private Dimension returnDimension;

	public EnderShardPotionEffect(EntityPlayer player) {
		super(Potions.enderShardWarp.id, ENDERWARP_DURATION);
		setReturnCoordinates(new ChunkCoordinates((int) player.posX, (int) player.posY, (int) player.posZ));
		setReturnDimension(Dimension.getDimensionFromInt(player.dimension));
	}
	
	protected void setReturnCoordinates(ChunkCoordinates position) {
		this.returnPosition = position;
	}
	
	protected ChunkCoordinates getDepartureCoordinates() {
		return this.returnPosition;
	}
	
	protected void setReturnDimension(Dimension dim) {
		this.returnDimension = dim;
	}
	
	protected Dimension getDimension() {
		return returnDimension;
	}
	
	@Override
	protected void onFinishedPotionEffect(EntityLivingBase entity) {		
		if (Utils.isServer(entity.worldObj) && entity instanceof EntityPlayerMP) {
			Log.debug("Ender Shard just ended.", (EntityPlayer) entity);
			SimpleTeleporter.teleportPlayerToDimension((EntityPlayerMP) entity, getDimension(), getDepartureCoordinates());
		}
	}
}
