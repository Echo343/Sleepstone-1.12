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
import com.blargsworkshop.engine.annotations.render.NoBlockstate;
import com.blargsworkshop.engine.annotations.render.TEISR;
import com.blargsworkshop.engine.annotations.render.TESR;
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

	private final List<Item> modItems = new ArrayList<>();
	private final List<ResourceLocation> modSprites = new ArrayList<>();
	
	private final List<Block> tesrBlocks = new ArrayList<>();
	@SuppressWarnings("rawtypes")
	private final Map<Class<? extends TileEntity>, Class<? extends TileEntitySpecialRenderer>> tesrTileEnities = new HashMap<>();
	private final Map<Item, Class<? extends TileEntityItemStackRenderer>> teisrItems = new HashMap<>();
	
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
			
			sortAndFilterTesrTeisr(f);
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
			
			sortAndFilterTesrTeisr(innerClass);
		}
	}

	/**
	 * Registers our models
	 * @param event
	 */
	@SuppressWarnings("unchecked")
	@SubscribeEvent
	public void onModelRegistry(ModelRegistryEvent event) {
		Log.advDebug("Register Models - Models");
		for (Item i : modItems) {
			registerItemModel(i);
			Log.advDebug("Register Models - " + i.getRegistryName());
		}
		
		tesrTileEnities.forEach((tileClass, tesrClass) -> {
			try {
				ClientRegistry.bindTileEntitySpecialRenderer(tileClass, tesrClass.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
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
	}
	
	/**
	 * Registers textures or sprites.
	 * @param event
	 */
	@SubscribeEvent
	public void onTextureRegistry(TextureStitchEvent.Pre event) {
		Log.advDebug("Register Models - Textures");
		for (ResourceLocation sprite : modSprites) {
			// TODO add these to a registry
			event.getMap().registerSprite(sprite);
			Log.advDebug("Register Models - " + sprite.toString());
		}
	}
	
	private void registerItemModel(Item item) {
		if (item.getHasSubtypes() && item instanceof ISubtypable) {
			ISubtypable subtypableItem = (ISubtypable) item;
			subtypableItem.getResourceLocationMap().forEach((Integer meta, ResourceLocation resource) -> {
				ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(resource, null));
			});
		}
		else {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), null));
		}
	}

	private void sortAndFilterTesrTeisr(Field f) throws IllegalAccessException {
		if (f.isAnnotationPresent(TEISR.class) && f.isAnnotationPresent(ModItem.class)) {
			teisrItems.put((Item) f.get(null), f.getAnnotation(TEISR.class).value());
		}
		else if (f.isAnnotationPresent(TESR.class) && f.isAnnotationPresent(ModTileEntity.class)) {
			Object tile = f.get(null);
			if (tile instanceof TileEntity) {
				tesrTileEnities.put(((TileEntity)tile).getClass(), f.getAnnotation(TESR.class).value());
			}
		}
		else if (f.isAnnotationPresent(NoBlockstate.class) && f.isAnnotationPresent(ModBlock.class)) {
			tesrBlocks.add((Block) f.get(null));
		}
	}

	private void sortAndFilterTesrTeisr(Class<?> innerClass) throws IllegalAccessException {
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
					Object tile = f.get(null);
					if (tile instanceof TileEntity) {
						tesrTileEnities.put(((TileEntity)tile).getClass(), f.getAnnotation(TESR.class).value());
					}
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
