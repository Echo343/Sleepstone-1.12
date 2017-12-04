package com.blargsworkshop.sleepstone.events;

import com.blargsworkshop.sleepstone.IModItems;
import com.blargsworkshop.sleepstone.Log;
import com.blargsworkshop.sleepstone.ModItems;
import com.blargsworkshop.sleepstone.items.gems.mats.BaseCraftable;
import com.blargsworkshop.sleepstone.items.stone.container.GemSlot;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RegisterModels {
	
	protected Class<? extends IModItems> class1;
	
	public RegisterModels(Class<? extends IModItems> class1) {
		this.class1 = class1;
	}

	@SubscribeEvent
	public void onModelRegistry(ModelRegistryEvent event) {
		Log.detail("RegisterModels - Models");
		//TODO make reflective and ask for subType to loop
		registerItemModel(ModItems.itemSleepstone);
		registerItemModel(ModItems.itemStoneGem);
		registerItemModel(ModItems.itemTimeSpaceGem);
		registerItemModel(ModItems.itemPathfinderGem);
		registerItemModel(ModItems.itemEtherealGem);
		registerItemModel(ModItems.itemGuardianGem); 
		registerItemModel(ModItems.itemFireGem);
		
		registerItemModel(ModItems.itemStoneCraftable);
		registerItemModel(ModItems.itemPathfinderCraftable);
		registerItemModel(ModItems.itemEnderShard);
	}
	
	@SubscribeEvent
	public void onTextureRegistry(TextureStitchEvent.Pre event) {
		Log.detail("RegisterModels - Textures");
		GemSlot.getSpritesToRegister().forEach((ResourceLocation rl) -> event.getMap().registerSprite(rl));
	}
	
	private void registerItemModel(Item item) {
		// TODO interface here
		if (item.getHasSubtypes() && item instanceof BaseCraftable) {
			BaseCraftable craftable = (BaseCraftable) item;
			craftable.getResourceLocationMap().forEach((Integer meta, ResourceLocation resource) -> {
				ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(resource, null));
			});
		}
		else {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), null));
		}
	}

}
