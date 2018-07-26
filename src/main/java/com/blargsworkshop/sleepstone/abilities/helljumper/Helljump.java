package com.blargsworkshop.sleepstone.abilities.helljumper;

import com.blargsworkshop.engine.utility.Utils;
import com.blargsworkshop.sleepstone.ModItems.Potions;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;

public enum Helljump {
	INSTANCE;
	
	public void startJump(EntityPlayerMP player) {
		if (player.isPotionActive(Potions.warpSickness)) {
			Utils.addStatusMessage(player, "text.sleepstone.suffering_effects_of_warping");
		}
		else {
			if (player.dimension == DimensionType.NETHER.getId() || player.dimension == DimensionType.OVERWORLD.getId()) {
				Helljumper jumper = new Helljumper(player);
				BlockPos destinationPoint = jumper.calcJumpPoint(jumper.destinationDim);
				BlockPos safePoint = jumper.findSafeSpot(destinationPoint);
				if (safePoint != null) {
					player.addPotionEffect(new HelljumpWarpEffect(player));
				}
				else {
					Utils.addStatusMessage(player, "text.helljump.fizzle");
				}
			}
			else {
				Utils.addChatMessage(player, "text.helljump.not.dimension.attuned");
			}
		}
	}

}
