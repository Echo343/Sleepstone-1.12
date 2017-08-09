package com.blargsworkshop.sleepstone;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.blargsworkshop.sleepstone.items.gems.ItemDensityGem;
import com.blargsworkshop.sleepstone.items.gems.support.density.DensityCraftable;
import com.blargsworkshop.sleepstone.items.stone.ItemSleepstone;
import com.blargsworkshop.sleepstone.network.BasicMessage;
import com.blargsworkshop.sleepstone.proxy.IProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ChatComponentText;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION)
public class SleepstoneMod {
	public static SimpleNetworkWrapper networkWrapper = null;

	@Instance
	public static SleepstoneMod instance = new SleepstoneMod();

	@SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.COMMON_PROXY)
	public static IProxy proxy;

	//Creative Mode Tabs
	public static CreativeTabs tabSleepstone;

	//Items
	public static Item itemSleepstone;
	public static Item itemDensityGem;
	public static Item itemDensityCraftable;

	/**
	 * Read Config, create blocks, items, etc & register them.
	 * @param e
	 */
	@EventHandler
	public static void preInit(FMLPreInitializationEvent e) {
		SleepstoneMod.debug("PreInit Start", ModInfo.DEBUG.DETAIL);
		proxy.preInit(e);
		initCreativeTabs();
		initItems();
		SleepstoneMod.debug("PreInit End", ModInfo.DEBUG.DETAIL);
	}

	/**
	 * Build up data structures, add Crafting Recipes, and register new handlers.
	 * @param e
	 */
	@EventHandler
	public static void init(FMLInitializationEvent e) {
		SleepstoneMod.debug("Hi! Hello There! ZZZZZZZZZZZZZZZZZ Sleepstone mod!", ModInfo.DEBUG.CASUAL);
		SleepstoneMod.debug("Init Start", ModInfo.DEBUG.DETAIL);
		preInitPotions();
		proxy.init(e);
		initRegisters();
		initRecipes();
		SleepstoneMod.debug("Init End", ModInfo.DEBUG.DETAIL);
	}

	/**
	 * Communicate with other mods and adjust setup.
	 * @param e
	 */
	@EventHandler
	public static void postInit(FMLPostInitializationEvent e) {
		SleepstoneMod.debug("PostInit Start", ModInfo.DEBUG.DETAIL);
		proxy.postInit(e);
		SleepstoneMod.debug("PostInit End", ModInfo.DEBUG.DETAIL);
	}

	private static void initCreativeTabs() {
		tabSleepstone = new CreativeTabs(ModInfo.CREATIVE_TAB_SLEEPSTONE) {

			@Override
			public Item getTabIconItem() {
				return itemSleepstone;
			}
		};
	}

	private static void initRegisters() {
		SleepstoneMod.networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.ID);
		SleepstoneMod.networkWrapper.registerMessage(ItemSleepstone.class, BasicMessage.class, 0, Side.SERVER); //TODO ? id should be dynamically generated?
	}

	private static void initItems() {
//		itemSleepstone = new ItemSleepstone();
//		itemDensityGem = new ItemDensityGem();
//		itemDensityCraftable = new DensityCraftable();
		GameRegistry.registerItem(itemSleepstone = new ItemSleepstone(), "modItemSleepstone"); //TODO something to generate random name.
		GameRegistry.registerItem(itemDensityGem = new ItemDensityGem(), "modItemDensityGem");
		GameRegistry.registerItem(itemDensityCraftable = new DensityCraftable(), "modItemDensityCraftable");
	}

	private static void initRecipes() {
		GameRegistry.addShapedRecipe(new ItemStack(itemSleepstone), "srs", "gbg", "srs", 's',
				new ItemStack(Blocks.stone), 'r', new ItemStack(Blocks.redstone_block), 'g',
				new ItemStack(Items.gold_ingot), 'b', new ItemStack(Items.bed));
	}

	private static void preInitPotions() {
		SleepstoneMod.debug("Potions Start - length: " + Potion.potionTypes.length, ModInfo.DEBUG.DETAIL);
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
		SleepstoneMod.debug("Potions End - length: " + Potion.potionTypes.length, ModInfo.DEBUG.DETAIL);

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

	public static void debug(String str, ModInfo.DEBUG option) {
		debug(str, option, null);
	}

	public static void debug(String str, ModInfo.DEBUG option, EntityPlayer player) {
		switch (ModInfo.DEBUG_LVL) {
		case CASUAL:
			if (option == ModInfo.DEBUG.CASUAL) {
				if (ModInfo.DEBUG_CHAT && player != null) {
					player.addChatMessage(new ChatComponentText(str));
				}
				System.out.println(str);
			}
			break;
		case DETAIL:
			if (ModInfo.DEBUG_CHAT && player != null) {
				player.addChatMessage(new ChatComponentText(str));
			}
			System.out.println(str);
			break;
		case NONE:
		default:
			break;
		}
	}
}
