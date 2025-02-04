package com.enderzombi102.loadercomplex.fabric171.impl.platform

import com.enderzombi102.loadercomplex.api.minecraft.block.Block
import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity
import com.enderzombi102.loadercomplex.api.minecraft.item.CreativeTabs
import com.enderzombi102.loadercomplex.api.minecraft.item.Item
import com.enderzombi102.loadercomplex.api.minecraft.util.RegistryKey
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier
import com.enderzombi102.loadercomplex.api.platform.Registry
import com.enderzombi102.loadercomplex.fabric171.imixin.IItemMixin
import com.enderzombi102.loadercomplex.fabric171.impl.block.FabricBlock
import com.enderzombi102.loadercomplex.fabric171.impl.item.FabricItem
import com.enderzombi102.loadercomplex.fabric171.impl.utils.toMC
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraft.item.Item as McItem
import net.minecraft.item.ItemGroup as McItemGroup
import net.minecraft.util.registry.Registry as McRegistry


class FabricRegistry : Registry {
	override fun register( block: Block, identifier: ResourceIdentifier ) {
		this.register( block, identifier, CreativeTabs.MISC )
	}

	override fun register( block: Block, identifier: ResourceIdentifier, creativeTab: ResourceIdentifier? ) {
		val id = identifier.toMC()
		val fabricBlock = FabricBlock( block )
		McRegistry.register( McRegistry.BLOCK, id, fabricBlock )

		val itemSettings = McItem.Settings()
		if ( creativeTab != null ) {
			itemSettings.group( getOrCreateCreativeTab( creativeTab, identifier ) )
		}
		McRegistry.register( McRegistry.ITEM, id, BlockItem( fabricBlock, itemSettings ) )

	}

	override fun register( item: Item, identifier: ResourceIdentifier ) {
		val fabricItem = FabricItem( item )
		McRegistry.register( McRegistry.ITEM, identifier.toMC(), fabricItem )
		@Suppress( "CAST_NEVER_SUCCEEDS" )
		(fabricItem as IItemMixin).`lc$setGroup`( getOrCreateCreativeTab( item.creativeTab, identifier ) )
	}

	override fun register( entity: Entity, identifier: ResourceIdentifier ) =
		throw NotImplementedError()

	override fun registerCreativeTab( name: String?, icon: ResourceIdentifier ): ResourceIdentifier {
		val id = ResourceIdentifier( icon.namespace, name ?: icon.namespace )
		ITEM_GROUPS.computeIfAbsent( id ) {
			FabricItemGroupBuilder.create( it.toMC() )
				.icon { ItemStack( McRegistry.ITEM[ icon.toMC() ] ) }
				.build()
		}
		return id
	}

	override fun isRegistered( key: RegistryKey, id: ResourceIdentifier ): Boolean = when ( key ) {
		RegistryKey.Item -> McRegistry.ITEM.containsId( id.toMC() )
		RegistryKey.Block -> McRegistry.BLOCK.containsId( id.toMC() )
		else -> false
	}


	override fun isRegistered( key: RegistryKey, id: String ): Boolean = when ( key ) {
		RegistryKey.Item -> McRegistry.ITEM.containsId( Identifier( id ) )
		RegistryKey.Block -> McRegistry.BLOCK.containsId( Identifier( id ) )
		else -> false
	}

	override fun getVanillaRegistry( type: RegistryKey ): Any? = when ( type ) {
		RegistryKey.Item -> McRegistry.ITEM
		RegistryKey.Block -> McRegistry.BLOCK
		else -> null
	}

	private fun getOrCreateCreativeTab( tab: ResourceIdentifier?, icon: ResourceIdentifier ): McItemGroup? {
		if ( tab == null ) {
			return null
		}
		if (! ITEM_GROUPS.containsKey( tab ) ) {
			registerCreativeTab( null, icon )
		}
		return ITEM_GROUPS[tab]
	}

	companion object {
		private val ITEM_GROUPS: MutableMap<ResourceIdentifier, McItemGroup> =
			HashMap<ResourceIdentifier, McItemGroup>().apply {
				put( CreativeTabs.BREWING, McItemGroup.BREWING )
				put( CreativeTabs.BUILDING_BLOCKS, McItemGroup.BUILDING_BLOCKS )
				put( CreativeTabs.COMBAT, McItemGroup.COMBAT )
				put( CreativeTabs.DECORATIONS, McItemGroup.DECORATIONS )
				put( CreativeTabs.FOOD, McItemGroup.FOOD )
				put( CreativeTabs.MATERIALS, McItemGroup.MATERIALS )
				put( CreativeTabs.REDSTONE, McItemGroup.REDSTONE )
				put( CreativeTabs.TOOLS, McItemGroup.TOOLS )
				put( CreativeTabs.TRANSPORTATION, McItemGroup.TRANSPORTATION )
				put( CreativeTabs.MISC, McItemGroup.MISC )
			}
	}
}
