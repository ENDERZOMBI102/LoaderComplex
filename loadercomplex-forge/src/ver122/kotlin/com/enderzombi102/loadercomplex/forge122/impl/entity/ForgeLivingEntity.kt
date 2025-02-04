package com.enderzombi102.loadercomplex.forge122.impl.entity

import com.enderzombi102.loadercomplex.api.minecraft.entity.LivingEntity
import com.enderzombi102.loadercomplex.api.minecraft.entity.PlayerEntity
import com.enderzombi102.loadercomplex.api.minecraft.item.EquipmentSlot
import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack
import com.enderzombi102.loadercomplex.forge122.impl.item.ForgeItemStack
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.EntityEquipmentSlot

open class ForgeLivingEntity(private val wrappedEntity: EntityLivingBase) : ForgeEntity(
	wrappedEntity
),
	LivingEntity {
	override fun getHealth(): Float {
		return wrappedEntity.health
	}

	override fun setHealth(health: Float) {
		wrappedEntity.health = health
	}

	override fun isLivingEntity(): Boolean {
		return true
	}

	override fun asLivingEntity(): LivingEntity {
		return this
	}

	override fun isPlayer(): Boolean {
		return wrappedEntity is EntityPlayer
	}

	override fun asPlayer(): PlayerEntity {
		return ForgePlayer(wrappedEntity as EntityPlayer)
	}

	override fun isChild(): Boolean {
		return wrappedEntity.isChild
	}

	override fun isWaterMob(): Boolean {
		return wrappedEntity.canBreatheUnderwater()
	}

	override fun isClimbing(): Boolean {
		return wrappedEntity.isOnLadder
	}

	override fun getArmorProtection(): Int {
		return wrappedEntity.totalArmorValue
	}

	override fun getItemInMainHand(): ItemStack {
		return ForgeItemStack(wrappedEntity.heldItemMainhand)
	}

	override fun getItemInOffHand(): ItemStack {
		return ForgeItemStack(wrappedEntity.heldItemOffhand)
	}

	override fun hasEquipment(slot: EquipmentSlot): Boolean {
		return wrappedEntity.hasItemInSlot(EntityEquipmentSlot.valueOf(slot.name))
	}

	override fun getEquipment(slot: EquipmentSlot): ItemStack {
		return ForgeItemStack(
			wrappedEntity.getItemStackFromSlot(EntityEquipmentSlot.valueOf(slot.name))
		)
	}

	override fun setEquipment(slot: EquipmentSlot, stack: ItemStack) {
		wrappedEntity.setItemStackToSlot(
			EntityEquipmentSlot.valueOf(slot.name),
			stack.stack as net.minecraft.item.ItemStack
		)
	}
}
