package com.enderzombi102.loadercomplex.forge122.impl.entity

import com.enderzombi102.loadercomplex.api.math.Vec3d
import com.enderzombi102.loadercomplex.api.minecraft.entity.ItemEntity
import com.enderzombi102.loadercomplex.api.minecraft.entity.LivingEntity
import com.enderzombi102.loadercomplex.api.minecraft.world.World
import com.enderzombi102.loadercomplex.forge122.impl.utils.toLC
import com.enderzombi102.loadercomplex.forge122.impl.world.ForgeWorld
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.item.EntityItem
import net.minecraft.util.text.ITextComponent
import java.util.*

open class ForgeEntity(private val wrappedEntity: Entity) :
	com.enderzombi102.loadercomplex.api.minecraft.entity.Entity {
	override fun getDisplayName(): String {
		return ITextComponent.Serializer.componentToJson(wrappedEntity.displayName)
	}

	override fun getName(): String {
		return wrappedEntity.name
	}

	override fun getUuid(): UUID {
		return wrappedEntity.uniqueID
	}

	override fun isLivingEntity(): Boolean {
		return wrappedEntity is EntityLivingBase
	}

	override fun asLivingEntity(): LivingEntity {
		return ForgeLivingEntity(wrappedEntity as EntityLivingBase)
	}

	override fun isItem(): Boolean {
		return wrappedEntity is EntityItem
	}

	override fun asItem(): ItemEntity {
		return ForgeItemEntity(wrappedEntity as EntityItem)
	}

	override fun kill() {
		wrappedEntity.setDead()
	}

	override fun isDead(): Boolean {
		return wrappedEntity.isDead
	}

	override fun getPosition(): Vec3d {
		return wrappedEntity.position.toLC().toVec3d()
	}

	override fun setPosition(pos: Vec3d) {
		wrappedEntity.setPosition(pos.x, pos.y, pos.z)
	}

	override fun getPitch(): Float {
		return wrappedEntity.rotationPitch
	}

	override fun getYaw(): Float {
		return wrappedEntity.rotationYaw
	}

	override fun getWorld(): World {
		return ForgeWorld(wrappedEntity.world)
	}

	override fun getObject(): Any {
		return this.wrappedEntity
	}
}
