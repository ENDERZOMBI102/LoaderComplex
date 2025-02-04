package com.enderzombi102.loadercomplex.fabric171.impl.platform

import com.enderzombi102.loadercomplex.api.math.Vec3d
import com.enderzombi102.loadercomplex.api.minecraft.block.BlockState
import com.enderzombi102.loadercomplex.api.minecraft.command.CommandManager
import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity
import com.enderzombi102.loadercomplex.api.minecraft.entity.ItemEntity
import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack
import com.enderzombi102.loadercomplex.api.minecraft.keybind.KeybindManager
import com.enderzombi102.loadercomplex.api.minecraft.network.NetworkManager
import com.enderzombi102.loadercomplex.api.minecraft.server.Server
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier
import com.enderzombi102.loadercomplex.api.minecraft.world.World
import com.enderzombi102.loadercomplex.api.platform.FactoryWorld
import com.enderzombi102.loadercomplex.fabric171.impl.block.FabricBlockState
import com.enderzombi102.loadercomplex.fabric171.impl.entity.FabricEntity
import com.enderzombi102.loadercomplex.fabric171.impl.entity.FabricItemEntity
import com.enderzombi102.loadercomplex.fabric171.impl.item.FabricItemStack
import com.enderzombi102.loadercomplex.fabric171.impl.utils.toMC
import net.minecraft.block.Blocks
import net.minecraft.entity.EntityType
import net.minecraft.util.registry.Registry
import net.minecraft.item.ItemStack as McItemStack
import net.minecraft.world.World as McWorld


class FabricFactoryWorld : FactoryWorld {
	override fun createStack( type: ResourceIdentifier ): ItemStack {
		return FabricItemStack( McItemStack( Registry.ITEM[ type.toMC() ] ) )
	}

	override fun createEntity( world: World, type: ResourceIdentifier, pos: Vec3d ): Entity? =
		Registry.ENTITY_TYPE[ type.toMC() ]
			.create( world.getObject() as McWorld )
			?.let {
				it.setPosition( pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble() )
				return FabricEntity( it )
			}

	override fun createItemEntity( world: World, stack: ItemStack, pos: Vec3d ): ItemEntity? =
		EntityType.ITEM
			.create( world.getObject() as McWorld )
			?.let {
				it.setPosition( pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble() )
				it.stack = stack.stack as McItemStack
				FabricItemEntity( it )
			}

	override fun createBlockstate( type: ResourceIdentifier ): BlockState =
		FabricBlockState( Registry.BLOCK[ type.toMC() ].defaultState )

	override fun airBlockstate(): BlockState =
		FabricBlockState( Blocks.AIR.defaultState )

	override fun adaptWorld( server: Server, id: Int ): World =
		throw NotImplementedError()
}
