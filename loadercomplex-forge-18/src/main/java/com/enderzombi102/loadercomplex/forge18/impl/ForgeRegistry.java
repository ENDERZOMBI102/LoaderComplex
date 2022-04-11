package com.enderzombi102.loadercomplex.forge18.impl;

import com.enderzombi102.loadercomplex.api.Registry;
import com.enderzombi102.loadercomplex.api.block.Block;
import com.enderzombi102.loadercomplex.api.entity.Entity;
import com.enderzombi102.loadercomplex.api.item.Item;
import com.enderzombi102.loadercomplex.api.utils.RegistryKey;
import com.enderzombi102.loadercomplex.api.utils.ResourceIdentifier;
import com.enderzombi102.loadercomplex.forge18.LoaderComplexForge;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.RegistryManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import static com.enderzombi102.loadercomplex.api.utils.ResourceIdentifier.ri;

public class ForgeRegistry implements Registry {
	private static final Map<ResourceIdentifier, CreativeModeTab> ITEM_GROUPS = new HashMap<>() {{
		put( ri( "itemgroup.brewing" ), CreativeModeTab.TAB_BREWING );
		put( ri( "itemgroup.building_blocks" ), CreativeModeTab.TAB_BUILDING_BLOCKS );
		put( ri( "itemgroup.combat" ), CreativeModeTab.TAB_COMBAT );
		put( ri( "itemgroup.decorations" ), CreativeModeTab.TAB_DECORATIONS );
		put( ri( "itemgroup.food" ), CreativeModeTab.TAB_FOOD );
		put( ri( "itemgroup.materials" ), CreativeModeTab.TAB_MATERIALS );
		put( ri( "itemgroup.redstone" ), CreativeModeTab.TAB_REDSTONE );
		put( ri( "itemgroup.tools" ), CreativeModeTab.TAB_TOOLS );
		put( ri( "itemgroup.transportation" ), CreativeModeTab.TAB_TRANSPORTATION );
		put( ri( "itemgroup.misc" ), CreativeModeTab.TAB_MISC );
	}};

	@Override
	public void register(@NotNull Block block, @NotNull ResourceIdentifier identifier) {
		this.register(block, identifier, ri( "itemgroup.misc" ) );
	}

	@Override
	public void register(@NotNull Block block, @NotNull ResourceIdentifier identifier, @Nullable ResourceIdentifier itemGroup ) {
		final ResourceLocation id = new ResourceLocation( identifier.toString() );
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
			key -> QuiltItemGroup.builder( new ResourceLocation( id.toString() ) )
				.icon( () -> new ItemStack(
					RegistryManager.ACTIVE.getRegistry( net.minecraft.core.Registry.ITEM_REGISTRY )
							.getValue( new ResourceLocation( icon.toString() ) )
				) )
				.displayText( new TranslatableText( "itemGroup." + ( name != null ? name : icon.getNamespace() ) ) )
				.build()
		);
		return id;
	}

	@Override
	public boolean isRegistered(@NotNull RegistryKey key, @NotNull String id) {
		return switch (key) {
			case Item -> RegistryManager.ACTIVE.getRegistry( net.minecraft.core.Registry.ITEM_REGISTRY ).containsKey( new ResourceLocation(id) );
			case Block -> RegistryManager.ACTIVE.getRegistry( net.minecraft.core.Registry.BLOCK_REGISTRY ).containsKey( new ResourceLocation(id) );
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
			case Item -> RegistryManager.ACTIVE.getRegistry( net.minecraft.core.Registry.ITEM_REGISTRY );
			case Block -> RegistryManager.ACTIVE.getRegistry( net.minecraft.core.Registry.BLOCK_REGISTRY );
			default -> null;
		};
	}

	public static CreativeModeTab getOrCreateItemGroup(@Nullable ResourceIdentifier itemGroup, ResourceIdentifier icon ) {
		if ( itemGroup == null )
			return null;
		if (! ITEM_GROUPS.containsKey( itemGroup ) )
			LoaderComplexForge.INSTANCE.getLoader().getRegistry().registerItemGroup( null, icon );
		return ITEM_GROUPS.get( itemGroup );
	}
}
