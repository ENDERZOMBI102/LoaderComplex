package com.enderzombi102.loadercomplex.fabric12.impl;

import com.enderzombi102.loadercomplex.api.platform.Registry;
import com.enderzombi102.loadercomplex.fabric12.LoaderComplexFabric;
import com.enderzombi102.loadercomplex.fabric12.imixin.IItemMixin;
import com.enderzombi102.loadercomplex.fabric12.impl.block.FabricBlock;
import com.enderzombi102.loadercomplex.fabric12.impl.item.FabricItem;
import com.enderzombi102.loadercomplex.api.minecraft.block.Block;
import com.enderzombi102.loadercomplex.api.minecraft.entity.Entity;
import com.enderzombi102.loadercomplex.api.minecraft.item.Item;
import com.enderzombi102.loadercomplex.api.minecraft.util.RegistryKey;
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier;
import com.enderzombi102.loadercomplex.fabric12.impl.utils.ItemGroupBuilder;
import net.minecraft.item.BlockItem;
import net.minecraft.item.CreativeModeTab;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier.ri;

public class FabricRegistry implements Registry {
	private final List<net.minecraft.item.Item> registeredItems = new ArrayList<>();
	private static final Map<ResourceIdentifier, CreativeModeTab> CREATIVE_TABS = new HashMap<ResourceIdentifier, CreativeModeTab>() {{
		put( ri( "creativetab.brewing" ), CreativeModeTab.BREWING );
		put( ri( "creativetab.building_blocks" ), CreativeModeTab.BUILDING_BLOCKS );
		put( ri( "creativetab.combat" ), CreativeModeTab.COMBAT );
		put( ri( "creativetab.decorations" ), CreativeModeTab.DECORATIONS );
		put( ri( "creativetab.food" ), CreativeModeTab.FOOD );
		put( ri( "creativetab.materials" ), CreativeModeTab.MATERIALS );
		put( ri( "creativetab.redstone" ), CreativeModeTab.REDSTONE );
		put( ri( "creativetab.tools" ), CreativeModeTab.TOOLS );
		put( ri( "creativetab.transportation" ), CreativeModeTab.TRANSPORTATION );
		put( ri( "creativetab.misc" ), CreativeModeTab.MISC );
	}};

	@Override
	public void register( @NotNull Block block, @NotNull ResourceIdentifier identifier ) {
		this.register( block, identifier, ri( "itemgroup.misc" ) );
	}

	@Override
	public void register( @NotNull Block block, @NotNull ResourceIdentifier identifier, @Nullable ResourceIdentifier creativeTab ) {
		final Identifier id = new Identifier( identifier.toString() );
		final FabricBlock fabricBlock = (FabricBlock) new FabricBlock( block )
			.setKey( identifier.getNamespace() + '.' + identifier.getPath() )
			.setCreativeModeTab( getOrCreateCreativeTab( creativeTab, identifier ) );
		net.minecraft.block.Block.REGISTRY.register(
			net.minecraft.block.Block.REGISTRY.keySet().size(),
			id,
			fabricBlock
		);
		// THESE 4 LINES COSTED SO MANY HOURS OF DEBUGGING
		for ( net.minecraft.block.state.BlockState state : fabricBlock.stateDefinition().all() ) {
			int rawId = net.minecraft.block.Block.REGISTRY.getId( fabricBlock ) << 4 | fabricBlock.getMetadataFromState( state );
			net.minecraft.block.Block.STATE_REGISTRY.put( state, rawId );
		}
		if ( creativeTab != null ) {
			net.minecraft.item.Item blockItem = new BlockItem( fabricBlock ).setKey( identifier.getNamespace() + '.' + identifier.getPath() );
			net.minecraft.item.Item.REGISTRY.register(
				net.minecraft.item.Item.REGISTRY.keySet().size(),
				id, blockItem
			);
			( (IItemMixin) blockItem ).lc$getBlockItemRegistry().put( fabricBlock, blockItem );
			this.registeredItems.add( blockItem );
		}
	}

	@Override
	public void register( @NotNull Item item, @NotNull ResourceIdentifier identifier ) {
		FabricItem fabricItem = (FabricItem) new FabricItem( item ).setKey( identifier.getNamespace() + '.' + identifier.getPath() );
		net.minecraft.item.Item.REGISTRY.register(
			net.minecraft.item.Item.REGISTRY.keySet().size(),
			new Identifier( identifier.toString() ),
			fabricItem
		);
		this.registeredItems.add( fabricItem );
		fabricItem.setCreativeModeTab( getOrCreateCreativeTab( fabricItem.getItemImpl().creativeTab, identifier ) );
	}

	@Override
	public void register( @NotNull Entity entity, @NotNull ResourceIdentifier identifier ) {

	}

	@Override
	public ResourceIdentifier registerCreativeTab( @Nullable String name, @NotNull ResourceIdentifier icon ) {
		ResourceIdentifier id = new ResourceIdentifier( icon.getNamespace(), name != null ? name : icon.getNamespace() );
		CREATIVE_TABS.computeIfAbsent(
			id,
			key -> ItemGroupBuilder.build(
				new Identifier( id.toString() ),
				() -> new ItemStack( net.minecraft.item.Item.REGISTRY.get( new Identifier( icon.toString() ) ) )
			)
		);
		return id;
	}

	@Override
	public boolean isRegistered( @NotNull RegistryKey key, @NotNull ResourceIdentifier id ) {
		return this.isRegistered( key, id.toString() );
	}

	@Override
	public boolean isRegistered( @NotNull RegistryKey key, @NotNull String id ) {
		switch ( key ) {
			case Item:
				return net.minecraft.item.Item.REGISTRY.keySet().contains( new Identifier( id ) );
			case Block:
				return net.minecraft.block.Block.REGISTRY.keySet().contains( new Identifier( id ) );
			default:
				return false;
		}
	}

	@Override
	public Object getVanillaRegistry( @NotNull RegistryKey type ) {
		switch ( type ) {
			case Item:
				return net.minecraft.item.Item.REGISTRY;
			case Block:
				return net.minecraft.block.Block.REGISTRY;
			default:
				return null;
		}
	}

	public List<net.minecraft.item.Item> getRegisteredItems() {
		return this.registeredItems;
	}

	public CreativeModeTab getOrCreateCreativeTab( @Nullable ResourceIdentifier tab, ResourceIdentifier icon ) {
		if ( tab == null ) {
			return null;
		}
		if ( !CREATIVE_TABS.containsKey( tab ) ) {
			registerCreativeTab( null, icon );
		}
		return CREATIVE_TABS.get( tab );
	}
}
