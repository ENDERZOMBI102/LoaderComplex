package com.enderzombi102.loadercomplex.forge18.impl.block;

import com.enderzombi102.loadercomplex.api.block.Block;
import com.enderzombi102.loadercomplex.api.utils.Callable;
import com.enderzombi102.loadercomplex.forge18.impl.entity.ForgeEntity;
import com.enderzombi102.loadercomplex.forge18.impl.entity.ForgePlayer;
import com.enderzombi102.loadercomplex.forge18.impl.utils.BlockUtils;
import com.enderzombi102.loadercomplex.forge18.impl.world.ForgeWorld;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ForgeBlock extends net.minecraft.block.Block {

	private final Block blockImpl;

	public ForgeBlock(Block block) {
		super(
			( (Callable<Settings>) () -> {
				var settings =  Settings.of( Material.STONE )
					.slipperiness( block.slipperiness )
					.resistance( block.resistance )
					.strength( block.hardness )
					.luminance( state -> block.lightLevel );

				if (! block.hasCollision )
					settings.noCollision();

				if (! block.opaque )
					settings.nonOpaque();

				if ( block.randomTicks )
					settings.ticksRandomly();

				return settings;
			} ).call()
		);
		this.blockImpl = block;
		block.implementationBlock = this;
	}

	// logic method overrides


	public boolean isEqualTo(net.minecraft.block.Block block) {
		return block instanceof ForgeBlock && ( (ForgeBlock) block ).blockImpl == this.blockImpl;
	}

	@Override
	public @NotNull ActionResult onUse(@NotNull BlockState state, @NotNull World world, @NotNull BlockPos pos, @NotNull PlayerEntity player, Hand hand, BlockHitResult hit) {
		return this.blockImpl.OnBlockInteracted(
			new ForgeWorld( world ),
			BlockUtils.toPosition( pos ),
			new ForgeBlockstate( state ),
			new ForgePlayer( player ),
			com.enderzombi102.loadercomplex.api.utils.Hand.valueOf( hand.name() ),
			com.enderzombi102.loadercomplex.api.utils.Direction.valueOf( hit.getSide().name() ),
			hit.getBlockPos().getX(), hit.getBlockPos().getY(), hit.getBlockPos().getZ()
		) ? ActionResult.SUCCESS : ActionResult.PASS;
	}

	@Override
	public void onSteppedOn(@NotNull World world, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Entity entity) {
		this.blockImpl.OnSteppedOn(
			new ForgeWorld( world ),
			BlockUtils.toPosition( pos ),
			new ForgeEntity( entity )
		);
	}

	@Override
	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		this.blockImpl.OnBreak(
			new ForgeWorld( world ),
			BlockUtils.toPosition( pos ),
			new ForgeBlockstate( state ),
			new ForgePlayer( player )
		);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		this.blockImpl.OnEntityCollision(
			new ForgeWorld( world ),
			BlockUtils.toPosition( pos ),
			new ForgeBlockstate( state ),
			new ForgeEntity( entity )
		);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		this.blockImpl.OnRandomTick(
			new ForgeWorld( world ),
			BlockUtils.toPosition( pos ),
			new ForgeBlockstate( state ),
			random
		);
	}

	// getter methods overrides

	@Override
	public BlockSoundGroup getSoundGroup(BlockState state) {
		return switch (this.blockImpl.soundGroup) {
			case WOOD -> BlockSoundGroup.WOOD;
			case GRAVEL -> BlockSoundGroup.GRAVEL;
			case GRASS -> BlockSoundGroup.GRASS;
			default -> /* case STONE: */ BlockSoundGroup.STONE;
			case METAL -> BlockSoundGroup.METAL;
			case GLASS -> BlockSoundGroup.GLASS;
			case CLOTH -> BlockSoundGroup.WOOL;
			case SAND -> BlockSoundGroup.SAND;
			case SNOW -> BlockSoundGroup.SNOW;
			case LADDER -> BlockSoundGroup.LADDER;
			case ANVIL -> BlockSoundGroup.ANVIL;
			case SLIME -> BlockSoundGroup.SLIME;
		};
	}

	@Override
	public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
		return this.blockImpl.opacity;
	}

	@Override
	public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
		return this.blockImpl.translucent;
	}

	@Override
	public float getBlastResistance() {
		return this.blockImpl.resistance / 5.0F;
	}
}
