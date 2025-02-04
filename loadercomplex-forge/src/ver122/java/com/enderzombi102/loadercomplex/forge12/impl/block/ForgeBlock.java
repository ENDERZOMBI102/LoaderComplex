package com.enderzombi102.loadercomplex.forge12.impl.block;

import com.enderzombi102.loadercomplex.api.minecraft.block.Block;
import com.enderzombi102.loadercomplex.api.minecraft.util.ActionResult;
import com.enderzombi102.loadercomplex.api.math.Direction;
import com.enderzombi102.loadercomplex.api.minecraft.util.Hand;
import com.enderzombi102.loadercomplex.forge12.impl.entity.ForgeEntity;
import com.enderzombi102.loadercomplex.forge12.impl.entity.ForgePlayer;
import com.enderzombi102.loadercomplex.forge12.impl.utils.BlockUtils;
import com.enderzombi102.loadercomplex.forge12.impl.world.ForgeWorld;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

@SuppressWarnings( "deprecation" )
public class ForgeBlock extends net.minecraft.block.Block {

	private final Block blockImpl;

	public ForgeBlock( Block block ) {
		super( Material.ROCK );
		this.blockImpl = block;
		block.implementationBlock = this;
		this.slipperiness = this.blockImpl.slipperiness;
		this.blockParticleGravity = this.blockImpl.particleGravity;
	}

	// logic method overrides

	@Override
	public boolean isAssociatedBlock( @NotNull net.minecraft.block.Block block ) {
		return block instanceof ForgeBlock && ((ForgeBlock) block).blockImpl == this.blockImpl;
	}

	@Override
	public boolean onBlockActivated( @NotNull World world, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityPlayer player, @NotNull EnumHand hand, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ ) {
		return this.blockImpl.onBlockInteracted(
			new ForgeWorld( world ),
			BlockUtils.toPosition( pos ),
			new ForgeBlockState( state ),
			new ForgePlayer( player ),
			Hand.valueOf( hand.name() ),
			Direction.valueOf( facing.name() ),
			hitX, hitY, hitZ
		) == ActionResult.SUCCESS;
	}

	@Override
	public void onEntityWalk( @NotNull World world, @NotNull BlockPos pos, @NotNull Entity entity ) {
		this.blockImpl.onSteppedOn( new ForgeWorld( world ), BlockUtils.toPosition( pos ), new ForgeEntity( entity ) );
	}

	@Override
	public void onBlockHarvested( @NotNull World world, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityPlayer player ) {
		this.blockImpl.onBreak(
			new ForgeWorld( world ),
			BlockUtils.toPosition( pos ),
			new ForgeBlockState( state ),
			new ForgePlayer( player )
		);
	}

	@Override
	public void onEntityCollidedWithBlock( @NotNull World world, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull Entity entity ) {
		this.blockImpl.onEntityCollision(
			new ForgeWorld( world ),
			BlockUtils.toPosition( pos ),
			new ForgeBlockState( state ),
			new ForgeEntity( entity )
		);
	}

	@Override
	public void randomTick( @NotNull World world, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull Random random ) {
		this.blockImpl.onRandomTick(
			new ForgeWorld( world ),
			BlockUtils.toPosition( pos ),
			new ForgeBlockState( state ),
			random
		);
	}

	// getter methods overrides

	@Override
	public float getBlockHardness( @NotNull IBlockState state, @NotNull World world, @NotNull BlockPos pos ) {
		return this.blockImpl.hardness;
	}

	@Override
	public @NotNull SoundType getSoundType() {
		switch ( this.blockImpl.soundGroup ) {
			case WOOD:
				return SoundType.WOOD;
			case GRAVEL:
				return SoundType.GROUND;
			case GRASS:
				return SoundType.PLANT;
			default: // case STONE:
				return SoundType.STONE;
			case METAL:
				return SoundType.METAL;
			case GLASS:
				return SoundType.GLASS;
			case CLOTH:
				return SoundType.CLOTH;
			case SAND:
				return SoundType.SAND;
			case SNOW:
				return SoundType.SNOW;
			case LADDER:
				return SoundType.LADDER;
			case ANVIL:
				return SoundType.ANVIL;
			case SLIME:
				return SoundType.SLIME;
		}
	}

	@Override
	public boolean isOpaqueCube( @NotNull IBlockState state ) {
		if ( this.blockImpl == null ) {
			return false;
		}
		return this.blockImpl.opaque;
	}

	@Override
	public int getLightOpacity( @NotNull IBlockState state ) {
		return this.blockImpl.opacity;
	}

	@Override
	public boolean isTranslucent( @NotNull IBlockState state ) {
		return this.blockImpl.translucent;
	}

	@Override
	public int getLightValue( @NotNull IBlockState state ) {
		return this.blockImpl.lightLevel;
	}

	@Override
	public boolean getUseNeighborBrightness( @NotNull IBlockState state ) {
		return this.blockImpl.useNeighborLight;
	}

	@Override
	public float getExplosionResistance( @NotNull Entity entity ) {
		return this.blockImpl.resistance / 5.0F;
	}

	@Override
	public boolean getTickRandomly() {
		return this.blockImpl.randomTicks;
	}

	@Override
	public boolean isCollidable() {
		return this.blockImpl.hasCollision;
	}

	@Override
	public boolean hasTileEntity() {
		return this.blockImpl.blockEntity;
	}
}
