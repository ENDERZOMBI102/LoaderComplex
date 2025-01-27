package com.enderzombi102.loadercomplex.forge18.impl;

import com.enderzombi102.loadercomplex.api.minecraft.block.Block;
import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity;
import com.enderzombi102.loadercomplex.api.minecraft.item.Item;
import com.enderzombi102.loadercomplex.api.minecraft.util.RegistryKey;
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier;
import com.enderzombi102.loadercomplex.api.platform.Registry;
import com.enderzombi102.loadercomplex.forge18.LoaderComplexForge;
import com.enderzombi102.loadercomplex.forge18.imixin.IItemMixin;
import com.enderzombi102.loadercomplex.forge18.impl.block.ForgeBlock;
import com.enderzombi102.loadercomplex.forge18.impl.item.ForgeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier.ri;

public class ForgeRegistry implements Registry {
	public final List<net.minecraft.block.Block> blocks = new ArrayList<>();
	public final List<net.minecraft.item.Item> items = new ArrayList<>();
	private static final Map<ResourceIdentifier, ItemGroup> ITEM_GROUPS = new HashMap<>() {{
		put( ri( "itemgroup.brewing" ), ItemGroup.BREWING );
		put( ri( "itemgroup.building_blocks" ), ItemGroup.BUILDING_BLOCKS );
		put( ri( "itemgroup.combat" ), ItemGroup.COMBAT );
		put( ri( "itemgroup.decorations" ), ItemGroup.DECORATIONS );
		put( ri( "itemgroup.food" ), ItemGroup.FOOD );
		put( ri( "itemgroup.materials" ), ItemGroup.MATERIALS );
		put( ri( "itemgroup.redstone" ), ItemGroup.REDSTONE );
		put( ri( "itemgroup.tools" ), ItemGroup.TOOLS );
		put( ri( "itemgroup.transportation" ), ItemGroup.TRANSPORTATION );
		put( ri( "itemgroup.misc" ), ItemGroup.MISC );
	}};

	@Override
	public void register(@NotNull Block block, @NotNull ResourceIdentifier identifier) {
		this.register(block, identifier, ri( "itemgroup.misc" ) );
	}

	@Override
	public void register(@NotNull Block block, @NotNull ResourceIdentifier identifier, @Nullable ResourceIdentifier itemGroup ) {
		final Identifier id = new Identifier( identifier.toString() );
		final ForgeBlock forgeBlock = (ForgeBlock) new ForgeBlock( block ).setRegistryName( id );
		blocks.add( forgeBlock );
		if ( itemGroup != null ) {
			items.add(
				new BlockItem(
					forgeBlock,
					new net.minecraft.item.Item.Settings().group( getOrCreateItemGroup( itemGroup, identifier ) )
				).setRegistryName( id )
			);
		}
	}

	@Override
	public void register(@NotNull Item item, @NotNull ResourceIdentifier identifier) {
		@NotNull ForgeItem forgeItem = (ForgeItem) new ForgeItem( item )
				.setRegistryName( new Identifier( identifier.toString() ) );
		items.add( forgeItem );
		( (IItemMixin) forgeItem ).lc$setGroup( getOrCreateItemGroup( forgeItem.getItemImpl().group, identifier ) );
	}

	@Override
	public void register(@NotNull Entity entity, @NotNull ResourceIdentifier identifier) {

	}

	@Override
	public ResourceIdentifier registerItemGroup(@Nullable String name, @NotNull ResourceIdentifier icon) {
		ResourceIdentifier id = new ResourceIdentifier( icon.getNamespace(), name != null ? name : icon.getNamespace() );
		ITEM_GROUPS.computeIfAbsent(
			id,
			key -> new ItemGroup( id.getPath() ) {
				@Override
				public @NotNull ItemStack createIcon() {
					return new ItemStack( ForgeRegistries.ITEMS.getValue( new Identifier( icon.toString() ) ) );
				}
			}
		);
		return id;
	}

	@Override
	public boolean isRegistered(@NotNull RegistryKey key, @NotNull String id) {
		return switch (key) {
			case Item -> ForgeRegistries.ITEMS.containsKey( new Identifier(id) );
			case Block -> ForgeRegistries.BLOCKS.containsKey( new Identifier(id) );
			default -> false;
		};
	}

	@Override
	public boolean isRegistered(@NotNull RegistryKey key, @NotNull ResourceIdentifier id) {
		return this.isRegistered( key, id.toString() );
	}

	@Override
	public Object getVanillaRegistry(@NotNull RegistryKey type) {
		return switch (type) {
			case Item -> ForgeRegistries.ITEMS;
			case Block -> ForgeRegistries.BLOCKS;
			default -> null;
		};
	}

	public static ItemGroup getOrCreateItemGroup(@Nullable ResourceIdentifier itemGroup, ResourceIdentifier icon ) {
		if ( itemGroup == null )
			return null;
		if (! ITEM_GROUPS.containsKey( itemGroup ) )
			LoaderComplexForge.instance.getLoader().getRegistry().registerItemGroup( null, icon );
		return ITEM_GROUPS.get( itemGroup );
	}
}
