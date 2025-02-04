package com.enderzombi102.loadercomplex.fabric171.impl.block

import com.enderzombi102.loadercomplex.api.minecraft.block.Block
import com.enderzombi102.loadercomplex.api.minecraft.block.BlockSoundGroup
import com.enderzombi102.loadercomplex.fabric171.impl.entity.FabricEntity
import com.enderzombi102.loadercomplex.fabric171.impl.entity.FabricPlayerEntity
import com.enderzombi102.loadercomplex.fabric171.impl.utils.toLC
import com.enderzombi102.loadercomplex.fabric171.impl.utils.toMC
import com.enderzombi102.loadercomplex.fabric171.impl.world.FabricWorld
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.BlockState
import net.minecraft.block.Material
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.World
import java.util.*
import net.minecraft.block.Block as McBlock
import net.minecraft.block.BlockState as McBlockState
import net.minecraft.entity.Entity as McEntity
import net.minecraft.entity.player.PlayerEntity as McPlayerEntity
import net.minecraft.sound.BlockSoundGroup as McBlockSoundGroup
import net.minecraft.util.ActionResult as McActionResult
import net.minecraft.util.Hand as McHand


class FabricBlock( private val blockImpl: Block ) : McBlock( createSettings( blockImpl ) ) {
	init {
		blockImpl.implementationBlock = this
	}

	// region logic method overrides
	@Deprecated( "Deprecated in Java" )
	override fun onUse( state: McBlockState, world: World, pos: BlockPos, player: McPlayerEntity, hand: McHand, hit: BlockHitResult ): McActionResult =
		blockImpl.onBlockInteracted(
			FabricWorld( world ),
			pos.toLC(),
			FabricBlockState( state ),
			FabricPlayerEntity( player ),
			hand.toLC(),
			hit.side.toLC(),
			hit.pos.x, hit.pos.y, hit.pos.z
		).toMC()

	override fun onSteppedOn( world: World, pos: BlockPos, state: McBlockState, entity: McEntity ) {
		blockImpl.onSteppedOn( FabricWorld( world ), pos.toLC(), FabricEntity( entity ) )
	}

	override fun onBreak( world: World, pos: BlockPos, state: BlockState, player: PlayerEntity ) {
		blockImpl.onBreak(
			FabricWorld( world ),
			pos.toLC(),
			FabricBlockState( state ),
			FabricPlayerEntity( player )
		)
	}

	@Deprecated( "Deprecated in Java" )
	override fun onEntityCollision( state: McBlockState, world: World, pos: BlockPos, entity: McEntity ) {
		blockImpl.onEntityCollision(
			FabricWorld( world ),
			pos.toLC(),
			FabricBlockState( state ),
			FabricEntity( entity )
		)
	}

	@Deprecated( "Deprecated in Java" )
	override fun randomTick( state: McBlockState, world: ServerWorld, pos: BlockPos, random: Random ) {
		blockImpl.onRandomTick(
			FabricWorld( world ),
			pos.toLC(),
			FabricBlockState( state ),
			random
		)
	}
	// endregion

	// region getter methods overrides
	override fun getSoundGroup( state: McBlockState ): McBlockSoundGroup = when ( blockImpl.soundGroup ) {
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
		else -> McBlockSoundGroup.STONE
	}

	@Deprecated( "Deprecated in Java" )
	override fun getOpacity(state: BlockState, world: BlockView, pos: BlockPos ): Int =
		blockImpl.opacity

	override fun isTranslucent( state: BlockState, world: BlockView, pos: BlockPos ): Boolean =
		blockImpl.translucent

	override fun getBlastResistance(): Float =
		blockImpl.resistance / 5.0f
	// endregion

	companion object {
		fun createSettings( block: Block ): FabricBlockSettings {
			val settings = FabricBlockSettings.of( Material.STONE )
				.slipperiness( block.slipperiness )
				.hardness( block.hardness )
				.strength( block.hardness )
				.resistance( block.resistance )
				.collidable( block.hasCollision )
				.lightLevel { block.lightLevel }
			if (! block.opaque ) {
				settings.nonOpaque()
			}

			if ( block.randomTicks ) {
				settings.ticksRandomly()
			}

			return settings
		}
	}
}
