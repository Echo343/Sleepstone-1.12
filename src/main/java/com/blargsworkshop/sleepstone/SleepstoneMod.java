package com.blargsworkshop.sleepstone;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ChatComponentText;

import com.blargsworkshop.sleepstone.network.BasicMessage;
import com.blargsworkshop.sleepstone.stones.ItemSleepstone;
import com.blargsworkshop.sleepstone.interfaces.IProxy;

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

@Mod(modid = SleepstoneMod.MODID, name = SleepstoneMod.NAME, version = SleepstoneMod.VERSION)
public class SleepstoneMod {
	public static final String MODID = "sleepstonemod";
	public static final String NAME = "Sleepstone";
	public static final String VERSION = "1.2";
	public static SimpleNetworkWrapper networkWrapper = null;

	public static enum DEBUG {
		NONE, CASUAL, DETAIL
	};

	protected static DEBUG DEBUG_LVL = DEBUG.CASUAL;
	protected static boolean DEBUG_CHAT = false;

	@Instance
	public static SleepstoneMod instance = new SleepstoneMod();

	@SidedProxy(clientSide = "com.blargsworkshop.sleepstone.ClientProxy", serverSide = "com.blargsworkshop.sleepstone.ServerProxy")
	public static IProxy proxy;

	//Creative Mode Tabs
	public static CreativeTabs tabSleepstone;
	private static final String CREATIVE_TAB_SLEEPSTONE = "sleepstoneCreativeTab";

	//Items
	public static Item sleepstoneItem;

	/**
	 * Read Config, create blocks, items, etc & register them.
	 * @param e
	 */
	@EventHandler
	public static void preInit(FMLPreInitializationEvent e) {
		SleepstoneMod.debug("Forge PreInit Start", DEBUG.DETAIL, null);
		proxy.preInit(e);
		initCreativeTabs();
		initItems();
		SleepstoneMod.debug("Forge PreInit End", DEBUG.DETAIL, null);
	}

	/**
	 * Build up data structures, add Crafting Recipes, and register new handlers.
	 * @param e
	 */
	@EventHandler
	public static void init(FMLInitializationEvent e) {
		SleepstoneMod.debug("Hi! Hello There! ZZZZZZZZZZZZZZZZZ Sleepstone mod!", DEBUG.CASUAL, null);
		preInitPotions();
		proxy.init(e);
		initRegisters();
		initRecipes();
	}

	/**
	 * Communicate with other mods and adjust setup.
	 * @param e
	 */
	@EventHandler
	public static void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
	}

	private static void initCreativeTabs() {
		tabSleepstone = new CreativeTabs(CREATIVE_TAB_SLEEPSTONE) {

			@Override
			public Item getTabIconItem() {
				return sleepstoneItem;
			}
		};
	}

	private static void initRegisters() {
		SleepstoneMod.networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(SleepstoneMod.MODID);
		SleepstoneMod.networkWrapper.registerMessage(ItemSleepstone.class, BasicMessage.class, 0, Side.SERVER); //TODO ? id should be dynamically generated?
	}

	private static void initItems() {
		sleepstoneItem = new ItemSleepstone();
		GameRegistry.registerItem(sleepstoneItem, "modItemSleepstoneItem"); //TODO something to generate random name.
	}

	private static void initRecipes() {
		GameRegistry.addShapedRecipe(new ItemStack(sleepstoneItem), "srs", "gbg", "srs", 's',
				new ItemStack(Blocks.stone), 'r', new ItemStack(Blocks.redstone_block), 'g',
				new ItemStack(Items.gold_ingot), 'b', new ItemStack(Items.bed));
	}

	private static void preInitPotions() {
		SleepstoneMod.debug("Potions Start - length: " + Potion.potionTypes.length, DEBUG.DETAIL, null);
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
		SleepstoneMod.debug("Potions End - length: " + Potion.potionTypes.length, DEBUG.DETAIL, null);

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
