package com.enderzombi102.loadercomplex.fabric17.impl.block;

import com.enderzombi102.loadercomplex.minecraft.block.Block;
import com.enderzombi102.loadercomplex.fabric17.impl.entity.FabricEntity;
import com.enderzombi102.loadercomplex.fabric17.impl.entity.FabricPlayer;
import com.enderzombi102.loadercomplex.fabric17.impl.utils.BlockUtils;
import com.enderzombi102.loadercomplex.minecraft.util.Supplier;
import com.enderzombi102.loadercomplex.fabric17.impl.world.FabricWorld;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
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

import java.util.Random;

@SuppressWarnings("deprecation")
public class FabricBlock extends net.minecraft.block.Block {

	private final Block blockImpl;

	public FabricBlock(Block block) {
		super(
			( (Supplier<FabricBlockSettings>) () -> {
				var settings =  FabricBlockSettings.of( Material.STONE )
					.slipperiness( block.slipperiness )
					.hardness( block.hardness )
					.strength( block.hardness )
					.resistance( block.resistance )
					.collidable( block.hasCollision )
					.lightLevel( state -> block.lightLevel );


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
		return block instanceof FabricBlock && ( (FabricBlock) block ).blockImpl == this.blockImpl;
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		return this.blockImpl.OnBlockInteracted(
			new FabricWorld( world ),
			BlockUtils.toPosition( pos ),
			new FabricBlockstate( state ),
			new FabricPlayer( player ),
			com.enderzombi102.loadercomplex.minecraft.util.Hand.valueOf( hand.name() ),
			com.enderzombi102.loadercomplex.minecraft.util.Direction.valueOf( hit.getSide().name() ),
			hit.getBlockPos().getX(), hit.getBlockPos().getY(), hit.getBlockPos().getZ()
		) ? ActionResult.SUCCESS : ActionResult.PASS;
	}

	@Override
	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
		this.blockImpl.OnSteppedOn(
			new FabricWorld( world ),
			BlockUtils.toPosition( pos ),
			new FabricEntity( entity )
		);
	}

	@Override
	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		this.blockImpl.OnBreak(
			new FabricWorld( world ),
			BlockUtils.toPosition( pos ),
			new FabricBlockstate( state ),
			new FabricPlayer( player )
		);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		this.blockImpl.OnEntityCollision(
			new FabricWorld( world ),
			BlockUtils.toPosition( pos ),
			new FabricBlockstate( state ),
			new FabricEntity( entity )
		);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		this.blockImpl.OnRandomTick(
			new FabricWorld( world ),
			BlockUtils.toPosition( pos ),
			new FabricBlockstate( state ),
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
