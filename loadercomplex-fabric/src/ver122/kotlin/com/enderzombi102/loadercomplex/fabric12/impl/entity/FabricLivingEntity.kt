package com.enderzombi102.loadercomplex.fabric12.impl.entity

import com.enderzombi102.loadercomplex.api.minecraft.entity.LivingEntity
import com.enderzombi102.loadercomplex.api.minecraft.entity.PlayerEntity
import com.enderzombi102.loadercomplex.api.minecraft.item.EquipmentSlot
import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack
import com.enderzombi102.loadercomplex.fabric12.impl.entity.FabricEntity
import com.enderzombi102.loadercomplex.fabric12.impl.entity.FabricPlayer
import com.enderzombi102.loadercomplex.fabric12.impl.item.FabricItemStack
import com.enderzombi102.loadercomplex.fabric12.impl.utils.toMC
import net.minecraft.entity.living.LivingEntity as McLivingEntity
import net.minecraft.entity.living.player.PlayerEntity as McPlayerEntity
import net.minecraft.world.InteractionHand

open class FabricLivingEntity( private val backingEntity: McLivingEntity ) : FabricEntity( backingEntity ), LivingEntity {
	override fun getHealth(): Float {
		return backingEntity.health
	}

	override fun setHealth( health: Float ) {
		backingEntity.health = health
	}

	override fun isPlayer(): Boolean {
		return backingEntity is McPlayerEntity
	}

	override fun asPlayer(): PlayerEntity {
		return FabricPlayer( backingEntity as McPlayerEntity )
	}

	override fun isChild(): Boolean {
		return backingEntity.isBaby
	}

	override fun isWaterMob(): Boolean {
		return backingEntity.isWaterMob
	}

	override fun isClimbing(): Boolean {
		return backingEntity.isClimbing
	}

	override fun getArmorProtection(): Int {
		return backingEntity.armorProtection
	}

	override fun getItemInMainHand(): ItemStack {
		return FabricItemStack( backingEntity.getItemInHand( InteractionHand.MAIN_HAND ) )
	}

	override fun getItemInOffHand(): ItemStack {
		return FabricItemStack( backingEntity.getItemInHand( InteractionHand.OFF_HAND ) )
	}

	override fun hasEquipment( slot: EquipmentSlot ): Boolean {
		return backingEntity.getEquipment( slot.toMC() ).isEmpty
	}

	override fun getEquipment( slot: EquipmentSlot ): ItemStack {
		return FabricItemStack( backingEntity.getEquipment( slot.toMC() ) )
	}

	override fun setEquipment( slot: EquipmentSlot, stack: ItemStack ) {
		backingEntity.setEquipment( slot.toMC(), stack.toMC() )
	}
}

