package com.blargsworkshop.sleepstone.abilities.temporal_aid.inventory;

import javax.annotation.Nonnull;

import com.blargsworkshop.engine.sound.SoundManager;
import com.blargsworkshop.sleepstone.ModItems.Sounds;
import com.blargsworkshop.sleepstone.abilities.temporal_aid.TemporalAidProvider;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraftforge.items.ItemStackHandler;

public class AidInventory extends ItemStackHandler implements IAidInventory {
    
	private ItemStack stone = null;
	private EntityPlayer player = null;

	public AidInventory() { }
	
	public AidInventory(ItemStack stack) {
    	super(INV_SIZE);
    	stone = stack;
    	onContentsChanged(0);    	
    }
    
    @Override
	public ItemStack getStone() {
    	return stone;
    }

	@Override
	public void setTarget(@Nonnull EntityPlayer owner) {
		player = owner;
	}
    
    @Override
    protected void onContentsChanged(int slot) {
    	if (player != null && slot == 0 && !this.getStackInSlot(slot).isEmpty()) {
    		ItemStack teleportItem = this.getStackInSlot(0);
    		this.stacks.set(slot, ItemStack.EMPTY);
    		TemporalAidProvider.getTarget(player).getTarget().dropItem(teleportItem, true, false);
    		SoundManager.playSoundAtEntityFromServer(player, Sounds.pop);
    		if (player instanceof EntityPlayerMP) {
    			((EntityPlayerMP) player).connection.sendPacket(new SPacketSetSlot(player.openContainer.windowId, 0, ItemStack.EMPTY));
    		}
    	}
    }
    
    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack)
    {
        validateSlotIndex(slot);
        this.stacks.set(slot, stack);
        onContentsChanged(slot);
    }    
}
