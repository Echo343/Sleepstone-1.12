package com.blargsworkshop.sleepstone;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.blargsworkshop.sleepstone.items.gems.ItemDensityGem;
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

@Mod(modid = SleepstoneMod.MODID, name = SleepstoneMod.NAME, version = SleepstoneMod.VERSION)
public class SleepstoneMod {
	public static final String MODID = "sleepstonemod";
	public static final String NAME = "Sleepstone";
	public static final String VERSION = "1.2";
	public static SimpleNetworkWrapper networkWrapper = null;

	public static enum DEBUG {
		NONE, CASUAL, DETAIL
	};

	protected static DEBUG DEBUG_LVL = DEBUG.DETAIL;
	protected static boolean DEBUG_CHAT = true;

	@Instance
	public static SleepstoneMod instance = new SleepstoneMod();

	@SidedProxy(clientSide = "com.blargsworkshop.sleepstone.proxy..ClientProxy", serverSide = "com.blargsworkshop.sleepstone.proxy..CommonProxy")
	public static IProxy proxy;

	//Creative Mode Tabs
	public static CreativeTabs tabSleepstone;
	private static final String CREATIVE_TAB_SLEEPSTONE = "sleepstoneCreativeTab";

	//Items
	public static Item itemSleepstone;
	public static Item itemDensityGem;

	/**
	 * Read Config, create blocks, items, etc & register them.
	 * @param e
	 */
	@EventHandler
	public static void preInit(FMLPreInitializationEvent e) {
		SleepstoneMod.debug("PreInit Start", DEBUG.DETAIL);
		proxy.preInit(e);
		initCreativeTabs();
		initItems();
		SleepstoneMod.debug("PreInit End", DEBUG.DETAIL);
	}

	/**
	 * Build up data structures, add Crafting Recipes, and register new handlers.
	 * @param e
	 */
	@EventHandler
	public static void init(FMLInitializationEvent e) {
		SleepstoneMod.debug("Hi! Hello There! ZZZZZZZZZZZZZZZZZ Sleepstone mod!", DEBUG.CASUAL);
		SleepstoneMod.debug("Init Start", DEBUG.DETAIL);
		preInitPotions();
		proxy.init(e);
		initRegisters();
		initRecipes();
		SleepstoneMod.debug("Init End", DEBUG.DETAIL);
	}

	/**
	 * Communicate with other mods and adjust setup.
	 * @param e
	 */
	@EventHandler
	public static void postInit(FMLPostInitializationEvent e) {
		SleepstoneMod.debug("PostInit Start", DEBUG.DETAIL);
		proxy.postInit(e);
		SleepstoneMod.debug("PostInit End", DEBUG.DETAIL);
	}

	private static void initCreativeTabs() {
		tabSleepstone = new CreativeTabs(CREATIVE_TAB_SLEEPSTONE) {

			@Override
			public Item getTabIconItem() {
				return itemSleepstone;
			}
		};
	}

	private static void initRegisters() {
		SleepstoneMod.networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(SleepstoneMod.MODID);
		SleepstoneMod.networkWrapper.registerMessage(ItemSleepstone.class, BasicMessage.class, 0, Side.SERVER); //TODO ? id should be dynamically generated?
	}

	private static void initItems() {
		itemSleepstone = new ItemSleepstone();
		itemDensityGem = new ItemDensityGem();
		GameRegistry.registerItem(itemSleepstone, "modItemSleepstone"); //TODO something to generate random name.
		GameRegistry.registerItem(itemDensityGem, "modItemDensityGem");
	}

	private static void initRecipes() {
		GameRegistry.addShapedRecipe(new ItemStack(itemSleepstone), "srs", "gbg", "srs", 's',
				new ItemStack(Blocks.stone), 'r', new ItemStack(Blocks.redstone_block), 'g',
				new ItemStack(Items.gold_ingot), 'b', new ItemStack(Items.bed));
	}

	private static void preInitPotions() {
		SleepstoneMod.debug("Potions Start - length: " + Potion.potionTypes.length, DEBUG.DETAIL);
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
		SleepstoneMod.debug("Potions End - length: " + Potion.potionTypes.length, DEBUG.DETAIL);

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

	public static void debug(String str, DEBUG option) {
		debug(str, option, null);
	}

	public static void debug(String str, DEBUG option, EntityPlayer player) {
		switch (DEBUG_LVL) {
		case CASUAL:
			if (option == DEBUG.CASUAL) {
				if (SleepstoneMod.DEBUG_CHAT && player != null) {
					player.addChatMessage(new ChatComponentText(str));
				}
				System.out.println(str);
			}
			break;
		case DETAIL:
			if (SleepstoneMod.DEBUG_CHAT && player != null) {
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
