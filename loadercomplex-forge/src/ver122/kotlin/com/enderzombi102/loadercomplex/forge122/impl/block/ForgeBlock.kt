package com.enderzombi102.loadercomplex.forge122.impl.block

import com.enderzombi102.loadercomplex.api.math.Direction
import com.enderzombi102.loadercomplex.api.minecraft.block.Block
import com.enderzombi102.loadercomplex.api.minecraft.block.BlockSoundGroup
import com.enderzombi102.loadercomplex.api.minecraft.util.ActionResult
import com.enderzombi102.loadercomplex.api.minecraft.util.Hand
import com.enderzombi102.loadercomplex.forge122.impl.entity.ForgeEntity
import com.enderzombi102.loadercomplex.forge122.impl.entity.ForgePlayer
import com.enderzombi102.loadercomplex.forge122.impl.utils.toLC
import com.enderzombi102.loadercomplex.forge122.impl.world.ForgeWorld
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*
import net.minecraft.block.Block as McBlock


@Suppress("OVERRIDE_DEPRECATION")
class ForgeBlock( private val blockImpl: Block ) : McBlock( Material.ROCK ) {
	init {
		blockImpl.implementationBlock = this
		this.slipperiness = blockImpl.slipperiness
		this.blockParticleGravity = blockImpl.particleGravity
	}

	// logic method overrides
	override fun isAssociatedBlock( block: McBlock ): Boolean =
		block is ForgeBlock && block.blockImpl === this.blockImpl

	override fun onBlockActivated( world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float ): Boolean {
		return blockImpl.onBlockInteracted(
			ForgeWorld( world ),
			pos.toLC(),
			ForgeBlockState( state ),
			ForgePlayer( player ),
			Hand.valueOf( hand.name ),
			Direction.valueOf( facing.name ),
			hitX.toDouble(), hitY.toDouble(), hitZ.toDouble()
		) == ActionResult.SUCCESS
	}

	override fun onEntityWalk( world: World, pos: BlockPos, entity: Entity ) {
		blockImpl.onSteppedOn( ForgeWorld( world ), pos.toLC(), ForgeEntity( entity ) )
	}

	override fun onBlockHarvested( world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer ) {
		blockImpl.onBreak( ForgeWorld( world ), pos.toLC(), ForgeBlockState( state ), ForgePlayer( player ) )
	}

	override fun onEntityCollidedWithBlock( world: World, pos: BlockPos, state: IBlockState, entity: Entity ) {
		blockImpl.onEntityCollision( ForgeWorld( world ), pos.toLC(), ForgeBlockState( state ), ForgeEntity( entity ) )
	}

	override fun randomTick( world: World, pos: BlockPos, state: IBlockState, random: Random ) {
		blockImpl.onRandomTick( ForgeWorld( world ), pos.toLC(), ForgeBlockState( state ), random )
	}

	// getter methods overrides
	override fun getBlockHardness( state: IBlockState, world: World, pos: BlockPos ): Float =
		blockImpl.hardness

	override fun getSoundType(): SoundType = when ( blockImpl.soundGroup ) {
		BlockSoundGroup.WOOD -> SoundType.WOOD
		BlockSoundGroup.GRAVEL -> SoundType.GROUND
		BlockSoundGroup.GRASS -> SoundType.PLANT
		BlockSoundGroup.METAL -> SoundType.METAL
		BlockSoundGroup.GLASS -> SoundType.GLASS
		BlockSoundGroup.CLOTH -> SoundType.CLOTH
		BlockSoundGroup.SAND -> SoundType.SAND
		BlockSoundGroup.SNOW -> SoundType.SNOW
		BlockSoundGroup.LADDER -> SoundType.LADDER
		BlockSoundGroup.ANVIL -> SoundType.ANVIL
		BlockSoundGroup.SLIME -> SoundType.SLIME
		else -> SoundType.STONE
	}

	override fun isOpaqueCube( state: IBlockState ): Boolean =
		blockImpl.opaque

	override fun getLightOpacity( state: IBlockState ): Int =
		blockImpl.opacity

	override fun isTranslucent( state: IBlockState ): Boolean =
		blockImpl.translucent

	override fun getLightValue( state: IBlockState ): Int =
		blockImpl.lightLevel

	override fun getUseNeighborBrightness( state: IBlockState ): Boolean =
		blockImpl.useNeighborLight

	override fun getExplosionResistance( entity: Entity ): Float =
		blockImpl.resistance / 5.0f

	override fun getTickRandomly(): Boolean =
		blockImpl.randomTicks

	override fun isCollidable(): Boolean =
		blockImpl.hasCollision

	override fun hasTileEntity(): Boolean =
		blockImpl.blockEntity
}
