package com.blargsworkshop.sleepstone.items.airmattress;

import com.blargsworkshop.engine.tile.IModTileEntity;
import com.blargsworkshop.sleepstone.ModInfo;
import com.blargsworkshop.sleepstone.ModItems;

import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AirMattressTileEntity extends TileEntity implements IModTileEntity {
	private static final String RESOURCE_NAME = "airmattresste";
	
	protected EnumDyeColor color = EnumDyeColor.RED;
	
	@Override
	public String getResourceLocation() {
		return ModInfo.ID + ":" + RESOURCE_NAME;
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		if (net.getDirection() == EnumPacketDirection.CLIENTBOUND) {
			this.readFromNBT(pkt.getNbtCompound());
		}
	}
	
	/**
     * Called from Chunk.setBlockIDWithMetadata and Chunk.fillChunk, determines if this tile entity should be re-created when the ID, or Metadata changes.
     * Use with caution as this will leave straggler TileEntities, or create conflicts with other TileEntities if not used properly.
     *
     * @param world Current world
     * @param pos Tile's world position
     * @param oldState The old ID of the block
     * @param newState The new ID of the block (May be the same)
     * @return true forcing the invalidation of the existing TE, false not to invalidate the existing TE
     */
    @Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return oldState.getBlock() != newState.getBlock();
    }

    public void setItemValues(ItemStack p_193051_1_)
    {
        this.setColor(EnumDyeColor.byMetadata(p_193051_1_.getMetadata()));
    }

    @Override
	public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        if (compound.hasKey("color"))
        {
            this.color = EnumDyeColor.byMetadata(compound.getInteger("color"));
        }
    }

    @Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("color", this.color.getMetadata());
        return compound;
    }

    @Override
	public NBTTagCompound getUpdateTag()
    {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
	public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 11, this.getUpdateTag());
    }

    public EnumDyeColor getColor()
    {
        return this.color;
    }

    public void setColor(EnumDyeColor color)
    {
        this.color = color;
        this.markDirty();
    }

    @SideOnly(Side.CLIENT)
    public boolean isHeadPiece()
    {
        return BlockBed.isHeadPiece(this.getBlockMetadata());
    }

    public ItemStack getItemStack()
    {
        return new ItemStack(ModItems.Items.airMattress, 1, this.color.getMetadata());
    }

}
