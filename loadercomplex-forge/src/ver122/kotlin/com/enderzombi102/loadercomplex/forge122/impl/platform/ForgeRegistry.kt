package com.enderzombi102.loadercomplex.forge122.impl.platform

import com.enderzombi102.loadercomplex.api.minecraft.block.Block
import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity
import com.enderzombi102.loadercomplex.api.minecraft.item.Item
import com.enderzombi102.loadercomplex.api.minecraft.util.RegistryKey
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier.ri
import com.enderzombi102.loadercomplex.api.platform.Registry
import com.enderzombi102.loadercomplex.forge122.impl.block.ForgeBlock
import com.enderzombi102.loadercomplex.forge122.impl.item.ForgeItem
import com.enderzombi102.loadercomplex.impl.LoaderComplex
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.registries.GameData
import net.minecraftforge.registries.RegistryManager
import java.util.*
import net.minecraft.block.Block as McBlock
import net.minecraft.item.Item as McItem

class ForgeRegistry : Registry {
	private val blocks = HashMap<ResourceIdentifier, McBlock>()
	private val items = HashMap<ResourceIdentifier, McItem>()
	override fun register( block: Block, identifier: ResourceIdentifier ) {
		this.register( block, identifier, ri( "itemgroup.misc" ) )
	}

	override fun register( block: Block, identifier: ResourceIdentifier, itemGroup: ResourceIdentifier? ) {
		val forgeBlock = ForgeBlock( block )
		forgeBlock.setUnlocalizedName( identifier.namespace + '.' + identifier.path )
		blocks[identifier] = forgeBlock
		if ( itemGroup != null ) {
			items[identifier] = ItemBlock( forgeBlock )
				.setUnlocalizedName( identifier.namespace + '.' + identifier.path )
				.setCreativeTab( getOrCreateCreativeTab( itemGroup, identifier ) )
		}
	}

	override fun register( item: Item, identifier: ResourceIdentifier ) {
		items[identifier] = ForgeItem( item )
			.setUnlocalizedName( identifier.namespace + '.' + identifier.path )
			.setCreativeTab( getOrCreateCreativeTab( item.creativeTab, identifier ) )
	}

	override fun register( entity: Entity, identifier: ResourceIdentifier ) {
	}

	override fun registerCreativeTab( name: String?, icon: ResourceIdentifier ): ResourceIdentifier {
		val id = ResourceIdentifier( icon.namespace, name ?: icon.namespace )
		CREATIVE_TABS.computeIfAbsent( id ) {
			object : CreativeTabs( if ( name != null ) icon.namespace + "." + name else icon.namespace ) {
				@SideOnly( Side.CLIENT )
				override fun getTabIconItem(): ItemStack =
					ItemStack( requireNotNull( McItem.getByNameOrId( icon.toString() ) ) { String.format( "The icon given by %s was invalid", icon.namespace ) } )
			}
		}
		return id
	}

	override fun isRegistered( key: RegistryKey, identifier: String ): Boolean =
		false

	override fun isRegistered( key: RegistryKey, identifier: ResourceIdentifier ): Boolean =
		false

	override fun getVanillaRegistry( type: RegistryKey ): Any? = when (type) {
		RegistryKey.Item -> RegistryManager.ACTIVE.getRegistry<McItem>( GameData.ITEMS )
		RegistryKey.Block -> RegistryManager.ACTIVE.getRegistry<McBlock>( GameData.BLOCKS )
		else -> null
	}

	fun onItemRegistry( evt: RegistryEvent.Register<McItem> ) {
		items.forEach { ( id: ResourceIdentifier, item: McItem ) ->
			evt.registry.register( item.setRegistryName( id.toString() ) )
		}
	}

	fun onBlockRegistry( evt: RegistryEvent.Register<McBlock> ) {
		blocks.forEach { ( id: ResourceIdentifier, block: McBlock ) ->
			evt.registry.register( block.setRegistryName( id.toString() ) )
		}
	}

	fun onModelRegistry() {
		items.forEach { ( id: ResourceIdentifier, item: McItem ) ->
			ModelLoader.setCustomModelResourceLocation(
				item,
				0,
				ModelResourceLocation( id.toString(), "inventory" )
			)
		}
	}

	companion object {
		private val CREATIVE_TABS: MutableMap<ResourceIdentifier, CreativeTabs> =
			HashMap<ResourceIdentifier, CreativeTabs>().apply {
				put( ri( "creativetab.brewing" ), CreativeTabs.BREWING )
				put( ri( "creativetab.building_blocks" ), CreativeTabs.BUILDING_BLOCKS )
				put( ri( "creativetab.combat" ), CreativeTabs.COMBAT )
				put( ri( "creativetab.decorations" ), CreativeTabs.DECORATIONS )
				put( ri( "creativetab.food" ), CreativeTabs.FOOD )
				put( ri( "creativetab.materials" ), CreativeTabs.MATERIALS )
				put( ri( "creativetab.redstone" ), CreativeTabs.REDSTONE )
				put( ri( "creativetab.tools" ), CreativeTabs.TOOLS )
				put( ri( "creativetab.transportation" ), CreativeTabs.TRANSPORTATION )
				put( ri( "creativetab.misc" ), CreativeTabs.MISC )
			}

		fun getOrCreateCreativeTab( itemGroup: ResourceIdentifier?, icon: ResourceIdentifier ): CreativeTabs? {
			if ( itemGroup == null ) {
				return null
			}
			if (! CREATIVE_TABS.containsKey( itemGroup ) ) {
				LoaderComplex.get().context.registry.registerCreativeTab( null, icon )
			}
			return CREATIVE_TABS[itemGroup]
		}
	}
}
