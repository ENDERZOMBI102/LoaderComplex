package com.enderzombi102.loadercomplex.forge12.impl.block;

import com.enderzombi102.loadercomplex.minecraft.block.Block;
import com.enderzombi102.loadercomplex.minecraft.util.Direction;
import com.enderzombi102.loadercomplex.minecraft.util.Hand;
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

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@SuppressWarnings("deprecation")
public class ForgeBlock extends net.minecraft.block.Block {

	private final Block blockImpl;

	public ForgeBlock(Block block) {
		super(Material.ROCK);
		this.blockImpl = block;
		block.implementationBlock = this;
		this.slipperiness = this.blockImpl.slipperiness;
		this.blockParticleGravity = this.blockImpl.particleGravity;
	}

	// logic method overrides

	@Override
	@ParametersAreNonnullByDefault
	public boolean isAssociatedBlock(net.minecraft.block.Block block) {
		return block instanceof ForgeBlock && ( (ForgeBlock) block ).blockImpl == this.blockImpl;
	}

	@Override
	@ParametersAreNonnullByDefault
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return this.blockImpl.OnBlockInteracted(
			new ForgeWorld(world),
			BlockUtils.toPosition(pos),
			new ForgeBlockstate( state ),
			new ForgePlayer( player ),
			Hand.valueOf( hand.name() ),
			Direction.valueOf( facing.name() ),
			hitX, hitY, hitZ
		);
	}

	@Override
	@ParametersAreNonnullByDefault
	public void onEntityWalk(World world, BlockPos pos, Entity entity) {
		this.blockImpl.OnSteppedOn( new ForgeWorld(world), BlockUtils.toPosition(pos), new ForgeEntity(entity) );
	}

	@Override
	@ParametersAreNonnullByDefault
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		this.blockImpl.OnBreak(
			new ForgeWorld(world),
			BlockUtils.toPosition(pos),
			new ForgeBlockstate(state),
			new ForgePlayer(player)
		);
	}

	@Override
	@ParametersAreNonnullByDefault
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		this.blockImpl.OnEntityCollision(
				new ForgeWorld(world),
				BlockUtils.toPosition(pos),
				new ForgeBlockstate(state),
				new ForgeEntity(entity)
		);
	}

	@Override
	@ParametersAreNonnullByDefault
	public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
		this.blockImpl.OnRandomTick(
				new ForgeWorld(world),
				BlockUtils.toPosition(pos),
				new ForgeBlockstate(state),
				random
		);
	}

	// getter methods overrides

	@Override
	@ParametersAreNonnullByDefault
	public float getBlockHardness(IBlockState state, World world, BlockPos pos) {
		return this.blockImpl.hardness;
	}

	@Override
	public @NotNull SoundType getSoundType() {
		switch (this.blockImpl.soundGroup) {
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
	@ParametersAreNonnullByDefault
	public boolean isOpaqueCube(IBlockState state) {
		if (this.blockImpl == null) return false;
		return this.blockImpl.opaque;
	}

	@Override
	@ParametersAreNonnullByDefault
	public int getLightOpacity(IBlockState state) {
		return this.blockImpl.opacity;
	}

	@Override
	@ParametersAreNonnullByDefault
	public boolean isTranslucent(IBlockState state) {
		return this.blockImpl.translucent;
	}

	@Override
	@ParametersAreNonnullByDefault
	public int getLightValue(IBlockState state) {
		return this.blockImpl.lightLevel;
	}

	@Override
	@ParametersAreNonnullByDefault
	public boolean getUseNeighborBrightness(IBlockState state) {
		return this.blockImpl.useNeighbourLight;
	}

	@Override
	@ParametersAreNonnullByDefault
	public float getExplosionResistance(Entity entity) {
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
