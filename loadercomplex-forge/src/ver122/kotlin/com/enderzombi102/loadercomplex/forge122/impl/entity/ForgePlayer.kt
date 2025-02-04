package com.enderzombi102.loadercomplex.forge122.impl.entity

import com.enderzombi102.loadercomplex.api.math.Vec3i
import com.enderzombi102.loadercomplex.api.minecraft.entity.PlayerEntity
import com.enderzombi102.loadercomplex.api.minecraft.util.Gamemode
import com.enderzombi102.loadercomplex.forge122.impl.utils.toLC
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentString
import net.minecraft.world.GameType
import java.util.*

class ForgePlayer(private val wrappedEntity: EntityPlayer) : ForgeLivingEntity(
	wrappedEntity
),
	PlayerEntity {
	override fun isSleeping(): Boolean {
		return wrappedEntity.isPlayerSleeping
	}

	override fun getBedLocation(): Optional<Vec3i> {
		return Optional.ofNullable(wrappedEntity.getBedLocation()).map(BlockPos::toLC)
	}

	override fun getScore(): Int {
		return wrappedEntity.score
	}

	override fun addScore(score: Int) {
		wrappedEntity.addScore(score)
	}

	override fun sendMessage(msg: String) {
		wrappedEntity.sendMessage(TextComponentString(msg))
	}

	override fun setRespawnPoint(pos: Vec3i) {
		wrappedEntity.setSpawnPoint(BlockPos(pos.x, pos.y, pos.z), true)
	}

	override fun getFoodLevel(): Int {
		return wrappedEntity.foodStats.foodLevel
	}

	override fun setFoodLevel(level: Int) {
		wrappedEntity.foodStats.foodLevel = level
	}

	override fun getSaturationLevel(): Float {
		return wrappedEntity.foodStats.saturationLevel
	}

	override fun setSaturationLevel(level: Float) {
		wrappedEntity.foodStats.setFoodSaturationLevel(level)
	}

	override fun getGamemode(): Gamemode {
		val type = if (wrappedEntity.entityWorld.isRemote) {
			Minecraft.getMinecraft().playerController.currentGameType
		} else {
			(wrappedEntity as EntityPlayerMP).interactionManager.gameType
		}
		check(type != GameType.NOT_SET) { "Player has no gamemode!" }
		return Gamemode.valueOf(type.getName().uppercase(Locale.getDefault()))
	}

	override fun getExperienceLevel(): Int {
		return wrappedEntity.experienceLevel
	}

	override fun isPlayer(): Boolean {
		return true
	}

	override fun asPlayer(): PlayerEntity {
		return this
	}
}
