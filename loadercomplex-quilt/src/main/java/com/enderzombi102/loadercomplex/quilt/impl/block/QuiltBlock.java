package com.enderzombi102.loadercomplex.quilt.impl.block;

import com.enderzombi102.enderlib.interfaces.Getter;
import com.enderzombi102.loadercomplex.minecraft.block.Block;
import com.enderzombi102.loadercomplex.minecraft.util.Direction;
import com.enderzombi102.loadercomplex.quilt.impl.entity.QuiltEntity;
import com.enderzombi102.loadercomplex.quilt.impl.entity.QuiltPlayer;
import com.enderzombi102.loadercomplex.quilt.impl.utils.BlockUtils;
import com.enderzombi102.loadercomplex.quilt.impl.world.QuiltWorld;
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
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import java.util.Random;

@SuppressWarnings("deprecation")
public class QuiltBlock extends net.minecraft.block.Block {

	private final Block blockImpl;

	public QuiltBlock(Block block) {
		super(
			( (Getter<QuiltBlockSettings>) () -> {
				var settings =  QuiltBlockSettings.of( Material.STONE )
					.slipperiness( block.slipperiness )
					.hardness( block.hardness )
					.strength( block.hardness )
					.resistance( block.resistance )
					.collidable( block.hasCollision )
					.luminance( state -> block.lightLevel );

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
		return block instanceof QuiltBlock && ( (QuiltBlock) block ).blockImpl == this.blockImpl;
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		return this.blockImpl.OnBlockInteracted(
			new QuiltWorld( world ),
			BlockUtils.toPosition( pos ),
			new QuiltBlockstate( state ),
			new QuiltPlayer( player ),
			com.enderzombi102.loadercomplex.minecraft.util.Hand.valueOf( hand.name() ),
			Direction.valueOf( hit.getSide().name() ),
			hit.getBlockPos().getX(), hit.getBlockPos().getY(), hit.getBlockPos().getZ()
		) ? ActionResult.SUCCESS : ActionResult.PASS;
	}

	@Override
	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
		this.blockImpl.OnSteppedOn(
			new QuiltWorld( world ),
			BlockUtils.toPosition( pos ),
			new QuiltEntity( entity )
		);
	}

	@Override
	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		this.blockImpl.OnBreak(
			new QuiltWorld( world ),
			BlockUtils.toPosition( pos ),
			new QuiltBlockstate( state ),
			new QuiltPlayer( player )
		);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		this.blockImpl.OnEntityCollision(
			new QuiltWorld( world ),
			BlockUtils.toPosition( pos ),
			new QuiltBlockstate( state ),
			new QuiltEntity( entity )
		);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		this.blockImpl.OnRandomTick(
			new QuiltWorld( world ),
			BlockUtils.toPosition( pos ),
			new QuiltBlockstate( state ),
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
