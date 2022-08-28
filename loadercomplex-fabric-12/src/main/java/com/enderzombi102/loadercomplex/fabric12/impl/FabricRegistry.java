package com.enderzombi102.loadercomplex.fabric12.impl;

import com.enderzombi102.loadercomplex.fabric12.LoaderComplexFabric;
import com.enderzombi102.loadercomplex.fabric12.imixin.IItemMixin;
import com.enderzombi102.loadercomplex.fabric12.impl.block.FabricBlock;
import com.enderzombi102.loadercomplex.fabric12.impl.item.FabricItem;
import com.enderzombi102.loadercomplex.api.Registry;
import com.enderzombi102.loadercomplex.api.block.Block;
import com.enderzombi102.loadercomplex.api.entity.Entity;
import com.enderzombi102.loadercomplex.api.item.Item;
import com.enderzombi102.loadercomplex.api.utils.RegistryKey;
import com.enderzombi102.loadercomplex.api.utils.ResourceIdentifier;
import com.enderzombi102.loadercomplex.fabric12.impl.utils.ItemGroupBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.itemgroup.ItemGroup;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.enderzombi102.loadercomplex.api.utils.ResourceIdentifier.ri;

public class FabricRegistry implements Registry {
	private final List<net.minecraft.item.Item> registeredItems = new ArrayList<>();
	private static final Map<ResourceIdentifier, ItemGroup> ITEM_GROUPS = new HashMap<ResourceIdentifier, ItemGroup>() {{
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
	public void register( @NotNull Block block, @NotNull ResourceIdentifier identifier ) {
		this.register( block, identifier, ri( "itemgroup.misc" ) );
	}

	@Override
	public void register( @NotNull Block block, @NotNull ResourceIdentifier identifier, @Nullable ResourceIdentifier itemGroup ) {
		final Identifier id = new Identifier( identifier.toString() );
		final FabricBlock fabricBlock = (FabricBlock) new FabricBlock( block )
			.setTranslationKey( identifier.getNamespace() + '.' + identifier.getPath() )
			.setItemGroup( getOrCreateItemGroup( itemGroup, identifier ) );
		net.minecraft.block.Block.REGISTRY.add(
			net.minecraft.block.Block.REGISTRY.getKeySet().size(),
			id,
			fabricBlock
		);
		// THESE 4 LINES CONSTED SO MANY HOURS OF DEBUGGING
		for ( BlockState state : fabricBlock.getStateManager().getBlockStates() ) {
			int rawId = net.minecraft.block.Block.REGISTRY.getRawId(fabricBlock) << 4 | fabricBlock.getData( state );
			net.minecraft.block.Block.BLOCK_STATES.set( state, rawId );
		}
		if ( itemGroup != null ) {
			net.minecraft.item.Item blockItem = new BlockItem(fabricBlock)
					.setTranslationKey( identifier.getNamespace() + '.' + identifier.getPath() );
			net.minecraft.item.Item.REGISTRY.add(
				net.minecraft.item.Item.REGISTRY.getKeySet().size(),
				id, blockItem
			);
			( (IItemMixin) blockItem ).lc$getBlockItemRegistry().put( fabricBlock, blockItem );
			registeredItems.add( blockItem );
		}
	}

	@Override
	public void register(@NotNull Item item, @NotNull ResourceIdentifier identifier) {
		FabricItem fabricItem = (FabricItem) new FabricItem( item )
				.setTranslationKey( identifier.getNamespace() + '.' + identifier.getPath() );
		net.minecraft.item.Item.REGISTRY.add(
			net.minecraft.item.Item.REGISTRY.getKeySet().size(),
			new Identifier( identifier.toString() ),
			fabricItem
		);
		registeredItems.add( fabricItem );
		fabricItem.setItemGroup( getOrCreateItemGroup( fabricItem.getItemImpl().group, identifier ) );
	}

	@Override
	public void register(@NotNull Entity entity, @NotNull ResourceIdentifier identifier) {

	}

	@Override
	public ResourceIdentifier registerItemGroup(@Nullable String name, @NotNull ResourceIdentifier icon) {
		ResourceIdentifier id = new ResourceIdentifier( icon.getNamespace(), name != null ? name : icon.getNamespace() );
		ITEM_GROUPS.computeIfAbsent(
			id,
			key -> ItemGroupBuilder.create( new Identifier( id.toString() ) )
				.icon( () -> new ItemStack(
					net.minecraft.item.Item.REGISTRY.get( new Identifier( icon.toString() ) )
				))
				.build()
		);
		return id;
	}

	@Override
	public boolean isRegistered(@NotNull RegistryKey key, @NotNull String id) {
		switch (key) {
			case Item:
				return net.minecraft.item.Item.REGISTRY.getKeySet().contains( new Identifier(id) );
			case Block:
				return net.minecraft.block.Block.REGISTRY.getKeySet().contains( new Identifier( id ) );
			default:
				return false;
		}
	}

	@Override
	public boolean isRegistered(@NotNull RegistryKey key, @NotNull ResourceIdentifier id) {
		return this.isRegistered( key, id.toString() );
	}

	@Override
	public Object getVanillaRegistry(@NotNull RegistryKey type) {
		switch (type) {
			case Item:
				return net.minecraft.item.Item.REGISTRY;
			case Block:
				return net.minecraft.block.Block.REGISTRY;
			default:
				return null;
		}
	}

	public List<net.minecraft.item.Item> getRegisteredItems() {
		return registeredItems;
	}

	public static ItemGroup getOrCreateItemGroup(@Nullable ResourceIdentifier itemGroup, ResourceIdentifier icon ) {
		if ( itemGroup == null )
			return null;
		if (! ITEM_GROUPS.containsKey( itemGroup ) )
			LoaderComplexFabric.INSTANCE.getLoader().getRegistry().registerItemGroup( null, icon );
		return ITEM_GROUPS.get( itemGroup );
	}
}
