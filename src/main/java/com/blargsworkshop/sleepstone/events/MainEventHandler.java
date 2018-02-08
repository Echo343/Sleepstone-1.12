package com.blargsworkshop.sleepstone.events;

import com.blargsworkshop.engine.event.IEventHandler;
import com.blargsworkshop.engine.logger.Log;
import com.blargsworkshop.engine.utility.Utils;
import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.abilities.Ability;
import com.blargsworkshop.sleepstone.abilities.Windwalker;
import com.blargsworkshop.sleepstone.capabilites.itemstack.StoneInventoryProvider;
import com.blargsworkshop.sleepstone.capabilites.itemstack.StonePropertiesProvider;
import com.blargsworkshop.sleepstone.capabilites.player.AbilityStatusProvider;
import com.blargsworkshop.sleepstone.capabilites.player.IAbilityStatus;
import com.blargsworkshop.sleepstone.items.stone.Sleepstone;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * EventHandler
 */
public class MainEventHandler implements IEventHandler {
	
	@SubscribeEvent
	public void attachCapabilityToEntity(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof EntityPlayer) {
			event.addCapability(new ResourceLocation(ModInfo.ID, "player_props"), new AbilityStatusProvider((EntityPlayer) event.getObject()));
		}
	}
	
	@SubscribeEvent
	public void attachCapabilityToItemStack(AttachCapabilitiesEvent<ItemStack> event) {
		if (event.getObject().getItem() instanceof Sleepstone) {
			event.addCapability(new ResourceLocation(ModInfo.ID, "sleepstone_inventory"), new StoneInventoryProvider(event.getObject()));
			event.addCapability(new ResourceLocation(ModInfo.ID, "sleepstone_properties"), new StonePropertiesProvider(event.getObject()));
		}
	}
	
	@SubscribeEvent
	public void onEntityContructing(EntityConstructing event) {
		if (event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			player.getAttributeMap().registerAttribute(Windwalker.STEP_HEIGHT);
		}
	}
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (Utils.isServer(event.getEntity().getEntityWorld())) {
			if (event.getEntity() instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.getEntity();
				AbilityStatusProvider.getCapability(player).syncAll();
			}
		}
	}
	
	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event) {
		if (event.isWasDeath()) {
			IStorage<IAbilityStatus> dataStore = AbilityStatusProvider.ABILITY_STATUS_CAPABILITY.getStorage();
			NBTBase data = dataStore.writeNBT(AbilityStatusProvider.ABILITY_STATUS_CAPABILITY, AbilityStatusProvider.getCapability(event.getOriginal()), null);
			dataStore.readNBT(AbilityStatusProvider.ABILITY_STATUS_CAPABILITY, AbilityStatusProvider.getCapability(event.getEntityPlayer()), null, data);
		}
	}
	
	@SubscribeEvent
	public void onLivingFallEvent(LivingFallEvent event) {
		if (Utils.isServer(event.getEntity().getEntityWorld()) && event.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntity();
			if (event.getDistance() > 3.0F && AbilityStatusProvider.getCapability(player).isAbilityAvailable(Ability.ETHEREAL_FEET)) {
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
			if (AbilityStatusProvider.getCapability(player).isAbilityAvailable(Ability.PRECOGNITION)) {
				if (isDodgeableAttack(event.getSource())) {
					double dodgeChance = Math.random();
					if (dodgeChance < 0.5) {
						event.setCanceled(true);
						// TODO change to actual message
						Log.debug("Dodged the " + event.getSource().getDamageType(), player);
					}
				}
			}
			if (AbilityStatusProvider.getCapability(player).isAbilityAvailable(Ability.VENOM_IMMUNITY)) {
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
	
	@SubscribeEvent
	public void onLivingEntityDrops(LivingDropsEvent event) {
		if (!Utils.isServer(event.getEntity().getEntityWorld())) { return; }
		MobDrops.handleGlobalGemDropRates(event);
		MobDrops.handleFireSeedDropRates(event);
		MobDrops.handleEtherealGemDropRates(event);
		MobDrops.handleEnderShardDropRates(event);
	}
}