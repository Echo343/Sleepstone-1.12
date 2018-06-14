package com.blargsworkshop.sleepstone.items.airmattress;

import java.util.Random;

import javax.annotation.Nullable;

import com.blargsworkshop.sleepstone.ModItems;

import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class AirMattressBlock extends BlockBed {
	private static final String UNLOCALIZED_NAME = "airmattress";
	private static final String REGISTRY_NAME = "airmattressblock";

	public AirMattressBlock() {
		super();
		this.setUnlocalizedName(UNLOCALIZED_NAME);
		this.setRegistryName(REGISTRY_NAME);
		this.setCreativeTab(ModItems.tabSleepstone);
		this.setHardness(0.01f);
	}
	
	@Override
	public boolean isBed(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity player)
    {
        return true;
    }
	
	/**
     * Get the Item that this Block should drop when harvested.
     */
    @Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return state.getValue(PART) == BlockBed.EnumPartType.FOOT ? Items.AIR : ModItems.Items.airMattress;
    }
    
    /**
     * Spawns this Block's drops into the World as EntityItems.
     */
    @Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
    {
        if (state.getValue(PART) == BlockBed.EnumPartType.HEAD)
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            EnumDyeColor enumdyecolor = tileentity instanceof TileEntityBed ? ((TileEntityBed)tileentity).getColor() : EnumDyeColor.RED;
            spawnAsEntity(worldIn, pos, new ItemStack(ModItems.Items.airMattress, 1, enumdyecolor.getMetadata()));
        }
    }
    
    @Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        BlockPos blockpos = pos;

        if (state.getValue(PART) == BlockBed.EnumPartType.FOOT)
        {
            blockpos = pos.offset((EnumFacing)state.getValue(FACING));
        }

        TileEntity tileentity = worldIn.getTileEntity(blockpos);
        EnumDyeColor enumdyecolor = tileentity instanceof TileEntityBed ? ((TileEntityBed)tileentity).getColor() : EnumDyeColor.RED;
        return new ItemStack(ModItems.Items.airMattress, 1, enumdyecolor.getMetadata());
    }
    
    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    @Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new AirMattressTileEntity();
    }

}
