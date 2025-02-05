package com.enderzombi102.loadercomplex.forge182.impl.block

import com.enderzombi102.loadercomplex.api.math.Direction
import com.enderzombi102.loadercomplex.api.minecraft.block.Block
import com.enderzombi102.loadercomplex.api.minecraft.block.BlockSoundGroup
import com.enderzombi102.loadercomplex.api.minecraft.util.Hand
import com.enderzombi102.loadercomplex.forge182.impl.utils.toLC
import com.enderzombi102.loadercomplex.forge182.impl.entity.ForgeEntity
import com.enderzombi102.loadercomplex.forge182.impl.entity.ForgePlayer
import com.enderzombi102.loadercomplex.forge182.impl.utils.toMC
import com.enderzombi102.loadercomplex.forge182.impl.world.ForgeWorld
import net.minecraft.block.BlockState
import net.minecraft.block.Material
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.ActionResult
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.World
import java.util.*
import net.minecraft.sound.BlockSoundGroup as McBlockSoundGroup
import net.minecraft.util.Hand as McHand


@Suppress("OVERRIDE_DEPRECATION")
class ForgeBlock(private val blockImpl: Block) : net.minecraft.block.Block( createSettings(blockImpl) ) {
	init {
		blockImpl.implementationBlock = this
	}

	// logic method overrides
	fun isEqualTo(block: net.minecraft.block.Block?): Boolean {
		return block is ForgeBlock && block.blockImpl === this.blockImpl
	}

	override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: McHand, hit: BlockHitResult ): ActionResult {
		return blockImpl.onBlockInteracted(
			ForgeWorld(world),
			pos.toLC(),
			ForgeBlockState(state),
			ForgePlayer(player),
			Hand.valueOf(hand.name),
			Direction.valueOf(hit.side.name),
			hit.pos.x, hit.pos.y, hit.pos.z
		).toMC()
	}

	override fun onSteppedOn(world: World, pos: BlockPos, state: BlockState, entity: Entity) {
		blockImpl.onSteppedOn(
			ForgeWorld(world),
			pos.toLC(),
			ForgeEntity(entity)
		)
	}

	override fun onBreak(world: World, pos: BlockPos, state: BlockState, player: PlayerEntity) {
		blockImpl.onBreak(
			ForgeWorld(world),
			pos.toLC(),
			ForgeBlockState(state),
			ForgePlayer(player)
		)
	}

	override fun onEntityCollision(state: BlockState, world: World, pos: BlockPos, entity: Entity) {
		blockImpl.onEntityCollision(
			ForgeWorld(world),
			pos.toLC(),
			ForgeBlockState(state),
			ForgeEntity(entity)
		)
	}

	override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
		blockImpl.onRandomTick(
			ForgeWorld(world),
			pos.toLC(),
			ForgeBlockState(state),
			random
		)
	}

	// getter methods overrides
	override fun getSoundGroup(state: BlockState): McBlockSoundGroup {
		return when (blockImpl.soundGroup) {
			BlockSoundGroup.WOOD -> McBlockSoundGroup.WOOD
			BlockSoundGroup.GRAVEL -> McBlockSoundGroup.GRAVEL
			BlockSoundGroup.GRASS -> McBlockSoundGroup.GRASS
			BlockSoundGroup.METAL -> McBlockSoundGroup.METAL
			BlockSoundGroup.GLASS -> McBlockSoundGroup.GLASS
			BlockSoundGroup.CLOTH -> McBlockSoundGroup.WOOL
			BlockSoundGroup.SAND -> McBlockSoundGroup.SAND
			BlockSoundGroup.SNOW -> McBlockSoundGroup.SNOW
			BlockSoundGroup.LADDER -> McBlockSoundGroup.LADDER
			BlockSoundGroup.ANVIL -> McBlockSoundGroup.ANVIL
			BlockSoundGroup.SLIME -> McBlockSoundGroup.SLIME
			else      /* STONE */ -> McBlockSoundGroup.STONE
		}
	}

	override fun getOpacity(state: BlockState, world: BlockView, pos: BlockPos): Int {
		return blockImpl.opacity
	}

	override fun isTranslucent(state: BlockState, world: BlockView, pos: BlockPos): Boolean {
		return blockImpl.translucent
	}

	override fun getBlastResistance(): Float {
		return blockImpl.resistance / 5.0f
	}

	companion object {
		private fun createSettings( blockImpl: Block ): Settings {
			val settings = Settings.of( Material.STONE )
				.slipperiness( blockImpl.slipperiness )
				.resistance( blockImpl.resistance )
				.strength( blockImpl.hardness )
				.luminance { blockImpl.lightLevel }
			if (! blockImpl.hasCollision ) {
				settings.noCollision()
			}

			if (! blockImpl.opaque ) {
				settings.nonOpaque()
			}

			if ( blockImpl.randomTicks ) {
				settings.ticksRandomly()
			}
			return settings
		}
	}
}
