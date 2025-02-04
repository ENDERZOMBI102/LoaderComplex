package com.enderzombi102.loadercomplex.fabric171.impl.entity

import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity
import com.enderzombi102.loadercomplex.api.minecraft.entity.ItemEntity
import com.enderzombi102.loadercomplex.api.minecraft.entity.LivingEntity
import com.enderzombi102.loadercomplex.api.minecraft.util.Position
import com.enderzombi102.loadercomplex.api.minecraft.world.World
import com.enderzombi102.loadercomplex.fabric171.impl.utils.toLC
import com.enderzombi102.loadercomplex.fabric171.impl.world.FabricWorld
import net.minecraft.entity.Entity as McEntity
import net.minecraft.entity.ItemEntity as McItemEntity
import net.minecraft.entity.LivingEntity as McLivingEntity
import net.minecraft.text.Text
import java.util.*


open class FabricEntity( private val backingEntity: McEntity ) : Entity {
	override fun getDisplayName(): String =
		Text.Serializer.toJson( backingEntity.displayName )

	override fun getName(): String =
		Text.Serializer.toJson( backingEntity.name )

	override fun getUuid(): UUID =
		backingEntity.uuid

	override fun isLivingEntity(): Boolean =
		backingEntity is McLivingEntity

	override fun asLivingEntity(): LivingEntity =
		FabricLivingEntity( backingEntity as McLivingEntity )

	override fun isItem(): Boolean =
		backingEntity is McItemEntity

	override fun asItem(): ItemEntity =
		FabricItemEntity( backingEntity as McItemEntity )

	override fun kill() {
		backingEntity.remove( McEntity.RemovalReason.KILLED )
	}

	override fun isDead(): Boolean =
		backingEntity.isRemoved

	override fun getPosition(): Position =
		backingEntity.blockPos.toLC()

	override fun setPosition( pos: Position ) {
		backingEntity.setPosition( pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble() )
	}

	override fun getPitch(): Float =
		backingEntity.pitch

	override fun getYaw(): Float =
		backingEntity.yaw

	override fun getWorld(): World =
		FabricWorld( backingEntity.world )

	override fun getObject(): Any =
		this.backingEntity
}
