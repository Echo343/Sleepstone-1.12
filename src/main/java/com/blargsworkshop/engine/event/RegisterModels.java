package com.blargsworkshop.engine.event;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blargsworkshop.engine.IModItems;
import com.blargsworkshop.engine.annotations.ModBlock;
import com.blargsworkshop.engine.annotations.ModItem;
import com.blargsworkshop.engine.annotations.ModSprite;
import com.blargsworkshop.engine.annotations.ModTileEntity;
import com.blargsworkshop.engine.annotations.NoBlockstate;
import com.blargsworkshop.engine.annotations.TEISR;
import com.blargsworkshop.engine.annotations.TESR;
import com.blargsworkshop.engine.item.ISubtypable;
import com.blargsworkshop.engine.logger.Log;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class is used to register all the models and sprites on the client side only.
 * It uses reflection on your IModItems file to work like magic. =)
 */
@SideOnly(Side.CLIENT)
public class RegisterModels {

	private List<Item> modItems = new ArrayList<>();
	private List<ResourceLocation> modSprites = new ArrayList<>();
	
	private List<Block> tesrBlocks = new ArrayList<>();
	private Map<Item, Class<? extends TileEntityItemStackRenderer>> teisrItems = new HashMap<>();
	private Map<TileEntity, Class<? extends TileEntitySpecialRenderer<?>>> tesrTileEnities = new HashMap<>();
	
	public RegisterModels(Class<? extends IModItems> class1) {
		try {
			sortAndFilter(class1.getDeclaredFields());
			sortAndFilter(class1.getDeclaredClasses());			
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	protected void sortAndFilter(Field[] fields) throws IllegalArgumentException, IllegalAccessException {
		for (Field f : fields) {
			if (f.isAnnotationPresent(ModItem.class)) {
				modItems.add((Item) f.get(null));
			}
			else if (f.isAnnotationPresent(ModSprite.class)) {
				modSprites.add((ResourceLocation) f.get(null));
			}
			
			// TODO pull out into method
			if (f.isAnnotationPresent(TEISR.class) && f.isAnnotationPresent(ModItem.class)) {
				teisrItems.put((Item) f.get(null), f.getAnnotation(TEISR.class).value());
			}
			else if (f.isAnnotationPresent(TESR.class) && f.isAnnotationPresent(ModTileEntity.class)) {
				tesrTileEnities.put((TileEntity) f.get(null), f.getAnnotation(TESR.class).value());
			}
			else if (f.isAnnotationPresent(NoBlockstate.class) && f.isAnnotationPresent(ModBlock.class)) {
				tesrBlocks.add((Block) f.get(null));
			}
		}
	}
	
	protected void sortAndFilter(Class<?>[] innerClasses) throws IllegalArgumentException, IllegalAccessException {
		for (Class<?> innerClass : innerClasses) {
			if (innerClass.isAnnotationPresent(ModItem.class)) {
				for (Field f : innerClass.getDeclaredFields()) {
					modItems.add((Item) f.get(null));
				}
			}
			else if (innerClass.isAnnotationPresent(ModSprite.class)) {
				for (Field f : innerClass.getDeclaredFields()) {
					modSprites.add((ResourceLocation) f.get(null));
				}
			}
			
			// TODO pull out into method
			if (innerClass.isAnnotationPresent(ModItem.class)) {
				for (Field f : innerClass.getDeclaredFields()) {
					if (f.isAnnotationPresent(TEISR.class)) {
						teisrItems.put((Item) f.get(null), f.getAnnotation(TEISR.class).value());
					}
				}
			}
			else if (innerClass.isAnnotationPresent(ModTileEntity.class)) {
				for (Field f : innerClass.getDeclaredFields()) {
					if (f.isAnnotationPresent(TESR.class)) {
						tesrTileEnities.put((TileEntity) f.get(null), f.getAnnotation(TESR.class).value());
					}
				}
			}
			else if (innerClass.isAnnotationPresent(ModBlock.class)) {
				for (Field f : innerClass.getDeclaredFields()) {
					if (f.isAnnotationPresent(NoBlockstate.class)) {
						tesrBlocks.add((Block) f.get(null));
					}
				}
			}
			
		}
	}

	/**
	 * Registers our models
	 * @param event
	 */
	@SubscribeEvent
	public void onModelRegistry(ModelRegistryEvent event) {
		Log.detail("Register Models - Models");
		for (Item i : modItems) {
			registerItemModel(i);
			Log.detail("Register Models - " + i.getRegistryName());
		}
		
		tesrTileEnities.forEach((tile, tesr) -> {
			ClientRegistry.bindTileEntitySpecialRenderer(tile.getClass(), tesr.newInstance());
		});
		teisrItems.forEach((item, teisr) -> {
			try {
				item.setTileEntityItemStackRenderer(teisr.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		tesrBlocks.forEach((block) -> {
			ModelLoader.setCustomStateMapper(block, new IStateMapper() {
				@Override
				public Map<IBlockState, ModelResourceLocation> putStateModelLocations(Block blockIn) {
					return Collections.emptyMap();
				}
			});
		});
		
		//TODO automate this
//		ClientRegistry.bindTileEntitySpecialRenderer(AirMattressTileEntity.class, new TileEntityAirMattressRenderer());
//		((Item)ModItems.Items.airMattress).setTileEntityItemStackRenderer(new AirMattressItemStackRenderer());
//		ModelLoader.setCustomStateMapper(ModItems.Blocks.airMattress, new IStateMapper() {
//			@Override
//			public Map<IBlockState, ModelResourceLocation> putStateModelLocations(Block blockIn) {
//				return Collections.emptyMap();
//			}
//		});
	}
	
	/**
	 * Registers textures or sprites.
	 * @param event
	 */
	@SubscribeEvent
	public void onTextureRegistry(TextureStitchEvent.Pre event) {
		Log.detail("Register Models - Textures");
		for (ResourceLocation sprite : modSprites) {
			// TODO add these to a registry
			event.getMap().registerSprite(sprite);
			Log.detail("Register Models - " + sprite.toString());
		}
	}
	
	private void registerItemModel(Item item) {
		if (item.getHasSubtypes() && item instanceof ISubtypable) {
			ISubtypable craftable = (ISubtypable) item;
			craftable.getResourceLocationMap().forEach((Integer meta, ResourceLocation resource) -> {
				ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(resource, null));
			});
		}
		else {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), null));
		}
	}

}
