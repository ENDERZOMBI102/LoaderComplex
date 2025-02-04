package com.enderzombi102.loadercomplex.fabric122.impl.world

import com.enderzombi102.loadercomplex.api.math.Vec3d
import com.enderzombi102.loadercomplex.api.math.Vec3i
import com.enderzombi102.loadercomplex.api.minecraft.block.BlockState
import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity
import com.enderzombi102.loadercomplex.api.minecraft.entity.PlayerEntity
import com.enderzombi102.loadercomplex.api.minecraft.util.Difficulty
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier
import com.enderzombi102.loadercomplex.api.minecraft.server.Server
import com.enderzombi102.loadercomplex.fabric122.impl.server.FabricServer
import com.enderzombi102.loadercomplex.fabric122.impl.block.FabricBlockState
import com.enderzombi102.loadercomplex.fabric122.impl.utils.toLC
import com.enderzombi102.loadercomplex.fabric122.impl.utils.toMC
import net.minecraft.block.state.BlockState as McBlockState
import net.minecraft.resource.Identifier
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.world.World
import com.enderzombi102.loadercomplex.api.math.Direction as McDirection
import com.enderzombi102.loadercomplex.api.minecraft.world.World as McWorld
import net.minecraft.entity.Entity as McEntity


class FabricWorld( private val backingWorld: World ) : McWorld {
	override fun spawn( entity: Entity, pos: Vec3d ) {
		entity.position = pos
		backingWorld.addEntity( entity.getObject() as McEntity )
	}

	override fun isClient(): Boolean =
		backingWorld.isClient

	override fun isHardcore(): Boolean =
		false

	override fun isSunny(): Boolean =
		backingWorld.isSunny

	override fun isRaining(): Boolean =
		backingWorld.isRaining

	override fun isThundering(): Boolean =
		backingWorld.isThundering

	override fun isAir( pos: Vec3i ): Boolean =
		backingWorld.isAir( pos.toMC() )

	override fun isPositionLoaded( pos: Vec3i ): Boolean =
		backingWorld.isChunkLoaded( pos.toMC(), true )

	override fun hasBlockEntity( pos: Vec3i ): Boolean =
		backingWorld.getBlockEntity( pos.toMC() ) != null

	override fun hasSkyAccess( pos: Vec3i ): Boolean =
		backingWorld.hasSkyAccess( pos.toMC() )

	override fun canSnowFall( pos: Vec3i ): Boolean =
		backingWorld.canSnowFall( pos.toMC(), true )

	override fun getBlockState( pos: Vec3i ): BlockState =
		FabricBlockState( backingWorld.getBlockState( pos.toMC() ) )

	override fun setBlockState( pos: Vec3i, state: BlockState) =
		backingWorld.setBlockState( pos.toMC(), state.getObject() as McBlockState)

	override fun breakBlock( pos: Vec3i, dropItems: Boolean ) =
		backingWorld.breakBlock( pos.toMC(), dropItems )

	override fun removeBlock( pos: Vec3i ): Boolean =
		backingWorld.removeBlock( pos.toMC() )

	override fun getServer(): Server =
		FabricServer( backingWorld.server!! )

	override fun getSpawnLocation(): Vec3i =
		backingWorld.spawnPoint.toLC()

	override fun getDifficulty(): Difficulty =
		Difficulty.valueOf( backingWorld.difficulty.name )

	override fun getRedstonePower( pos: Vec3i, direction: McDirection ): Int {
		return backingWorld.getDirectSignal( pos.toMC(), direction.toMC() )
	}

	override fun playsound( player: PlayerEntity, x: Double, y: Double, z: Double, sound: ResourceIdentifier, volume: Float, pitch: Float ) {
		val event = SoundEvent.REGISTRY[Identifier( sound.namespace, sound.path )]
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
