package com.enderzombi102.loadercomplex.fabric17.impl;

import com.enderzombi102.loadercomplex.api.Registry;
import com.enderzombi102.loadercomplex.api.minecraft.block.Block;
import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity;
import com.enderzombi102.loadercomplex.api.minecraft.item.Item;
import com.enderzombi102.loadercomplex.api.minecraft.util.RegistryKey;
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier;
import com.enderzombi102.loadercomplex.fabric17.LoaderComplexFabric;
import com.enderzombi102.loadercomplex.fabric17.imixin.IItemMixin;
import com.enderzombi102.loadercomplex.fabric17.impl.block.FabricBlock;
import com.enderzombi102.loadercomplex.fabric17.impl.item.FabricItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import static com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier.ri;

public class FabricRegistry implements Registry {
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
	public void register(@NotNull Block block, @NotNull ResourceIdentifier identifier ) {
		this.register(block, identifier, ri( "itemgroup.misc" ) );
	}

	@Override
	public void register( @NotNull Block block, @NotNull ResourceIdentifier identifier, @Nullable ResourceIdentifier itemGroup ) {
		final Identifier id = new Identifier( identifier.toString() );
		final FabricBlock fabricBlock = new FabricBlock( block );
		net.minecraft.util.registry.Registry.register(
			net.minecraft.util.registry.Registry.BLOCK,
			id,
			fabricBlock
		);
		if ( itemGroup != null ) {
			net.minecraft.util.registry.Registry.register(
				net.minecraft.util.registry.Registry.ITEM,
				id,
				new BlockItem(
					fabricBlock,
					new net.minecraft.item.Item.Settings().group( getOrCreateItemGroup( itemGroup, identifier ) )
				)
			);
		}
	}

	@Override
	public void register(@NotNull Item item, @NotNull ResourceIdentifier identifier) {
		@NotNull FabricItem fabricItem = new FabricItem( item );
		net.minecraft.util.registry.Registry.register(
			net.minecraft.util.registry.Registry.ITEM,
			new Identifier( identifier.toString() ),
			fabricItem
		);
		//noinspection ConstantConditions
		( (IItemMixin) fabricItem ).lc$setGroup( getOrCreateItemGroup( fabricItem.getItemImpl().group, identifier ) );
	}

	@Override
	public void register(@NotNull Entity entity, @NotNull ResourceIdentifier identifier) {

	}

	@Override
	public ResourceIdentifier registerItemGroup(@Nullable String name, @NotNull ResourceIdentifier icon ) {
		ResourceIdentifier id = new ResourceIdentifier( icon.getNamespace(), name != null ? name : icon.getNamespace() );
		ITEM_GROUPS.computeIfAbsent(
			id,
			key -> FabricItemGroupBuilder.create( new Identifier( id.toString() ) )
				.icon( () -> new ItemStack(
					net.minecraft.util.registry.Registry.ITEM.get( new Identifier( icon.toString() ) )
				))
				.build()
		);
		return id;
	}

	@Override
	public boolean isRegistered(@NotNull RegistryKey key, @NotNull String id) {
		return switch (key) {
			case Item -> net.minecraft.util.registry.Registry.ITEM.containsId(new Identifier(id));
			case Block -> net.minecraft.util.registry.Registry.BLOCK.containsId(new Identifier(id));
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
			LoaderComplexFabric.INSTANCE.getLoader().getRegistry().registerItemGroup( null, icon );
		return ITEM_GROUPS.get( itemGroup );
	}
}
