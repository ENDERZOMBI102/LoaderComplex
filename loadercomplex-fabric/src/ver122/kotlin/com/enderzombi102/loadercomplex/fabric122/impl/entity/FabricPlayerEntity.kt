package com.enderzombi102.loadercomplex.fabric122.impl.entity

import com.enderzombi102.loadercomplex.api.math.Vec3i
import com.enderzombi102.loadercomplex.api.minecraft.entity.PlayerEntity
import com.enderzombi102.loadercomplex.api.minecraft.util.Gamemode
import com.enderzombi102.loadercomplex.fabric122.impl.utils.toLC
import com.enderzombi102.loadercomplex.fabric122.impl.utils.toMC
import net.minecraft.client.Minecraft
import net.minecraft.server.entity.living.player.ServerPlayerEntity
import net.minecraft.text.LiteralText
import net.minecraft.util.math.BlockPos
import java.util.*
import net.minecraft.entity.living.player.PlayerEntity as McPlayerEntity
import net.minecraft.world.GameMode as McGameMode


class FabricPlayerEntity( private val backingEntity: McPlayerEntity ) : FabricLivingEntity( backingEntity ), PlayerEntity {
	override fun isSleeping(): Boolean =
		backingEntity.isSleeping

	override fun getBedLocation(): Optional<Vec3i> =
		Optional.ofNullable(backingEntity.spawnPoint).map( BlockPos::toLC )

	override fun getScore(): Int =
		backingEntity.score

	override fun addScore( score: Int ) {
		backingEntity.addToScore( score )
	}

	override fun sendMessage( msg: String ) {
		backingEntity.sendMessage( LiteralText( msg ) )
	}

	override fun setRespawnPoint( pos: Vec3i) {
		backingEntity.setSpawnPoint( pos.toMC(), true )
	}

	override fun getFoodLevel(): Int {
		return backingEntity.hungerManager.foodLevel
	}

	override fun setFoodLevel( level: Int ) {
		backingEntity.hungerManager.foodLevel = level
	}

	override fun getSaturationLevel(): Float =
		backingEntity.hungerManager.saturationLevel

	override fun setSaturationLevel( level: Float ) {
		backingEntity.hungerManager.saturationLevel = level
	}

	override fun getGamemode(): Gamemode {
		val mode = if ( backingEntity.sourceWorld.isClient )
			Minecraft.getInstance().interactionManager.gameMode
		else
			(backingEntity as ServerPlayerEntity).interactionManager.gameMode

		check( mode != McGameMode.NOT_SET ) { "Player has no gamemode!" }
		return mode.toLC()
	}

	override fun getExperienceLevel(): Int =
		backingEntity.xpLevel
}
