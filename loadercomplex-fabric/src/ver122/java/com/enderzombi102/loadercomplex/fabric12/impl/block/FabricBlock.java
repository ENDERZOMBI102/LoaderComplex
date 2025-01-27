package com.enderzombi102.loadercomplex.fabric12.impl.block;

import com.enderzombi102.loadercomplex.api.minecraft.block.Block;
import com.enderzombi102.loadercomplex.fabric12.impl.entity.FabricEntity;
import com.enderzombi102.loadercomplex.fabric12.impl.entity.FabricPlayer;
import com.enderzombi102.loadercomplex.fabric12.impl.utils.ConversionKt;
import com.enderzombi102.loadercomplex.fabric12.impl.world.FabricWorld;
import net.minecraft.block.material.Material;
import net.minecraft.block.sound.BlockSounds;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.World;

import java.util.Random;


@SuppressWarnings( "deprecation" )
public class FabricBlock extends net.minecraft.block.Block {
	private final Block blockImpl;

	public FabricBlock( Block block ) {
		super( Material.STONE );
		this.blockImpl = block;
		block.implementationBlock = this;
		this.slipperiness = this.blockImpl.slipperiness;
		this.gravity = this.blockImpl.particleGravity;
	}

	// logic method overrides

	@Override
	public boolean is( net.minecraft.block.Block block ) {
		return block instanceof FabricBlock && ( (FabricBlock) block ).blockImpl == this.blockImpl;
	}

	@Override
	public boolean use( World world, BlockPos pos, BlockState state, PlayerEntity player, InteractionHand hand, Direction face, float hitX, float hitY, float hitZ ) {
		return this.blockImpl.onBlockInteracted(
			new FabricWorld( world ),
			ConversionKt.toLC( pos ),
			new FabricBlockstate( state ),
			new FabricPlayer( player ),
			com.enderzombi102.loadercomplex.api.minecraft.util.Hand.valueOf( hand.name() ),
			com.enderzombi102.loadercomplex.api.minecraft.util.Direction.valueOf( face.name() ),
			hitX, hitY, hitZ
		);
	}

	@Override
	public void onSteppedOn( World world, BlockPos pos, Entity entity ) {
		this.blockImpl.onSteppedOn( new FabricWorld( world ), ConversionKt.toLC( pos ), new FabricEntity( entity ) );
	}

	@Override
	public void beforeMinedByPlayer( World world, BlockPos pos, BlockState state, PlayerEntity player ) {
		this.blockImpl.onBreak(
			new FabricWorld( world ),
			ConversionKt.toLC( pos ),
			new FabricBlockstate( state ),
			new FabricPlayer( player )
		);
	}

	public void onEntityCollision( World world, BlockPos pos, BlockState state, Entity entity ) {
		this.blockImpl.onEntityCollision(
			new FabricWorld( world ),
			ConversionKt.toLC( pos ),
			new FabricBlockstate( state ),
			new FabricEntity( entity )
		);
	}

	@Override
	public void randomTick( World world, BlockPos pos, BlockState state, Random random ) {
		this.blockImpl.onRandomTick(
			new FabricWorld( world ),
			ConversionKt.toLC( pos ),
			new FabricBlockstate( state ),
			random
		);
	}

	// getter methods overrides

	@Override
	public float getMiningSpeed( BlockState state, World world, BlockPos pos ) {
		return this.blockImpl.hardness;
	}


	@Override
	public BlockSounds getSounds() {
		switch ( this.blockImpl.soundGroup ) {
			case WOOD:
				return BlockSounds.WOOD;
			case GRAVEL:
				return BlockSounds.GRAVEL;
			case GRASS:
				return BlockSounds.GRASS;
			default: // case STONE:
				return BlockSounds.STONE;
			case METAL:
				return BlockSounds.METAL;
			case GLASS:
				return BlockSounds.GLASS;
			case CLOTH:
				return BlockSounds.CLOTH;
			case SAND:
				return BlockSounds.SAND;
			case SNOW:
				return BlockSounds.SNOW;
			case LADDER:
				return BlockSounds.LADDER;
			case ANVIL:
				return BlockSounds.ANVIL;
			case SLIME:
				return BlockSounds.SLIME;
		}
	}

	/**
	 * isOpaque
	 */
	@Override
	public boolean isOpaque( BlockState state ) {
		return this.blockImpl.opaque;
	}

	@Override
	public int getOpacity( BlockState state ) {
		return this.blockImpl.opacity;
	}

	@Override
	public boolean isTranslucent( BlockState state ) {
		return this.blockImpl.translucent;
	}

	@Override
	public int getLight( BlockState state ) {
		return this.blockImpl.lightLevel;
	}

	@Override
	public boolean usesNeighborLight( BlockState state ) {
		return this.blockImpl.useNeighborLight;
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
	public boolean isSolid( BlockState state ) {
		return this.blockImpl.hasCollision;
	}

	@Override
	public boolean hasBlockEntity() {
		return this.blockImpl.blockEntity;
	}
}
