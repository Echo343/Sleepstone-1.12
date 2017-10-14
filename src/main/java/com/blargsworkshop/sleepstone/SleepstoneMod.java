package com.blargsworkshop.sleepstone;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.blargsworkshop.sleepstone.proxy.IProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.potion.Potion;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION)
public class SleepstoneMod {

	@Instance
	public static SleepstoneMod instance = new SleepstoneMod();

	@SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.COMMON_PROXY)
	public static IProxy proxy;

	/**
	 * Read Config, create blocks, items, etc & register them.
	 * @param e
	 */
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		Log.detail("PreInit Start");
		proxy.preInit(e);
		Log.detail("PreInit End");
	}

	/**
	 * Build up data structures, add Crafting Recipes, and register new handlers.
	 * @param e
	 */
	@EventHandler
	public void init(FMLInitializationEvent e) {
		Log.info("Hi! Hello There! ZZZZZZZZZZZZZZZZZ Sleepstone mod!");
		Log.detail("Init Start");
		preInitPotions();
		proxy.init(e);
		Log.detail("Init End");
	}

	/**
	 * Communicate with other mods and adjust setup.
	 * @param e
	 */
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		Log.detail("PostInit Start");
		proxy.postInit(e);
		Log.detail("PostInit End");
	}

	private void preInitPotions() {
		Log.detail("Potions Start - length: " + Potion.potionTypes.length);
		Potion[] potionTypes = null;
		for (Field field : Potion.class.getDeclaredFields()) {
			field.setAccessible(true);
			try {
				if (field.getName().equalsIgnoreCase("potionTypes")
						|| field.getName().equalsIgnoreCase("field_76425_a")) {
					Field modField = Field.class.getDeclaredField("modifiers");
					modField.setAccessible(true);
					modField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
					potionTypes = (Potion[]) field.get(null);
					final Potion[] newPotionTypes = new Potion[256];
					System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
					field.set(null, newPotionTypes);
					break;
				}
			} catch (Exception exc) {
				System.err.println("Error (reflection) - " + exc);
				System.out.println("Error with PotionTypes - " + exc);
			}
		}
		Log.detail("Potions End - length: " + Potion.potionTypes.length);

		int warpSicknessId = -1;
		for (int i = 0; i < Potion.potionTypes.length; i++) {
			if (Potion.potionTypes[i] == null) {
				warpSicknessId = i;
				break;
			}
		}
		if (warpSicknessId == -1) {
			throw new IndexOutOfBoundsException();
		}
		NovelPotion.warpSickness = new NovelPotion(warpSicknessId, false, 0);
		NovelPotion.warpSickness.setPotionName("potion.warpingsickness");
		NovelPotion.warpSickness.setIconIndex(5, 1);
	}
}
