package com.blargsworkshop.sleepstone.potions;

import com.blargsworkshop.sleepstone.Log;
import com.blargsworkshop.sleepstone.ModItems.Potions;
import com.blargsworkshop.sleepstone.utility.SimpleTeleporter;
import com.blargsworkshop.sleepstone.utility.Utils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;

public class EnderShardPotionEffect extends BlargsPotionEffect {
	private static final int ENDERWARP_DURATION = 20 * 30;
	
	private BlockPos returnPosition;
	private DimensionType returnDimension;

	public EnderShardPotionEffect(EntityPlayer player) {
		super(Potions.enderShardWarp, ENDERWARP_DURATION, 0, false, false);
		setReturnPosition(player.getPosition());
		setReturnDimension(DimensionType.getById(player.dimension));
	}
	
	protected void setReturnPosition(BlockPos position) {
		this.returnPosition = position;
	}
	
	protected BlockPos getReturnPosition() {
		return this.returnPosition;
	}
	
	protected void setReturnDimension(DimensionType dim) {
		this.returnDimension = dim;
	}
	
	protected DimensionType getReturnDimension() {
		return returnDimension;
	}
	
	@Override
	protected void onFinishedPotionEffect(EntityLivingBase entity) {		
		if (Utils.isServer(entity.getEntityWorld()) && entity instanceof EntityPlayerMP) {
			Log.debug("Ender Shard just ended.", (EntityPlayer) entity);
			SimpleTeleporter.teleportPlayerToDimension((EntityPlayerMP) entity, getReturnDimension(), getReturnPosition());
		}
	}
}
