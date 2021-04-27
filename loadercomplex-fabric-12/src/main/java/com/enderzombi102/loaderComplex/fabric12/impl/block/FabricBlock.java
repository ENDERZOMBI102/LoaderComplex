package com.enderzombi102.loaderComplex.fabric12.impl.block;

import com.enderzombi102.loadercomplex.abstraction.block.Block;
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

public class FabricBlock extends net.minecraft.block.Block {

	private final Block blockImpl;

	public FabricBlock(Block block) {
		super(Material.STONE);
		this.blockImpl = block;
		block.implementationBlock = this;
		this.slipperiness = this.blockImpl.slipperiness;
		this.particleGravity = this.blockImpl.particleGravity;
	}

	// logic method overrides

	@Override
	public boolean is(net.minecraft.block.Block block) {
		return block instanceof FabricBlock && ( (FabricBlock) block ).blockImpl == this.blockImpl;
	}

	@Override
	public boolean onUse(World world, BlockPos pos, BlockState state, PlayerEntity player, Hand hand, Direction facing, float hitX, float hitY, float hitZ) {
		return this.blockImpl.OnBlockInteracted(player);
	}

	@Override
	public void onSteppedOn(World world, BlockPos pos, Entity entity) {
		this.blockImpl.OnSteppedOn(entity);
	}

	@Override
	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		this.blockImpl.OnBreak(player);
	}

	public void onEntityCollision(World world, BlockPos pos, BlockState state, Entity entity) {
		this.blockImpl.OnEntityCollision(entity);
	}

	@Override
	public void randomTick(World world, BlockPos pos, BlockState state, Random random) {
		this.blockImpl.OnRandomTick(random);
	}

	// getter methods overrides

	@Override
	public float getHardness(BlockState state, World world, BlockPos pos) {
		return this.blockImpl.hardness;
	}

	@Override
	public BlockSoundGroup getSoundGroup() {
		switch (this.blockImpl.soundGroup) {
			case WOOD:
				return BlockSoundGroup.WOOD;
			case GRAVEL:
				return BlockSoundGroup.GRAVEL;
			case GRASS:
				return BlockSoundGroup.GRASS;
			default: // case STONE:
				return BlockSoundGroup.STONE;
			case METAL:
				return BlockSoundGroup.METAL;
			case GLASS:
				return BlockSoundGroup.GLASS;
			case CLOTH:
				return BlockSoundGroup.CLOTH;
			case SAND:
				return BlockSoundGroup.SAND;
			case SNOW:
				return BlockSoundGroup.SNOW;
			case LADDER:
				return BlockSoundGroup.LADDER;
			case ANVIL:
				return BlockSoundGroup.ANVIL;
			case SLIME:
				return BlockSoundGroup.SLIME;
		}
	}

	@Override
	public boolean isOpaque(BlockState state) {
		return this.blockImpl.opaque;
	}

	@Override
	public int getOpacity(BlockState state) {
		return this.blockImpl.opacity;
	}

	@Override
	public boolean isTranslucent(BlockState state) {
		return this.blockImpl.translucent;
	}

	@Override
	public int getLightLevel(BlockState state) {
		return this.blockImpl.lightLevel;
	}

	@Override
	public boolean usesNeighbourLight(BlockState state) {
		return this.blockImpl.useNeighbourLight;
	}

	@Override
	public float getBlastResistance(Entity entity) {
		return this.blockImpl.resistance / 5.0F;
	}

	@Override
	public boolean hasRandomTicks() {
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
