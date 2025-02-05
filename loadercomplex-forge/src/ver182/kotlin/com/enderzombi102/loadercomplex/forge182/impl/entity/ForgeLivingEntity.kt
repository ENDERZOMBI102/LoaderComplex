package com.enderzombi102.loadercomplex.forge182.impl.entity

import com.enderzombi102.loadercomplex.api.minecraft.entity.PlayerEntity
import com.enderzombi102.loadercomplex.api.minecraft.item.EquipmentSlot
import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack
import com.enderzombi102.loadercomplex.forge182.impl.item.ForgeItemStack
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity as McPlayerEntity
import net.minecraft.util.Hand

open class ForgeLivingEntity(private val backingEntity: LivingEntity) : ForgeEntity(
	backingEntity
),
	com.enderzombi102.loadercomplex.api.minecraft.entity.LivingEntity {
	override fun getHealth(): Float {
		return backingEntity.health
	}

	override fun setHealth(health: Float) {
		backingEntity.health = health
	}

	override fun isPlayer(): Boolean {
		return backingEntity is PlayerEntity
	}

	override fun asPlayer(): PlayerEntity {
		return ForgePlayer(backingEntity as McPlayerEntity)
	}

	override fun isChild(): Boolean {
		return backingEntity.isBaby
	}

	override fun isWaterMob(): Boolean {
		return backingEntity.canBreatheInWater()
	}

	override fun isClimbing(): Boolean {
		return backingEntity.isClimbing
	}

	override fun getArmorProtection(): Int {
		return backingEntity.armor
	}

	override fun getItemInMainHand(): ItemStack {
		return ForgeItemStack(backingEntity.getStackInHand(Hand.MAIN_HAND))
	}

	override fun getItemInOffHand(): ItemStack {
		return ForgeItemStack(backingEntity.getStackInHand(Hand.OFF_HAND))
	}

	override fun hasEquipment(slot: EquipmentSlot): Boolean {
		return backingEntity.hasStackEquipped(net.minecraft.entity.EquipmentSlot.valueOf(slot.name))
	}

	override fun getEquipment(slot: EquipmentSlot): ItemStack {
		return ForgeItemStack(backingEntity.getEquippedStack(net.minecraft.entity.EquipmentSlot.valueOf(slot.name)))
	}

	override fun setEquipment(slot: EquipmentSlot, stack: ItemStack) {
		backingEntity.equipStack(net.minecraft.entity.EquipmentSlot.valueOf(slot.name),
			(stack as ForgeItemStack).stack as net.minecraft.item.ItemStack?
		)
	}
}
