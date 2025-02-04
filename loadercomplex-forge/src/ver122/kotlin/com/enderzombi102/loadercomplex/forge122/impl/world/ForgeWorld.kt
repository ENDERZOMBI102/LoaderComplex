package com.enderzombi102.loadercomplex.forge122.impl.world

import com.enderzombi102.loadercomplex.api.math.Direction
import com.enderzombi102.loadercomplex.api.math.Vec3d
import com.enderzombi102.loadercomplex.api.math.Vec3i
import com.enderzombi102.loadercomplex.api.minecraft.block.BlockState
import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity
import com.enderzombi102.loadercomplex.api.minecraft.entity.PlayerEntity
import com.enderzombi102.loadercomplex.api.minecraft.server.Server
import com.enderzombi102.loadercomplex.api.minecraft.util.Difficulty
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier
import com.enderzombi102.loadercomplex.forge122.impl.block.ForgeBlockState
import com.enderzombi102.loadercomplex.forge122.impl.server.ForgeServer
import com.enderzombi102.loadercomplex.forge122.impl.utils.toLC
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundCategory
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.registry.ForgeRegistries

class ForgeWorld(private val backingWorld: World) : com.enderzombi102.loadercomplex.api.minecraft.world.World {
	override fun spawn(entity: Entity, pos: Vec3d) {
		entity.position = pos
		backingWorld.spawnEntity(entity.getObject() as net.minecraft.entity.Entity)
	}

	override fun isClient(): Boolean {
		return backingWorld.isRemote
	}

	override fun isHardcore(): Boolean {
		return false
	}

	override fun isSunny(): Boolean {
		return backingWorld.isDaytime
	}

	override fun isRaining(): Boolean {
		return backingWorld.isRaining
	}

	override fun isThundering(): Boolean {
		return backingWorld.isThundering
	}

	override fun isAir(pos: Vec3i): Boolean {
		return backingWorld.isAirBlock(BlockPos(pos.x, pos.y, pos.z))
	}

	override fun isPositionLoaded(pos: Vec3i): Boolean {
		return backingWorld.isBlockLoaded(BlockPos(pos.x, pos.y, pos.z))
	}

	override fun hasBlockEntity(pos: Vec3i): Boolean {
		return backingWorld.getTileEntity(BlockPos(pos.x, pos.y, pos.z)) != null
	}

	override fun hasSkyAccess(pos: Vec3i): Boolean {
		return backingWorld.canSeeSky(BlockPos(pos.x, pos.y, pos.z))
	}

	override fun canSnowFall(pos: Vec3i): Boolean {
		return backingWorld.canSnowAt(BlockPos(pos.x, pos.y, pos.z), true)
	}

	override fun getBlockState(pos: Vec3i): BlockState {
		return ForgeBlockState(backingWorld.getBlockState(BlockPos(pos.x, pos.y, pos.z)))
	}

	override fun setBlockState(pos: Vec3i, state: BlockState): Boolean {
		return backingWorld.setBlockState(BlockPos(pos.x, pos.y, pos.z), state.getObject() as IBlockState)
	}

	override fun breakBlock(pos: Vec3i, dropItems: Boolean): Boolean {
		return backingWorld.destroyBlock(BlockPos(pos.x, pos.y, pos.z), dropItems)
	}

	override fun removeBlock(pos: Vec3i): Boolean {
		return backingWorld.setBlockToAir(BlockPos(pos.x, pos.y, pos.z))
	}

	override fun getServer(): Server? {
		return ForgeServer(backingWorld.minecraftServer!!)
	}

	override fun getSpawnLocation(): Vec3i {
		return backingWorld.spawnPoint.toLC()
	}

	override fun getDifficulty(): Difficulty {
		return Difficulty.valueOf(backingWorld.difficulty.name)
	}

	override fun getRedstonePower(pos: Vec3i, direction: Direction): Int {
		return backingWorld.getRedstonePower(BlockPos(pos.x, pos.y, pos.z), EnumFacing.valueOf(direction.name))
	}

	override fun playsound(
		player: PlayerEntity,
		x: Double,
		y: Double,
		z: Double,
		sound: ResourceIdentifier,
		volume: Float,
		pitch: Float
	) {
		val event = ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation(sound.namespace, sound.path))

		requireNotNull(event) { String.format("SoundEvent %s was not found!", sound) }

		backingWorld.playSound(
			player.getObject() as EntityPlayer,
			x, y, z,
			event,
			SoundCategory.MASTER,
			volume,
			pitch
		)
	}

	override fun getObject(): Any {
		return this.backingWorld
	}
}
