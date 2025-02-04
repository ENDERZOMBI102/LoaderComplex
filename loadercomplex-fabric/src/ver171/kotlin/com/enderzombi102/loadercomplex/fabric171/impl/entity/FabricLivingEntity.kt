package com.enderzombi102.loadercomplex.fabric171.impl.entity

import com.enderzombi102.loadercomplex.api.minecraft.entity.LivingEntity
import com.enderzombi102.loadercomplex.api.minecraft.entity.PlayerEntity
import com.enderzombi102.loadercomplex.api.minecraft.item.EquipmentSlot
import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack
import com.enderzombi102.loadercomplex.fabric171.impl.item.FabricItemStack
import com.enderzombi102.loadercomplex.fabric171.impl.utils.toMC
import net.minecraft.entity.LivingEntity as McLivingEntity
import net.minecraft.entity.player.PlayerEntity as McPlayerEntity
import net.minecraft.util.Hand as McHand

open class FabricLivingEntity( private val backingEntity: McLivingEntity ) : FabricEntity( backingEntity ), LivingEntity {
	override fun getHealth(): Float =
		backingEntity.health

	override fun setHealth( health: Float ) {
		backingEntity.health = health
	}

	override fun isPlayer(): Boolean =
		backingEntity is McPlayerEntity

	override fun asPlayer(): PlayerEntity =
		FabricPlayerEntity( backingEntity as McPlayerEntity )

	override fun isChild(): Boolean =
		backingEntity.isBaby

	override fun isWaterMob(): Boolean =
		backingEntity.canBreatheInWater()

	override fun isClimbing(): Boolean =
		backingEntity.isClimbing

	override fun getArmorProtection(): Int =
		backingEntity.armor

	override fun getItemInMainHand(): ItemStack =
		FabricItemStack( backingEntity.getStackInHand( McHand.MAIN_HAND ) )

	override fun getItemInOffHand(): ItemStack =
		FabricItemStack( backingEntity.getStackInHand( McHand.OFF_HAND ) )

	override fun hasEquipment( slot: EquipmentSlot ): Boolean =
		backingEntity.hasStackEquipped( slot.toMC() )

	override fun getEquipment( slot: EquipmentSlot ): ItemStack =
		FabricItemStack( backingEntity.getEquippedStack( slot.toMC() ) )

	override fun setEquipment( slot: EquipmentSlot, stack: ItemStack ) {
		backingEntity.equipStack( slot.toMC(), stack.toMC() )
	}
}

