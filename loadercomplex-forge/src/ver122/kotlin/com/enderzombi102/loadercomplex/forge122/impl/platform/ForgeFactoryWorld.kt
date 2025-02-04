package com.enderzombi102.loadercomplex.forge122.impl.platform

import com.enderzombi102.loadercomplex.api.math.Vec3d
import com.enderzombi102.loadercomplex.api.minecraft.block.BlockState
import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity
import com.enderzombi102.loadercomplex.api.minecraft.entity.ItemEntity
import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack
import com.enderzombi102.loadercomplex.api.minecraft.server.Server
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier
import com.enderzombi102.loadercomplex.api.minecraft.world.World
import com.enderzombi102.loadercomplex.api.platform.FactoryWorld
import com.enderzombi102.loadercomplex.forge122.impl.block.ForgeBlockState
import com.enderzombi102.loadercomplex.forge122.impl.entity.ForgeEntity
import com.enderzombi102.loadercomplex.forge122.impl.entity.ForgeItemEntity
import com.enderzombi102.loadercomplex.forge122.impl.item.ForgeItemStack
import com.enderzombi102.loadercomplex.forge122.impl.utils.toMC
import net.minecraft.entity.item.EntityItem
import net.minecraftforge.fml.common.registry.ForgeRegistries
import net.minecraft.item.ItemStack as McItemStack
import net.minecraft.world.World as McWorld

class ForgeFactoryWorld : FactoryWorld {
	override fun createStack( type: ResourceIdentifier ): ItemStack {
		val item = ForgeRegistries.ITEMS.getValue( type.toMC() )
		requireNotNull( item ) { String.format( "\"%s\" is not a valid item!", type ) }
		return ForgeItemStack( McItemStack( item ) )
	}

	override fun createEntity( world: World, type: ResourceIdentifier, pos: Vec3d ): Entity? {
		val entity = ForgeRegistries.ENTITIES.getValue( type.toMC() )
		requireNotNull( entity ) { String.format( "\"%s\" is not a valid entity!", type ) }
		val it = entity.newInstance( world.getObject() as McWorld )
		it.setPositionAndUpdate( pos.x, pos.y, pos.z )
		return ForgeEntity( it )
	}

	override fun createItemEntity( world: World, stack: ItemStack, pos: Vec3d ): ItemEntity {
		val it = EntityItem( world.getObject() as McWorld )
		it.item = stack.stack as McItemStack
		it.setPositionAndUpdate( pos.x, pos.y, pos.z )
		return ForgeItemEntity( it )
	}

	override fun createBlockstate( type: ResourceIdentifier ): BlockState {
		val block = ForgeRegistries.BLOCKS.getValue( type.toMC() )
		requireNotNull( block ) { String.format( "\"%s\" is not a valid block!", type ) }
		return ForgeBlockState( block.defaultState )
	}

	override fun airBlockstate(): BlockState =
		createBlockstate( ResourceIdentifier( "minecraft", "air" ) )

	override fun adaptWorld( server: Server, id: Int ): World =
		throw RuntimeException( "Not implemented" )
}
