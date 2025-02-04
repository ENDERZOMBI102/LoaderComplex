package com.enderzombi102.loadercomplex.fabric122.impl.block

import com.enderzombi102.loadercomplex.api.minecraft.block.Block
import com.enderzombi102.loadercomplex.api.minecraft.block.BlockSoundGroup
import com.enderzombi102.loadercomplex.api.minecraft.util.ActionResult
import com.enderzombi102.loadercomplex.fabric122.impl.entity.FabricEntity
import com.enderzombi102.loadercomplex.fabric122.impl.entity.FabricPlayerEntity
import com.enderzombi102.loadercomplex.fabric122.impl.utils.toLC
import com.enderzombi102.loadercomplex.fabric122.impl.world.FabricWorld
import net.minecraft.entity.living.player.PlayerEntity as McPlayerEntity
import net.minecraft.entity.Entity as McEntity
import net.minecraft.block.sound.BlockSounds as McBlockSounds
import net.minecraft.block.state.BlockState as McBlockState
import net.minecraft.block.material.Material
import net.minecraft.block.Block as McBlock
import net.minecraft.util.math.Direction as McDirection
import net.minecraft.util.math.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.World

import java.util.*


class FabricBlock( private val blockImpl: Block ) : McBlock( Material.STONE ) {
	init {
		blockImpl.implementationBlock = this
		this.slipperiness = blockImpl.slipperiness
		this.gravity = blockImpl.particleGravity
	}

	// logic method overrides
	override fun `is`( block: McBlock ): Boolean =
		block is FabricBlock && block.blockImpl === this.blockImpl

	override fun use( world: World, pos: BlockPos, state: McBlockState, player: McPlayerEntity, hand: InteractionHand, face: McDirection, hitX: Float, hitY: Float, hitZ: Float ): Boolean =
		blockImpl.onBlockInteracted(
			FabricWorld( world ),
			pos.toLC(),
			FabricBlockState( state ),
			FabricPlayerEntity( player ),
			hand.toLC(),
			face.toLC(),
			hitX.toDouble(), hitY.toDouble(), hitZ.toDouble()
		) == ActionResult.SUCCESS

	override fun onSteppedOn( world: World, pos: BlockPos, entity: McEntity ) {
		blockImpl.onSteppedOn( FabricWorld( world ), pos.toLC(), FabricEntity( entity ) )
	}

	override fun beforeMinedByPlayer( world: World, pos: BlockPos, state: McBlockState, player: McPlayerEntity ) {
		blockImpl.onBreak(
			FabricWorld( world ),
			pos.toLC(),
			FabricBlockState( state ),
			FabricPlayerEntity( player )
		)
	}

	override fun onEntityCollision( world: World, pos: BlockPos, state: McBlockState, entity: McEntity ) {
		blockImpl.onEntityCollision(
			FabricWorld( world ),
			pos.toLC(),
			FabricBlockState( state ),
			FabricEntity( entity )
		)
	}

	override fun randomTick( world: World, pos: BlockPos, state: McBlockState, random: Random ) {
		blockImpl.onRandomTick(
			FabricWorld( world ),
			pos.toLC(),
			FabricBlockState( state ),
			random
		)
	}

	// getter methods overrides
	@Deprecated("Deprecated in Java")
	override fun getMiningSpeed( state: McBlockState, world: World, pos: BlockPos ): Float =
		blockImpl.hardness

	override fun getSounds(): McBlockSounds = when ( blockImpl.soundGroup ) {
		BlockSoundGroup.WOOD -> McBlockSounds.WOOD
		BlockSoundGroup.GRAVEL -> McBlockSounds.GRAVEL
		BlockSoundGroup.GRASS -> McBlockSounds.GRASS
		BlockSoundGroup.METAL -> McBlockSounds.METAL
		BlockSoundGroup.GLASS -> McBlockSounds.GLASS
		BlockSoundGroup.CLOTH -> McBlockSounds.CLOTH
		BlockSoundGroup.SAND -> McBlockSounds.SAND
		BlockSoundGroup.SNOW -> McBlockSounds.SNOW
		BlockSoundGroup.LADDER -> McBlockSounds.LADDER
		BlockSoundGroup.ANVIL -> McBlockSounds.ANVIL
		BlockSoundGroup.SLIME -> McBlockSounds.SLIME
		else -> McBlockSounds.STONE
	}

	@Deprecated("Deprecated in Java")
	override fun isOpaque( state: McBlockState ): Boolean =
		blockImpl.opaque

	@Deprecated("Deprecated in Java")
	override fun getOpacity( state: McBlockState ): Int =
		blockImpl.opacity

	@Deprecated("Deprecated in Java")
	override fun isTranslucent( state: McBlockState ): Boolean =
		blockImpl.translucent

	@Deprecated("Deprecated in Java")
	override fun getLight( state: McBlockState ): Int =
		blockImpl.lightLevel

	@Deprecated("Deprecated in Java")
	override fun usesNeighborLight( state: McBlockState ): Boolean =
		blockImpl.useNeighborLight

	override fun getBlastResistance( entity: McEntity ): Float =
		blockImpl.resistance / 5.0f

	override fun ticksRandomly(): Boolean =
		blockImpl.randomTicks

	@Deprecated("Deprecated in Java")
	override fun isSolid( state: McBlockState ): Boolean =
		blockImpl.hasCollision

	override fun hasBlockEntity(): Boolean =
		blockImpl.blockEntity
}
