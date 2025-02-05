package com.enderzombi102.loadercomplex.forge182.impl.platform

import com.enderzombi102.loadercomplex.api.minecraft.block.Block
import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity
import com.enderzombi102.loadercomplex.api.minecraft.item.Item
import com.enderzombi102.loadercomplex.api.minecraft.util.RegistryKey
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier.ri
import com.enderzombi102.loadercomplex.api.platform.Registry
import com.enderzombi102.loadercomplex.forge182.imixin.IItemMixin
import com.enderzombi102.loadercomplex.forge182.impl.block.ForgeBlock
import com.enderzombi102.loadercomplex.forge182.impl.item.ForgeItem
import com.enderzombi102.loadercomplex.forge182.impl.utils.toMC
import com.enderzombi102.loadercomplex.impl.LoaderComplex
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraftforge.registries.ForgeRegistries
import net.minecraft.block.Block as McBlock
import net.minecraft.item.Item as McItem

class ForgeRegistry : Registry {
	@JvmField
	val blocks: MutableList<McBlock> = ArrayList()
	@JvmField
	val items: MutableList<McItem> = ArrayList()

	override fun register( block: Block, identifier: ResourceIdentifier ) {
		this.register(block, identifier, ri("itemgroup.misc"))
	}

	override fun register( block: Block, identifier: ResourceIdentifier, itemGroup: ResourceIdentifier? ) {
		val id = identifier.toMC()
		val forgeBlock = ForgeBlock(block).setRegistryName(id) as ForgeBlock
		blocks.add(forgeBlock)
		if (itemGroup != null) {
			items.add( BlockItem( forgeBlock, McItem.Settings().group(getOrCreateItemGroup(itemGroup, identifier)) ).setRegistryName(id) )
		}
	}

	override fun register( item: Item, identifier: ResourceIdentifier ) {
		val forgeItem = ForgeItem(item).setRegistryName(identifier.toMC()) as ForgeItem
		items.add(forgeItem)
		(forgeItem as IItemMixin).`lc$setGroup`(getOrCreateItemGroup(forgeItem.itemImpl.creativeTab, identifier))
	}

	override fun register(entity: Entity, identifier: ResourceIdentifier) {
	}

	override fun registerCreativeTab(name: String?, icon: ResourceIdentifier): ResourceIdentifier {
		val id = ResourceIdentifier( icon.namespace, name ?: icon.namespace )
		CREATIVE_TABS.computeIfAbsent( id ) {
			object : ItemGroup(id.path) {
				override fun createIcon(): ItemStack {
					return ItemStack(ForgeRegistries.ITEMS.getValue(icon.toMC()))
				}
			}
		}
		return id
	}

	override fun isRegistered(key: RegistryKey, id: String): Boolean {
		return when (key) {
			RegistryKey.Item -> ForgeRegistries.ITEMS.containsKey(Identifier(id))
			RegistryKey.Block -> ForgeRegistries.BLOCKS.containsKey(Identifier(id))
			else -> false
		}
	}

	override fun isRegistered(key: RegistryKey, id: ResourceIdentifier): Boolean {
		return this.isRegistered(key, id.toString())
	}

	override fun getVanillaRegistry(type: RegistryKey): Any? {
		return when (type) {
			RegistryKey.Item -> ForgeRegistries.ITEMS
			RegistryKey.Block -> ForgeRegistries.BLOCKS
			else -> null
		}
	}

	companion object {
		private val CREATIVE_TABS: MutableMap<ResourceIdentifier, ItemGroup> =
			HashMap<ResourceIdentifier, ItemGroup>().apply {
				put( ri( "creativetab.brewing" ), ItemGroup.BREWING )
				put( ri( "creativetab.building_blocks" ), ItemGroup.BUILDING_BLOCKS )
				put( ri( "creativetab.combat" ), ItemGroup.COMBAT )
				put( ri( "creativetab.decorations" ), ItemGroup.DECORATIONS )
				put( ri( "creativetab.food" ), ItemGroup.FOOD )
				put( ri( "creativetab.materials" ), ItemGroup.MATERIALS )
				put( ri( "creativetab.redstone" ), ItemGroup.REDSTONE )
				put( ri( "creativetab.tools" ), ItemGroup.TOOLS )
				put( ri( "creativetab.transportation" ), ItemGroup.TRANSPORTATION )
				put( ri( "creativetab.misc" ), ItemGroup.MISC )
			}

		fun getOrCreateItemGroup(itemGroup: ResourceIdentifier?, icon: ResourceIdentifier ): ItemGroup? {
			if (itemGroup == null)
				return null
			if (!CREATIVE_TABS.containsKey(itemGroup))
				LoaderComplex.get().context.getRegistry().registerCreativeTab( null, icon )
			return CREATIVE_TABS[itemGroup]
		}
	}
}
