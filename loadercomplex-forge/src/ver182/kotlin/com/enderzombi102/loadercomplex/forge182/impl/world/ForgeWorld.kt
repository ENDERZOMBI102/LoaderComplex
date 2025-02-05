package com.enderzombi102.loadercomplex.forge182.impl.world

import com.enderzombi102.loadercomplex.api.math.Direction
import com.enderzombi102.loadercomplex.api.math.Vec3d
import com.enderzombi102.loadercomplex.api.math.Vec3i
import com.enderzombi102.loadercomplex.api.minecraft.block.BlockState
import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity
import com.enderzombi102.loadercomplex.api.minecraft.entity.PlayerEntity
import com.enderzombi102.loadercomplex.api.minecraft.server.Server
import com.enderzombi102.loadercomplex.api.minecraft.util.Difficulty
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier
import com.enderzombi102.loadercomplex.forge182.impl.server.ForgeServer
import com.enderzombi102.loadercomplex.forge182.impl.block.ForgeBlockState
import com.enderzombi102.loadercomplex.forge182.impl.utils.toLC
import com.enderzombi102.loadercomplex.forge182.impl.utils.toMC
import net.minecraft.entity.player.PlayerEntity as McPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import net.minecraft.world.Heightmap
import net.minecraft.world.World

class ForgeWorld(private val backingWorld: World) : com.enderzombi102.loadercomplex.api.minecraft.world.World {
	override fun spawn(entity: Entity, pos: Vec3d) {
		entity.position = pos
		backingWorld.spawnEntity(entity.getObject() as net.minecraft.entity.Entity)
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

	override fun isAir(pos: Vec3i): Boolean =
		backingWorld.isAir(pos.toMC())

	override fun isPositionLoaded(pos: Vec3i): Boolean =
		backingWorld.isChunkLoaded(pos.toMC())

	override fun hasBlockEntity(pos: Vec3i): Boolean =
		backingWorld.getBlockEntity(pos.toMC()) != null

	override fun hasSkyAccess(pos: Vec3i): Boolean =
		backingWorld.isSkyVisible(pos.toMC())

	override fun canSnowFall(pos: Vec3i): Boolean =
		backingWorld
			.getBiome(pos.toMC())
			.value()
			.canSetSnow(this.backingWorld, pos.toMC())

	override fun getBlockState(pos: Vec3i): BlockState =
		ForgeBlockState(backingWorld.getBlockState(pos.toMC()))

	override fun setBlockState(pos: Vec3i, state: BlockState): Boolean =
		backingWorld.setBlockState(pos.toMC(), state.getObject() as net.minecraft.block.BlockState)

	override fun breakBlock(pos: Vec3i, dropItems: Boolean): Boolean =
		backingWorld.breakBlock( pos.toMC(), dropItems )

	override fun removeBlock(pos: Vec3i): Boolean =
		backingWorld.removeBlock(pos.toMC(), false)

	override fun getServer(): Server =
		ForgeServer(backingWorld.server!!)

	override fun getSpawnLocation(): Vec3i =
		backingWorld.getTopPosition(Heightmap.Type.WORLD_SURFACE, BlockPos(0, 0, 0)).toLC()

	override fun getDifficulty(): Difficulty =
		Difficulty.valueOf(backingWorld.difficulty.name)

	override fun getRedstonePower(pos: Vec3i, direction: Direction): Int =
		backingWorld.getEmittedRedstonePower( pos.toMC(), net.minecraft.util.math.Direction.valueOf(direction.name) )

	override fun playsound( player: PlayerEntity, x: Double, y: Double, z: Double, sound: ResourceIdentifier, volume: Float, pitch: Float ) {
		val event = Registry.SOUND_EVENT[Identifier(sound.namespace, sound.path)]
		requireNotNull(event) { String.format("SoundEvent \"%s\" was not found!", sound) }
		backingWorld.playSound(
			player.getObject() as McPlayerEntity,
			x, y, z,
			event,
			SoundCategory.MASTER,
			volume, pitch
		)
	}

	override fun getObject(): Any =
		this.backingWorld
}
