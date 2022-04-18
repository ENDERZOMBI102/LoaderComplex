package com.enderzombi102.loadercomplex.forge18.impl;

import com.enderzombi102.loadercomplex.api.Registry;
import com.enderzombi102.loadercomplex.api.block.Block;
import com.enderzombi102.loadercomplex.api.entity.Entity;
import com.enderzombi102.loadercomplex.api.item.Item;
import com.enderzombi102.loadercomplex.api.utils.RegistryKey;
import com.enderzombi102.loadercomplex.api.utils.ResourceIdentifier;
import com.enderzombi102.loadercomplex.forge18.LoaderComplexForge;
import com.enderzombi102.loadercomplex.forge18.imixin.IItemMixin;
import com.enderzombi102.loadercomplex.forge18.impl.block.ForgeBlock;
import com.enderzombi102.loadercomplex.forge18.impl.item.ForgeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraftforge.registries.RegistryManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import static com.enderzombi102.loadercomplex.api.utils.ResourceIdentifier.ri;

public class ForgeRegistry implements Registry {
	private static final net.minecraft.util.registry.RegistryKey<net.minecraft.util.registry.Registry<net.minecraft.block.Block>> BLOCK_KEY = net.minecraft.util.registry.Registry.BLOCK_KEY;
	private static final net.minecraft.util.registry.RegistryKey<net.minecraft.util.registry.Registry<net.minecraft.item.Item>> ITEM_KEY = net.minecraft.util.registry.Registry.ITEM_KEY;
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
		RegistryManager.ACTIVE.getRegistry( BLOCK_KEY ).register( forgeBlock );
		if ( itemGroup != null ) {
			RegistryManager.ACTIVE.getRegistry( ITEM_KEY ).register(
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
		RegistryManager.ACTIVE.getRegistry( ITEM_KEY ).register( forgeItem );
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
					return new ItemStack(
						RegistryManager.ACTIVE
							.getRegistry( ITEM_KEY )
							.getValue( new Identifier( icon.toString() ) )
					);
				}
			}
		);
		return id;
	}

	@Override
	public boolean isRegistered(@NotNull RegistryKey key, @NotNull String id) {
		return switch (key) {
			case Item -> RegistryManager.ACTIVE.getRegistry( net.minecraft.util.registry.Registry.ITEM_KEY ).containsKey( new Identifier(id) );
			case Block -> RegistryManager.ACTIVE.getRegistry( net.minecraft.util.registry.Registry.BLOCK_KEY ).containsKey( new Identifier(id) );
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
			case Item -> RegistryManager.ACTIVE.getRegistry( net.minecraft.util.registry.Registry.ITEM_KEY );
			case Block -> RegistryManager.ACTIVE.getRegistry( net.minecraft.util.registry.Registry.BLOCK_KEY );
			default -> null;
		};
	}

	public static ItemGroup getOrCreateItemGroup(@Nullable ResourceIdentifier itemGroup, ResourceIdentifier icon ) {
		if ( itemGroup == null )
			return null;
		if (! ITEM_GROUPS.containsKey( itemGroup ) )
			LoaderComplexForge.INSTANCE.getLoader().getRegistry().registerItemGroup( null, icon );
		return ITEM_GROUPS.get( itemGroup );
	}
}
