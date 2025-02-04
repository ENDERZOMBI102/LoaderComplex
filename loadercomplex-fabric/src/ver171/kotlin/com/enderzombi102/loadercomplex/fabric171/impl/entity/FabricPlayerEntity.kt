package com.enderzombi102.loadercomplex.fabric171.impl.entity

import com.enderzombi102.loadercomplex.api.minecraft.entity.PlayerEntity
import com.enderzombi102.loadercomplex.api.minecraft.util.Gamemode
import com.enderzombi102.loadercomplex.api.minecraft.util.Position
import com.enderzombi102.loadercomplex.fabric171.impl.utils.toLC
import com.enderzombi102.loadercomplex.fabric171.impl.utils.toMC
import net.minecraft.client.MinecraftClient
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.LiteralText
import java.util.*
import net.minecraft.world.GameMode as McGameMode
import net.minecraft.entity.player.PlayerEntity as McPlayerEntity


class FabricPlayerEntity( private val backingEntity: McPlayerEntity ) : FabricLivingEntity( backingEntity ), PlayerEntity {
	override fun isSleeping(): Boolean =
		backingEntity.isSleeping

	override fun getBedLocation(): Optional<Position> =
		backingEntity.sleepingPosition.map { it.toLC() }

	override fun getScore(): Int =
		backingEntity.score

	override fun addScore( score: Int ) {
		backingEntity.addScore( score )
	}

	override fun sendMessage( msg: String ) {
		backingEntity.sendMessage( LiteralText( msg ), false )
	}

	override fun setRespawnPoint( pos: Position ) {
		backingEntity.setSleepingPosition( pos.toMC() )
	}

	override fun getFoodLevel(): Int =
		backingEntity.hungerManager.foodLevel

	override fun setFoodLevel( level: Int ) {
		backingEntity.hungerManager.foodLevel = level
	}

	override fun getSaturationLevel(): Float =
		backingEntity.hungerManager.saturationLevel

	override fun setSaturationLevel( level: Float ) {
		backingEntity.hungerManager.saturationLevel = level
	}

	override fun getGamemode(): Gamemode {
		val mode = if ( backingEntity.world.isClient )
			MinecraftClient.getInstance().interactionManager!!.currentGameMode
		else
			(backingEntity as ServerPlayerEntity).interactionManager.gameMode

		check( mode != McGameMode.DEFAULT ) { "Player has no gamemode!" }
		return mode.toLC()
	}

	override fun getExperienceLevel(): Int =
		backingEntity.experienceLevel
}
