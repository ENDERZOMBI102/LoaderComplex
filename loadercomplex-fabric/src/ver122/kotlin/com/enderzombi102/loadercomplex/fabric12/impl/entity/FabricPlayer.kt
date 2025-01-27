package com.enderzombi102.loadercomplex.fabric12.impl.entity

import com.enderzombi102.loadercomplex.api.minecraft.entity.PlayerEntity
import com.enderzombi102.loadercomplex.api.minecraft.util.Gamemode
import com.enderzombi102.loadercomplex.api.minecraft.util.Position
import com.enderzombi102.loadercomplex.fabric12.impl.utils.toLC
import com.enderzombi102.loadercomplex.fabric12.impl.utils.toMC
import net.minecraft.client.Minecraft
import net.minecraft.server.entity.living.player.ServerPlayerEntity
import net.minecraft.text.LiteralText
import net.minecraft.world.GameMode as McGameMode
import net.minecraft.entity.living.player.PlayerEntity as McPlayerEntity


class FabricPlayer( private val backingEntity: McPlayerEntity ) : FabricLivingEntity( backingEntity ), PlayerEntity {
	override fun isSleeping(): Boolean {
		return backingEntity.isSleeping
	}

	override fun getBedLocation(): Position {
		return backingEntity.spawnPoint.toLC()
	}

	override fun getScore(): Int {
		return backingEntity.score
	}

	override fun addScore( score: Int ) {
		backingEntity.addToScore( score )
	}

	override fun sendMessage( msg: String ) {
		backingEntity.sendMessage( LiteralText( msg ) )
	}

	override fun setRespawnPoint( pos: Position ) {
		backingEntity.setSpawnPoint( pos.toMC(), true )
	}

	override fun getFoodLevel(): Int {
		return backingEntity.hungerManager.foodLevel
	}

	override fun setFoodLevel( level: Int ) {
		backingEntity.hungerManager.foodLevel = level
	}

	override fun getSaturationLevel(): Float {
		return backingEntity.hungerManager.saturationLevel
	}

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

	override fun getExperienceLevel(): Int {
		return backingEntity.xpLevel
	}
}
