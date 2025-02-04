package com.enderzombi102.loadercomplex.fabric122.impl.platform

import com.enderzombi102.loadercomplex.api.math.Vec3d
import com.enderzombi102.loadercomplex.api.minecraft.block.BlockState
import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity
import com.enderzombi102.loadercomplex.api.minecraft.entity.ItemEntity
import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack
import com.enderzombi102.loadercomplex.api.minecraft.server.Server
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier
import com.enderzombi102.loadercomplex.api.minecraft.world.World
import com.enderzombi102.loadercomplex.api.platform.FactoryWorld
import com.enderzombi102.loadercomplex.fabric122.impl.block.FabricBlockState
import com.enderzombi102.loadercomplex.fabric122.impl.entity.FabricItemEntity
import com.enderzombi102.loadercomplex.fabric122.impl.item.FabricItemStack
import com.enderzombi102.loadercomplex.fabric122.impl.utils.toMC
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.entity.ItemEntity as McItemEntity
import net.minecraft.item.ItemStack as McItemStack
import net.minecraft.world.World as McWorld

class FabricFactoryWorld : FactoryWorld {
	override fun createStack( type: ResourceIdentifier ): ItemStack {
		val item = Item.REGISTRY[ type.toMC() ]
		requireNotNull( item ) { String.format( "Item \"%s\" does not exist!", type ) }
		return FabricItemStack( McItemStack( item ) )
	}

	override fun createEntity( world: World, type: ResourceIdentifier, pos: Vec3d ): Entity =
		throw NotImplementedError()

	override fun createItemEntity( world: World, stack: ItemStack, pos: Vec3d ): ItemEntity {
		val entity = McItemEntity( world.getObject() as McWorld )
		entity.item = stack.stack as McItemStack
		return FabricItemEntity( entity )
	}

	override fun createBlockstate( type: ResourceIdentifier ): BlockState =
		FabricBlockState( Block.REGISTRY[ type.toMC() ].defaultState() )

	override fun airBlockstate(): BlockState =
		createBlockstate( ResourceIdentifier( "minecraft", "air" ) )

	override fun adaptWorld( server: Server, id: Int ): World =
		throw NotImplementedError()
}
