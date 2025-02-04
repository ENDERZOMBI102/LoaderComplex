package com.enderzombi102.loadercomplex.fabric122.impl.platform

import com.enderzombi102.loadercomplex.api.minecraft.block.Block
import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity
import com.enderzombi102.loadercomplex.api.minecraft.item.Item
import com.enderzombi102.loadercomplex.api.minecraft.util.RegistryKey
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier.ri
import com.enderzombi102.loadercomplex.api.platform.Registry
import com.enderzombi102.loadercomplex.fabric122.imixin.IItemMixin
import com.enderzombi102.loadercomplex.fabric122.impl.block.FabricBlock
import com.enderzombi102.loadercomplex.fabric122.impl.item.FabricItem
import com.enderzombi102.loadercomplex.fabric122.impl.utils.ItemGroupBuilder
import com.enderzombi102.loadercomplex.fabric122.impl.utils.toMC
import net.minecraft.item.BlockItem as McBlockItem
import net.minecraft.item.CreativeModeTab as McCreativeModeTab
import net.minecraft.item.Item as McItem
import net.minecraft.item.ItemStack as McItemStack
import net.minecraft.resource.Identifier
import net.minecraft.block.Block as McBlock

class FabricRegistry : Registry {
	private val registeredItems: MutableList<McItem> = ArrayList()

	override fun register( block: Block, identifier: ResourceIdentifier ) {
		this.register( block, identifier, ri( "itemgroup.misc" ) )
	}

	override fun register( block: Block, identifier: ResourceIdentifier, creativeTab: ResourceIdentifier? ) {
		val id = identifier.toMC()
		val key = identifier.namespace + '.' + identifier.path
		val fabricBlock = FabricBlock( block )
			.setKey( key )
			.setCreativeModeTab( getOrCreateCreativeTab( creativeTab, identifier ) ) as FabricBlock
		McBlock.REGISTRY.register( McBlock.REGISTRY.keySet().size, id, fabricBlock )

		// THESE 4 LINES COSTED SO MANY HOURS OF DEBUGGING
		for ( state in fabricBlock.stateDefinition().all() ) {
			val rawId = McBlock.REGISTRY.getId( fabricBlock ) shl 4 or fabricBlock.getMetadataFromState( state )
			McBlock.STATE_REGISTRY.put( state, rawId )
		}

		if ( creativeTab != null ) {
			val blockItem = McBlockItem( fabricBlock ).setKey( key )
			McItem.REGISTRY.register( McItem.REGISTRY.keySet().size, id, blockItem )
			(blockItem as IItemMixin).`lc$getBlockItemRegistry`()[fabricBlock] = blockItem
			registeredItems.add( blockItem )
		}
	}

	override fun register( item: Item, identifier: ResourceIdentifier ) {
		val fabricItem = FabricItem( item ).setKey( identifier.namespace + '.' + identifier.path ) as FabricItem
		McItem.REGISTRY.register( McItem.REGISTRY.keySet().size, identifier.toMC(), fabricItem )
		registeredItems.add( fabricItem )
		fabricItem.setCreativeModeTab( getOrCreateCreativeTab( fabricItem.itemImpl.creativeTab, identifier ) )
	}

	override fun register( entity: Entity, identifier: ResourceIdentifier ) {
	}

	override fun registerCreativeTab( name: String?, icon: ResourceIdentifier ): ResourceIdentifier {
		val id = ResourceIdentifier( icon.namespace, name ?: icon.namespace )
		CREATIVE_TABS.computeIfAbsent( id ) {
			ItemGroupBuilder.build( id.toMC() ) { McItemStack( McItem.REGISTRY[ id.toMC() ] ) }
		}
		return id
	}

	override fun isRegistered( key: RegistryKey, id: ResourceIdentifier ): Boolean =
		this.isRegistered( key, id.toString() )

	override fun isRegistered( key: RegistryKey, id: String ): Boolean = when ( key ) {
		RegistryKey.Item -> McItem.REGISTRY.keySet().contains( Identifier( id ) )
		RegistryKey.Block -> McBlock.REGISTRY.keySet().contains( Identifier( id ) )
		else -> false
	}

	override fun getVanillaRegistry( type: RegistryKey ): Any? = when ( type ) {
		RegistryKey.Item -> McItem.REGISTRY
		RegistryKey.Block -> McBlock.REGISTRY
		else -> null
	}

	fun getRegisteredItems(): List<McItem> {
		return this.registeredItems
	}

	private fun getOrCreateCreativeTab( tab: ResourceIdentifier?, icon: ResourceIdentifier ): McCreativeModeTab? {
		if ( tab == null ) {
			return null
		}
		if (! CREATIVE_TABS.containsKey( tab ) ) {
			registerCreativeTab( null, icon )
		}
		return CREATIVE_TABS[tab]
	}

	companion object {
		private val CREATIVE_TABS: MutableMap<ResourceIdentifier, McCreativeModeTab> =
			HashMap<ResourceIdentifier, McCreativeModeTab>().apply {
				put( ri( "creativetab.brewing" ), McCreativeModeTab.BREWING )
				put( ri( "creativetab.building_blocks" ), McCreativeModeTab.BUILDING_BLOCKS )
				put( ri( "creativetab.combat" ), McCreativeModeTab.COMBAT )
				put( ri( "creativetab.decorations" ), McCreativeModeTab.DECORATIONS )
				put( ri( "creativetab.food" ), McCreativeModeTab.FOOD )
				put( ri( "creativetab.materials" ), McCreativeModeTab.MATERIALS )
				put( ri( "creativetab.redstone" ), McCreativeModeTab.REDSTONE )
				put( ri( "creativetab.tools" ), McCreativeModeTab.TOOLS )
				put( ri( "creativetab.transportation" ), McCreativeModeTab.TRANSPORTATION )
				put( ri( "creativetab.misc" ), McCreativeModeTab.MISC )
			}
	}
}
