package com.enderzombi102.loadercomplex.fabric12.impl.block;

import com.enderzombi102.loadercomplex.fabric12.impl.entity.FabricEntity;
import com.enderzombi102.loadercomplex.fabric12.impl.entity.FabricPlayer;
import com.enderzombi102.loadercomplex.fabric12.impl.utils.BlockUtils;
import com.enderzombi102.loadercomplex.fabric12.impl.world.FabricWorld;
import com.enderzombi102.loadercomplex.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Random;

@SuppressWarnings("deprecation")
public class FabricBlock extends net.minecraft.block.Block {

	private final Block blockImpl;

	public FabricBlock( Block block ) {
		super( Material.STONE );
		this.blockImpl = block;
		block.implementationBlock = this;
		this.slipperiness = this.blockImpl.slipperiness;
		this.particleGravity = this.blockImpl.particleGravity;
	}

	// logic method overrides

	@Override
	public boolean isEqualTo( net.minecraft.block.Block block ) {
		return block instanceof FabricBlock && ( (FabricBlock) block ).blockImpl == this.blockImpl;
	}

	@Override
	public boolean use( World world, BlockPos pos, BlockState state, PlayerEntity player, Hand hand, Direction facing, float hitX, float hitY, float hitZ ) {
		return this.blockImpl.OnBlockInteracted(
			new FabricWorld( world ),
			BlockUtils.toPosition( pos ),
			new FabricBlockstate( state ),
			new FabricPlayer( player ),
			com.enderzombi102.loadercomplex.minecraft.util.Hand.valueOf( hand.name() ),
			com.enderzombi102.loadercomplex.minecraft.util.Direction.valueOf( facing.name() ),
			hitX, hitY, hitZ
		);
	}

	@Override
	public void onSteppedOn( World world, BlockPos pos, Entity entity ) {
		this.blockImpl.OnSteppedOn( new FabricWorld( world ), BlockUtils.toPosition( pos ), new FabricEntity( entity ) );
	}

	@Override
	public void onBreakByPlayer( World world, BlockPos pos, BlockState state, PlayerEntity player ) {
		this.blockImpl.OnBreak(
			new FabricWorld( world ),
			BlockUtils.toPosition( pos ),
			new FabricBlockstate( state ),
			new FabricPlayer( player )
		);
	}

	public void onEntityCollision( World world, BlockPos pos, BlockState state, Entity entity ) {
		this.blockImpl.OnEntityCollision(
			new FabricWorld( world ),
			BlockUtils.toPosition( pos ),
			new FabricBlockstate( state ),
			new FabricEntity( entity )
		);
	}

	/**
	 * onRandomTick?
	 */
	@Override
	public void onUpdateTick( World world, BlockPos pos, BlockState state, Random random ) {
		this.blockImpl.OnRandomTick(
			new FabricWorld( world ),
			BlockUtils.toPosition( pos ),
			new FabricBlockstate( state ),
			random
		);
	}

	// getter methods overrides

	@Override
	public float getHardness( BlockState state, World world, BlockPos pos ) {
		return this.blockImpl.hardness;
	}

	@Override
	public BlockSoundGroup getSoundGroup() {
		switch ( this.blockImpl.soundGroup ) {
			case WOOD:
				return BlockSoundGroup.field_12759;
			case GRAVEL:
				return BlockSoundGroup.field_12760;
			case GRASS:
				return BlockSoundGroup.field_12761;
			default: // case STONE:
				return BlockSoundGroup.STONE;
			case METAL:
				return BlockSoundGroup.field_12763;
			case GLASS:
				return BlockSoundGroup.field_12764;
			case CLOTH:
				return BlockSoundGroup.field_12765;
			case SAND:
				return BlockSoundGroup.field_12766;
			case SNOW:
				return BlockSoundGroup.field_12767;
			case LADDER:
				return BlockSoundGroup.field_12768;
			case ANVIL:
				return BlockSoundGroup.field_12769;
			case SLIME:
				return BlockSoundGroup.field_12770;
		}
	}

	/**
	 * isOpaque
	 */
	@Override
	public boolean method_11568( BlockState state ) {
		return this.blockImpl.opaque;
	}

	@Override
	public int getOpacity( BlockState state ) {
		return this.blockImpl.opacity;
	}

	@Override
	public boolean isTransluscent( BlockState state ) {
		return this.blockImpl.translucent;
	}

	@Override
	public int getLuminance( BlockState state ) {
		return this.blockImpl.lightLevel;
	}

	@Override
	public boolean useNeighbourLight( BlockState state ) {
		return this.blockImpl.useNeighbourLight;
	}

	@Override
	public float getBlastResistance( Entity entity ) {
		return this.blockImpl.resistance / 5.0F;
	}

	@Override
	public boolean ticksRandomly() {
		return this.blockImpl.randomTicks;
	}

	@Override
	public boolean hasCollision() {
		return this.blockImpl.hasCollision;
	}

	@Override
	public boolean hasBlockEntity() {
		return this.blockImpl.blockEntity;
	}

}
