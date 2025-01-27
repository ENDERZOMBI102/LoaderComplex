package com.enderzombi102.loadercomplex.fabric12.impl.entity

import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity
import com.enderzombi102.loadercomplex.api.minecraft.entity.ItemEntity
import com.enderzombi102.loadercomplex.api.minecraft.entity.LivingEntity
import com.enderzombi102.loadercomplex.api.minecraft.util.Position
import com.enderzombi102.loadercomplex.api.minecraft.world.World
import com.enderzombi102.loadercomplex.fabric12.impl.utils.toLC
import com.enderzombi102.loadercomplex.fabric12.impl.world.FabricWorld
import net.minecraft.entity.Entity as McEntity
import net.minecraft.entity.ItemEntity as McItemEntity
import net.minecraft.entity.living.LivingEntity as McLivingEntity
import net.minecraft.text.Text
import java.util.*

open class FabricEntity( private val backingEntity: McEntity ) : Entity {
	override fun getDisplayName(): String {
		return Text.Serializer.toJson( backingEntity.displayName )
	}

	override fun getName(): String {
		return backingEntity.name
	}

	override fun getUuid(): UUID {
		return backingEntity.uuid
	}

	override fun isLivingEntity(): Boolean {
		return backingEntity is McLivingEntity
	}

	override fun asLivingEntity(): LivingEntity {
		return FabricLivingEntity( backingEntity as McLivingEntity )
	}

	override fun isItem(): Boolean {
		return backingEntity is McItemEntity
	}

	override fun asItem(): ItemEntity {
		return FabricItemEntity( backingEntity as McItemEntity )
	}

	override fun kill() {
		backingEntity.remove()
	}

	override fun isDead(): Boolean {
		return backingEntity.removed
	}

	override fun getPosition(): Position {
		return backingEntity.sourceBlockPos.toLC()
	}

	override fun setPosition( pos: Position ) {
		backingEntity.setPosition( pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble() );
	}

	override fun getPitch(): Float {
		return backingEntity.pitch
	}

	override fun getYaw(): Float {
		return backingEntity.yaw
	}

	override fun getWorld(): World {
		return FabricWorld( backingEntity.world )
	}

	override fun getObject(): Any {
		return this.backingEntity
	}
}
