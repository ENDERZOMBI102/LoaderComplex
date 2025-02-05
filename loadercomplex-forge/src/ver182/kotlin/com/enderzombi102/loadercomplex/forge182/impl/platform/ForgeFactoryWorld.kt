package com.enderzombi102.loadercomplex.forge182.impl.platform

import com.enderzombi102.loadercomplex.api.math.Vec3d
import com.enderzombi102.loadercomplex.api.minecraft.block.BlockState
import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity
import com.enderzombi102.loadercomplex.api.minecraft.entity.ItemEntity
import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack
import com.enderzombi102.loadercomplex.api.minecraft.server.Server
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier
import com.enderzombi102.loadercomplex.api.minecraft.world.World
import com.enderzombi102.loadercomplex.api.platform.FactoryWorld
import com.enderzombi102.loadercomplex.forge182.impl.utils.toMC
import com.enderzombi102.loadercomplex.forge182.impl.block.ForgeBlockState
import com.enderzombi102.loadercomplex.forge182.impl.entity.ForgeEntity
import com.enderzombi102.loadercomplex.forge182.impl.entity.ForgeItemEntity
import com.enderzombi102.loadercomplex.forge182.impl.item.ForgeItemStack
import net.minecraft.entity.EntityType
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraftforge.registries.RegistryManager
import net.minecraft.world.World as McWorld

class ForgeFactoryWorld : FactoryWorld {
	override fun createStack(type: ResourceIdentifier): ItemStack {
		val item = RegistryManager.ACTIVE.getRegistry(Registry.ITEM_KEY)
			.getValue(Identifier(type.namespace, type.path))
		requireNotNull(item) { String.format("Item \"%s\" does not exist!", type) }
		return ForgeItemStack(net.minecraft.item.ItemStack(item))
	}

	override fun createEntity(world: World, type: ResourceIdentifier, pos: Vec3d ): Entity? {
		val entityType = RegistryManager.ACTIVE.getRegistry(Registry.ENTITY_TYPE_KEY).getValue(type.toMC())
		requireNotNull(entityType) { String.format("Entity \"%s\" does not exist!", type) }
		return entityType.create(world.getObject() as McWorld)?.let { ForgeEntity(it) }
	}

	override fun createItemEntity(world: World, stack: ItemStack, pos: Vec3d ): ItemEntity {
		val entity = EntityType.ITEM.create(world.getObject() as McWorld)
		requireNotNull(entity) { "ItemEntity failed to spawn!" }
		entity.stack = stack.stack as net.minecraft.item.ItemStack
		return ForgeItemEntity(entity)
	}

	override fun createBlockstate(type: ResourceIdentifier): BlockState {
		val block = RegistryManager.ACTIVE.getRegistry(Registry.BLOCK_KEY)
			.getValue(type.toMC())
		requireNotNull(block) { String.format("Block \"%s\" does not exist!", type) }
		return ForgeBlockState(block.defaultState)
	}

	override fun airBlockstate(): BlockState {
		return createBlockstate(ResourceIdentifier("minecraft", "air"))
	}

	override fun adaptWorld(server: Server, id: Int): World? {
		throw NotImplementedError()
	}
}
