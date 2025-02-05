package com.enderzombi102.loadercomplex.forge182.impl.entity

import com.enderzombi102.loadercomplex.api.math.Vec3d
import com.enderzombi102.loadercomplex.api.minecraft.world.World
import com.enderzombi102.loadercomplex.forge182.impl.utils.toLC
import com.enderzombi102.loadercomplex.forge182.impl.world.ForgeWorld
import net.minecraft.entity.Entity
import net.minecraft.entity.ItemEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.text.Text
import java.util.*

open class ForgeEntity(private val backingEntity: Entity) :
	com.enderzombi102.loadercomplex.api.minecraft.entity.Entity {
	override fun getDisplayName(): String {
		return Text.Serializer.toJson(backingEntity.name)
	}

	override fun getName(): String {
		return backingEntity.entityName
	}

	override fun getUuid(): UUID {
		return backingEntity.uuid
	}

	override fun isLivingEntity(): Boolean {
		return backingEntity is LivingEntity
	}

	override fun asLivingEntity(): com.enderzombi102.loadercomplex.api.minecraft.entity.LivingEntity {
		return ForgeLivingEntity(backingEntity as LivingEntity)
	}

	override fun isItem(): Boolean {
		return backingEntity is ItemEntity
	}

	override fun asItem(): com.enderzombi102.loadercomplex.api.minecraft.entity.ItemEntity {
		return ForgeItemEntity(backingEntity as ItemEntity)
	}

	override fun kill() {
		backingEntity.remove(Entity.RemovalReason.KILLED)
	}

	override fun isDead(): Boolean {
		return backingEntity.isRemoved
	}

	override fun getPosition(): Vec3d {
		return backingEntity.blockPos.toLC().toVec3d()
	}

	override fun setPosition(pos: Vec3d) {
		backingEntity.setPosition(pos.x, pos.y, pos.z)
	}

	override fun getPitch(): Float {
		return backingEntity.pitch
	}

	override fun getYaw(): Float {
		return backingEntity.yaw
	}

	override fun getWorld(): World {
		return ForgeWorld(backingEntity.world)
	}

	override fun getObject(): Any {
		return this.backingEntity
	}
}
