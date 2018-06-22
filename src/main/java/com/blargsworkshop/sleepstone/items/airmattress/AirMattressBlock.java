package com.blargsworkshop.sleepstone.items.airmattress;

import java.util.Random;

import javax.annotation.Nullable;

import com.blargsworkshop.sleepstone.ModItems;

import net.minecraft.block.BlockBed;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AirMattressBlock extends BlockBed {
	private static final String UNLOCALIZED_NAME = "airmattress";
	private static final String REGISTRY_NAME = "airmattressblock";

	public AirMattressBlock() {
		super();
		this.setUnlocalizedName(UNLOCALIZED_NAME);
		this.setRegistryName(REGISTRY_NAME);
		this.setCreativeTab(ModItems.tabSleepstone);
		this.setHardness(0.01f);
		this.setSoundType(SoundType.CLOTH);
	}
	
	@Override
	public boolean isBed(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity player)
    {
        return true;
    }
	
	/**
     * Get the MapColor for this Block and the given BlockState
     */
    @Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        if (state.getValue(PART) == BlockBed.EnumPartType.FOOT)
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof AirMattressTileEntity)
            {
                EnumDyeColor enumdyecolor = ((AirMattressTileEntity)tileentity).getColor();
                return MapColor.getBlockColor(enumdyecolor);
            }
        }

        return MapColor.CLOTH;
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
            EnumDyeColor enumdyecolor = tileentity instanceof AirMattressTileEntity ? ((AirMattressTileEntity)tileentity).getColor() : EnumDyeColor.RED;
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
        EnumDyeColor enumdyecolor = tileentity instanceof AirMattressTileEntity ? ((AirMattressTileEntity)tileentity).getColor() : EnumDyeColor.RED;
        return new ItemStack(ModItems.Items.airMattress, 1, enumdyecolor.getMetadata());
    }
    
    /**
     * Spawns the block's drops in the world. By the time this is called the Block has possibly been set to air via
     * Block.removedByPlayer
     */
    @Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
    {
        if (state.getValue(PART) == BlockBed.EnumPartType.HEAD && te instanceof AirMattressTileEntity)
        {
        	AirMattressTileEntity tileentityairmattress = (AirMattressTileEntity)te;
            ItemStack itemstack = tileentityairmattress.getItemStack();
            spawnAsEntity(worldIn, pos, itemstack);
        }
        else
        {
            super.harvestBlock(worldIn, player, pos, state, (TileEntity)null, stack);
        }
    }
    
    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    @Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new AirMattressTileEntity();
    }
    
    /**
     * Spawn particles for when the block is destroyed. Due to the nature
     * of how this is invoked, the x/y/z locations are not always guaranteed
     * to host your block. So be sure to do proper sanity checks before assuming
     * that the location is this block.
     *
     * @param world The current world
     * @param pos Position to spawn the particle
     * @param manager A reference to the current particle manager.
     * @return True to prevent vanilla break particles from spawning.
     */
    @Override
	@SideOnly(Side.CLIENT)
    public boolean addDestroyEffects(World world, BlockPos pos, net.minecraft.client.particle.ParticleManager manager)
    {
        return true;
    }

    /**
     * Spawn a digging particle effect in the world, this is a wrapper
     * around EffectRenderer.addBlockHitEffects to allow the block more
     * control over the particles. Useful when you have entirely different
     * texture sheets for different sides/locations in the world.
     *
     * @param state The current state
     * @param worldObj The current world
     * @param target The target the player is looking at {x/y/z/side/sub}
     * @param manager A reference to the current particle manager.
     * @return True to prevent vanilla digging particles form spawning.
     */
    @Override
	@SideOnly(Side.CLIENT)
    public boolean addHitEffects(IBlockState state, World worldObj, RayTraceResult target, net.minecraft.client.particle.ParticleManager manager)
    {
        return true;
    }
    
    @Override
	public boolean addRunningEffects(IBlockState state, World world, BlockPos pos, Entity entity)
    {
        return true;
    }
    
    @Override
	public boolean addLandingEffects(IBlockState state, net.minecraft.world.WorldServer worldObj, BlockPos blockPosition, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles )
    {
        return true;
    }
}
