package com.blargsworkshop.sleepstone.events;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.blargsworkshop.engine.event.IEventHandler;
import com.blargsworkshop.engine.logger.Log;
import com.blargsworkshop.engine.utility.Utils;
import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.ModItems;
import com.blargsworkshop.sleepstone.abilities.Ability;
import com.blargsworkshop.sleepstone.abilities.temporal_aid.PlayerTemporalAidProvider;
import com.blargsworkshop.sleepstone.abilities.temporal_aid.StoneTemporalAidProvider;
import com.blargsworkshop.sleepstone.abilities.windwalker.Windwalker;
import com.blargsworkshop.sleepstone.items.stone.Sleepstone;
import com.blargsworkshop.sleepstone.items.stone.inventory.StoneInventoryProvider;
import com.blargsworkshop.sleepstone.items.stone.properties.StonePropertiesProvider;
import com.blargsworkshop.sleepstone.player.AbilityStatusProvider;
import com.blargsworkshop.sleepstone.player.IAbilityStatus;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * EventHandler
 */
public class MainEventHandler implements IEventHandler {
	
	@SubscribeEvent
	public void attachCapabilityToEntity(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof EntityPlayer) {
			event.addCapability(new ResourceLocation(ModInfo.ID, "player_props"), new AbilityStatusProvider((EntityPlayer) event.getObject()));
			event.addCapability(new ResourceLocation(ModInfo.ID, "temporal_aid_target"), new PlayerTemporalAidProvider());
		}
	}
	
	@SubscribeEvent
	public void attachCapabilityToItemStack(AttachCapabilitiesEvent<ItemStack> event) {
		if (event.getObject().getItem() instanceof Sleepstone) {
			event.addCapability(new ResourceLocation(ModInfo.ID, "sleepstone_inventory"), new StoneInventoryProvider(event.getObject()));
			event.addCapability(new ResourceLocation(ModInfo.ID, "sleepstone_properties"), new StonePropertiesProvider(event.getObject()));
			event.addCapability(new ResourceLocation(ModInfo.ID, "temporal_aid_inventory"), new StoneTemporalAidProvider(event.getObject()));
		}
	}
	
	@SubscribeEvent
	public void onEntityContructing(EntityConstructing event) {
		if (event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			player.getAttributeMap().registerAttribute(Windwalker.STEP_HEIGHT);
			player.getAttributeMap().registerAttribute(Windwalker.JUMP_MOVEMENT_FACTOR);
		}
	}
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (Utils.isServer(event.getEntity().getEntityWorld())) {
			if (event.getEntity() instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.getEntity();
				AbilityStatusProvider.getAbilityStatus(player).syncAll();
			}
		}
	}
	
	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event) {
		if (event.isWasDeath()) {
			IStorage<IAbilityStatus> dataStore = AbilityStatusProvider.getCapability().getStorage();
			NBTBase data = dataStore.writeNBT(AbilityStatusProvider.getCapability(), AbilityStatusProvider.getAbilityStatus(event.getOriginal()), null);
			dataStore.readNBT(AbilityStatusProvider.getCapability(), AbilityStatusProvider.getAbilityStatus(event.getEntityPlayer()), null, data);
		}
	}
	
	@SubscribeEvent
	public void onLivingFallEvent(LivingFallEvent event) {
		if (Utils.isServer(event.getEntity().getEntityWorld()) && event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			if (event.getDistance() > 3.0F && AbilityStatusProvider.getAbilityStatus(player).isAbilityAvailable(Ability.ETHEREAL_FEET)) {
				event.setDistance(2.0F);
				Log.debug(player.getDisplayNameString() + " just fell on the server.", player);				
			}
		}
	}
	
	private boolean isDodgeableAttack(DamageSource damage) {
		if (damage.getDamageType().equalsIgnoreCase("mob") || damage.isProjectile()) {
			return true;			
		}
		return false;
	}
	
	@SubscribeEvent
	public void onLivingAttacked(LivingAttackEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer && Utils.isServer(event.getEntity().getEntityWorld())) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (AbilityStatusProvider.getAbilityStatus(player).isAbilityAvailable(Ability.PRECOGNITION)) {
				if (isDodgeableAttack(event.getSource())) {
					double dodgeChance = Math.random();
					if (dodgeChance < 0.5) {
						event.setCanceled(true);
						// TODO change to actual message
						Log.debug("Dodged the " + event.getSource().getDamageType(), player);
					}
				}
			}
			if (AbilityStatusProvider.getAbilityStatus(player).isAbilityAvailable(Ability.VENOM_IMMUNITY)) {
				Potion poison = Potion.getPotionFromResourceLocation("poison");
				Potion wither = Potion.getPotionFromResourceLocation("wither");
				if (event.getSource().equals(DamageSource.MAGIC) && player.isPotionActive(poison)) {
					player.removePotionEffect(poison);
					event.setCanceled(true);
					Log.debug("Removed poison from " + player.getDisplayNameString(), player);
				}
				else if (event.getSource().equals(DamageSource.WITHER) && player.isPotionActive(wither)) {
					player.removePotionEffect(wither);
					event.setCanceled(true);
					Log.debug("Removed wither from " + player.getDisplayNameString(), player);
				}
			}
		}
	}
	
	final static String FISHING_LOOT_TABLE = "minecraft:gameplay/fishing";
	final static String FISHING_INJECT_FILE = "fishing";
	@SubscribeEvent
	public void onLootTableLoad(LootTableLoadEvent event) {
		String name = event.getName().toString();
		
		if (name.equals(FISHING_LOOT_TABLE)) {
			event.getTable().addPool(getInjectPool(FISHING_INJECT_FILE));
		}
	}
	
	private LootPool getInjectPool(String entryName) {
		return new LootPool(new LootEntry[] { getInjectEntry(entryName, 1) }, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0, 1), "sleepstonemod_inject_pool");
	}

	private LootEntryTable getInjectEntry(String name, int weight) {
		return new LootEntryTable(new ResourceLocation(ModInfo.ID, "inject/" + name), weight, 0, new LootCondition[0], "sleepstonemod_inject_entry");
	}
	
	@SubscribeEvent
	public void onLivingEntityDrops(LivingDropsEvent event) {
		if (Utils.isServer(event.getEntity().getEntityWorld())) {
			// TODO turn into a singleton or use loot tables
			MobDrops.handleGlobalGemDropRates(event);
			MobDrops.handleFireSeedDropRates(event);
			MobDrops.handleGuardianGemDropRates(event);
			MobDrops.handleEtherealGemDropRates(event);
			MobDrops.handleEnderShardDropRates(event);
		}
	}
	
	
	
	private static final Map<String, Boolean> playerWakeUpMap = new ConcurrentHashMap<>();
	private static final Map<String, BlockPos> spawnPoints = new ConcurrentHashMap<>();

	@SubscribeEvent
	public void onPlayerWakingUp(PlayerWakeUpEvent event) {
		if (Utils.isServer(event.getEntityPlayer().getEntityWorld()) && event.shouldSetSpawn()) {
			EntityPlayer player = event.getEntityPlayer();
			BlockPos oldSpawnPoint = player.getBedLocation();
			putOldSpawnPoint(player.getDisplayNameString(), oldSpawnPoint);
			playerWakeUpMap.put(player.getDisplayNameString(), Boolean.TRUE);
		}
	}
	
	@SubscribeEvent
	public void onSetPlayerSpawn(PlayerSetSpawnEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		if (Utils.isServer(player.getEntityWorld()) && didPlayerJustWakeUp(player.getDisplayNameString())) {
			playerWakeUpMap.put(player.getDisplayNameString(), Boolean.FALSE);
			BlockPos sleptInBedLocation = player.getBedLocation();
			if (sleptInBedLocation != null) {
				Block bedType = player.getEntityWorld().getBlockState(sleptInBedLocation).getBlock();
				if (bedType != null && bedType == ModItems.Blocks.airMattress) {
					BlockPos newSpawnPoint = player.getBedLocation();
					BlockPos oldSpawnPoint = spawnPoints.get(player.getDisplayNameString());
					if (!newSpawnPoint.equals(oldSpawnPoint)) {
						player.setSpawnPoint(oldSpawnPoint, false);
					}
				}
			}
		}
	}
	
	private void putOldSpawnPoint(String playerDisplayNameString, BlockPos oldSpawnPoint) {
		if (oldSpawnPoint == null) {
			spawnPoints.remove(playerDisplayNameString);
		}
		else {
			spawnPoints.put(playerDisplayNameString, oldSpawnPoint);
		}
	}
	
	private boolean didPlayerJustWakeUp(String playerDisplayNameString) {
		return Boolean.TRUE.equals(playerWakeUpMap.get(playerDisplayNameString));
	}

}