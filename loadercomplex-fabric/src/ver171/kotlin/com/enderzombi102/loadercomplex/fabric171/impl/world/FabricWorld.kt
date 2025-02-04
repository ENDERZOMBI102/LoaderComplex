package com.enderzombi102.loadercomplex.fabric171.impl.world

import com.enderzombi102.loadercomplex.api.minecraft.block.BlockState
import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity
import com.enderzombi102.loadercomplex.api.minecraft.entity.PlayerEntity
import com.enderzombi102.loadercomplex.api.minecraft.util.Difficulty
import com.enderzombi102.loadercomplex.api.minecraft.util.Position
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier
import com.enderzombi102.loadercomplex.api.minecraft.server.Server
import com.enderzombi102.loadercomplex.api.math.Direction
import com.enderzombi102.loadercomplex.fabric171.impl.server.FabricServer
import com.enderzombi102.loadercomplex.fabric171.impl.block.FabricBlockState
import com.enderzombi102.loadercomplex.fabric171.impl.utils.toMC
import com.enderzombi102.loadercomplex.api.minecraft.world.World
import net.minecraft.sound.SoundCategory
import net.minecraft.util.registry.Registry
import net.minecraft.world.World as McWorld
import net.minecraft.entity.Entity as McEntity
import net.minecraft.block.BlockState as McBlockState

class FabricWorld( private val backingWorld: McWorld ) : World {
	override fun spawn( entity: Entity, pos: Position ) {
		entity.position = pos
		backingWorld.spawnEntity( entity.getObject() as McEntity )
	}

	override fun isClient(): Boolean =
		backingWorld.isClient

	override fun isHardcore(): Boolean =
		backingWorld.levelProperties.isHardcore

	override fun isSunny(): Boolean =
		backingWorld.isDay

	override fun isRaining(): Boolean =
		backingWorld.isRaining

	override fun isThundering(): Boolean =
		backingWorld.isThundering

	override fun isAir( pos: Position ): Boolean =
		backingWorld.isAir( pos.toMC() )

	@Suppress( "DEPRECATION" )
	override fun isPositionLoaded( pos: Position ): Boolean =
		backingWorld.isChunkLoaded( pos.toMC() )

	override fun hasBlockEntity( pos: Position ): Boolean =
		backingWorld.getBlockEntity( pos.toMC() ) != null

	override fun hasSkyAccess( pos: Position ): Boolean =
		backingWorld.isSkyVisible( pos.toMC() )

	override fun canSnowFall( pos: Position ): Boolean =
		backingWorld.getBiome( pos.toMC() ).canSetSnow( backingWorld, pos.toMC() )

	override fun getBlockState( pos: Position ): BlockState =
		FabricBlockState( backingWorld.getBlockState( pos.toMC() ) )

	override fun setBlockState( pos: Position, state: BlockState ) =
		backingWorld.setBlockState( pos.toMC(), state.getObject() as McBlockState )

	override fun breakBlock( pos: Position, dropItems: Boolean ) =
		backingWorld.breakBlock( pos.toMC(), dropItems )

	override fun removeBlock( pos: Position ): Boolean =
		backingWorld.removeBlock( pos.toMC(), false )

	override fun getServer(): Server? =
		backingWorld.server?.let( ::FabricServer )

	override fun getSpawnLocation(): Position =
		backingWorld.levelProperties.let { Position( it.spawnX, it.spawnY, it.spawnZ ) }

	override fun getDifficulty(): Difficulty =
		Difficulty.valueOf( backingWorld.difficulty.name )

	override fun getRedstonePower( pos: Position, direction: Direction): Int {
		return backingWorld.getEmittedRedstonePower( pos.toMC(), direction.toMC() )
	}

	override fun playsound( player: PlayerEntity, x: Double, y: Double, z: Double, sound: ResourceIdentifier, volume: Float, pitch: Float ) {
		val event = Registry.SOUND_EVENT[ sound.toMC() ]
		requireNotNull( event ) { String.format( "SoundEvent \"%s\" was not found!", sound ) }
		backingWorld.playSound(
			player.toMC(),
			x, y, z,
			event,
			SoundCategory.MASTER,
			volume, pitch
		)
	}

	override fun getObject(): Any =
		this.backingWorld
}
