package com.enderzombi102.loadercomplex.forge182.impl.entity

import com.enderzombi102.loadercomplex.api.math.Vec3i
import com.enderzombi102.loadercomplex.api.minecraft.entity.PlayerEntity
import com.enderzombi102.loadercomplex.api.minecraft.util.Gamemode
import com.enderzombi102.loadercomplex.forge182.impl.utils.toLC
import net.minecraft.client.MinecraftClient
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.LiteralText
import net.minecraft.util.math.BlockPos
import net.minecraft.world.GameMode
import java.util.*
import net.minecraft.entity.player.PlayerEntity as McPlayerEntity

class ForgePlayer(private val wrappedEntity: McPlayerEntity) : ForgeLivingEntity( wrappedEntity ), PlayerEntity {
	override fun isSleeping(): Boolean =
		wrappedEntity.isSleeping

	override fun getBedLocation(): Optional<Vec3i> =
		wrappedEntity.sleepingPosition.map(BlockPos::toLC)

	override fun getScore(): Int =
		wrappedEntity.score

	override fun addScore(score: Int) {
		wrappedEntity.addScore(score)
	}

	override fun sendMessage(msg: String?) {
		wrappedEntity.sendMessage(LiteralText(msg), false)
	}

	override fun setRespawnPoint(pos: Vec3i) {
		wrappedEntity.setSleepingPosition(BlockPos(pos.x, pos.y, pos.z))
	}

	override fun getFoodLevel(): Int =
		wrappedEntity.hungerManager.foodLevel

	override fun setFoodLevel( level: Int ) {
		wrappedEntity.hungerManager.foodLevel = level
	}

	override fun getSaturationLevel(): Float =
		wrappedEntity.hungerManager.saturationLevel

	override fun setSaturationLevel( level: Float ) {
		wrappedEntity.hungerManager.saturationLevel = level
	}

	override fun getGamemode(): Gamemode {
		val mode = if (wrappedEntity.getWorld().isClient)
			MinecraftClient.getInstance().interactionManager!!.currentGameMode
		else
			(wrappedEntity as ServerPlayerEntity).interactionManager.gameMode
		if (mode == GameMode.DEFAULT)
			return Gamemode.SURVIVAL
		return Gamemode.valueOf(mode.getName().uppercase(Locale.getDefault()))
	}

	override fun getExperienceLevel(): Int =
		wrappedEntity.experienceLevel
}
