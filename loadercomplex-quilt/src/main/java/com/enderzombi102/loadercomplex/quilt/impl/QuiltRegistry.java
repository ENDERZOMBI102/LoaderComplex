package com.enderzombi102.loadercomplex.quilt.impl;

import com.enderzombi102.loadercomplex.minecraft.util.Registry;
import com.enderzombi102.loadercomplex.api.minecraft.block.Block;
import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity;
import com.enderzombi102.loadercomplex.api.minecraft.item.Item;
import com.enderzombi102.loadercomplex.api.minecraft.util.RegistryKey;
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier;
import com.enderzombi102.loadercomplex.quilt.LoaderComplexQuilt;
import com.enderzombi102.loadercomplex.quilt.imixin.IItemMixin;
import com.enderzombi102.loadercomplex.quilt.impl.block.QuiltBlock;
import com.enderzombi102.loadercomplex.quilt.impl.item.QuiltItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;

import java.util.HashMap;
import java.util.Map;

import static com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier.ri;

public class QuiltRegistry implements Registry {
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
		final QuiltBlock quiltBlock = new QuiltBlock( block );
		net.minecraft.util.registry.Registry.register(
			net.minecraft.util.registry.Registry.BLOCK,
			id,
			quiltBlock
		);
		if ( itemGroup != null ) {
			net.minecraft.util.registry.Registry.register(
				net.minecraft.util.registry.Registry.ITEM,
				id,
				new BlockItem(
					quiltBlock,
					new net.minecraft.item.Item.Settings().group( getOrCreateItemGroup( itemGroup, identifier ) )
				)
			);
		}
	}

	@Override
	public void register(@NotNull Item item, @NotNull ResourceIdentifier identifier) {
		@NotNull QuiltItem quiltItem = new QuiltItem( item );
		net.minecraft.util.registry.Registry.register(
			net.minecraft.util.registry.Registry.ITEM,
			new Identifier( identifier.toString() ),
			quiltItem
		);
		//noinspection ConstantConditions
		( (IItemMixin) quiltItem ).lc$setGroup( getOrCreateItemGroup( quiltItem.getItemImpl().group, identifier ) );

	}

	@Override
	public void register(@NotNull Entity entity, @NotNull ResourceIdentifier identifier) {

	}

	@Override
	public ResourceIdentifier registerItemGroup(@Nullable String name, @NotNull ResourceIdentifier icon) {
		ResourceIdentifier id = new ResourceIdentifier( icon.getNamespace(), name != null ? name : icon.getNamespace() );
		ITEM_GROUPS.computeIfAbsent(
			id,
			key -> QuiltItemGroup.builder( new Identifier( id.toString() ) )
				.icon( () -> new ItemStack(
						net.minecraft.util.registry.Registry.ITEM.get( new Identifier( icon.toString() ) )
				) )
				.displayText( new TranslatableText( "itemGroup." + ( name != null ? name : icon.getNamespace() ) ) )
				.build()
		);
		return id;
	}

	@Override
	public boolean isRegistered(@NotNull RegistryKey key, @NotNull String id) {
		return switch (key) {
			case Item -> net.minecraft.util.registry.Registry.ITEM.containsId( new Identifier(id) );
			case Block -> net.minecraft.util.registry.Registry.BLOCK.containsId( new Identifier(id) );
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
			case Item -> net.minecraft.util.registry.Registry.ITEM;
			case Block -> net.minecraft.util.registry.Registry.BLOCK;
			default -> null;
		};
	}

	public static ItemGroup getOrCreateItemGroup(@Nullable ResourceIdentifier itemGroup, ResourceIdentifier icon ) {
		if ( itemGroup == null )
			return null;
		if (! ITEM_GROUPS.containsKey( itemGroup ) )
			LoaderComplexQuilt.INSTANCE.getLoader().getRegistry().registerItemGroup( null, icon );
		return ITEM_GROUPS.get( itemGroup );
	}
}
