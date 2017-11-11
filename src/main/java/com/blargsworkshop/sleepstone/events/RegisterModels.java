package com.blargsworkshop.sleepstone.events;

import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.ModItems;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = ModInfo.ID)
public class RegisterModels {

	@SubscribeEvent
	public static void onModelRegistry(ModelRegistryEvent event) {
		//TODO make reflective and ask for subType to loop
//		ModelLoader.setCustomModelResourceLocation(ModItems.itemSleepstone, 0, new ModelResourceLocation(ModInfo.ID + ":" + "sleepstone"));
//		ModelLoader.setCustomModelResourceLocation(ModItems.itemStoneGem, 0, new ModelResourceLocation(ModInfo.ID + ":" + "gem_stone"));
//		ModelLoader.setCustomModelResourceLocation(ModItems.itemTimeSpaceGem, 0, new ModelResourceLocation(ModInfo.ID + ":" + "gem_timeandspace"));
		ModelLoader.setCustomModelResourceLocation(ModItems.itemPathfinderGem, 0, new ModelResourceLocation(ModItems.itemPathfinderGem.getRegistryName(), null));
//		ModelLoader.setCustomModelResourceLocation(ModItems.itemEtherealGem, 0, new ModelResourceLocation(ModInfo.ID + ":" + "gem_ethereal"));
//		ModelLoader.setCustomModelResourceLocation(ModItems.itemGuardianGem, 0, new ModelResourceLocation(ModInfo.ID + ":" + "gem_guardian"));
//		ModelLoader.setCustomModelResourceLocation(ModItems.itemFireGem, 0, new ModelResourceLocation(ModInfo.ID + ":" + "gem_fire"));
		
		// TODO do something with this.
//		for (int i = 0; i < StoneCraftable.NUMBER_OF_CRAFTABLES; i++) {
//			mesher.register(ModItems.itemStoneCraftable, i, new ModelResourceLocation(ModInfo.ID + ":" + "craftablestone_" + i));
//		}
//		mesher.register(ModItems.itemPathfinderCraftable, 0, new ModelResourceLocation(ModInfo.ID + ":" + "craftablepathfinder"));
//		mesher.register(ModItems.itemEnderShard, 0, new ModelResourceLocation(ModInfo.ID + ":" + "endershard"));
//		
//		mesher.register(ModItems.textureGemSlotStone, 0, new ModelResourceLocation(ModInfo.ID + ":" + "slot-gem-stone"));
//		mesher.register(ModItems.textureGemSlotPathfinder, 0, new ModelResourceLocation(ModInfo.ID + ":" + "slot-gem-pathfinder"));
//		mesher.register(ModItems.textureGemSlotTimeAndSpace, 0, new ModelResourceLocation(ModInfo.ID + ":" + "slot-gem-time-and-space"));
//		mesher.register(ModItems.textureGemSlotFire, 0, new ModelResourceLocation(ModInfo.ID + ":" + "slot-gem-fire"));
//		mesher.register(ModItems.textureGemSlotGuardian, 0, new ModelResourceLocation(ModInfo.ID + ":" + "slot-gem-guardian"));
//		mesher.register(ModItems.textureGemSlotEthereal, 0, new ModelResourceLocation(ModInfo.ID + ":" + "slot-gem-ethereal"));
	}

}
