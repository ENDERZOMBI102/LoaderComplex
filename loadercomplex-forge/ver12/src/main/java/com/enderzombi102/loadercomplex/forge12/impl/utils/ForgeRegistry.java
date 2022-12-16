package com.enderzombi102.loadercomplex.forge12.impl.utils;

import com.enderzombi102.loadercomplex.api.utils.Registry;
import com.enderzombi102.loadercomplex.minecraft.block.Block;
import com.enderzombi102.loadercomplex.minecraft.entity.Entity;
import com.enderzombi102.loadercomplex.minecraft.item.Item;
import com.enderzombi102.loadercomplex.minecraft.util.RegistryKey;
import com.enderzombi102.loadercomplex.minecraft.util.ResourceIdentifier;
import com.enderzombi102.loadercomplex.forge12.LoaderComplexForge;
import com.enderzombi102.loadercomplex.forge12.impl.block.ForgeBlock;
import com.enderzombi102.loadercomplex.forge12.impl.item.ForgeItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.RegistryManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.enderzombi102.loadercomplex.minecraft.util.ResourceIdentifier.ri;

public class ForgeRegistry implements Registry {
	private final HashMap<ResourceIdentifier, net.minecraft.block.Block> blocks = new HashMap<>();
	private final HashMap<ResourceIdentifier, net.minecraft.item.Item> items = new HashMap<>();
	private static final Map<ResourceIdentifier, CreativeTabs> ITEM_GROUPS = new HashMap<ResourceIdentifier, CreativeTabs>() {{
		put( ri( "itemgroup.brewing" ), CreativeTabs.BREWING );
		put( ri( "itemgroup.building_blocks" ), CreativeTabs.BUILDING_BLOCKS );
		put( ri( "itemgroup.combat" ), CreativeTabs.COMBAT );
		put( ri( "itemgroup.decorations" ), CreativeTabs.DECORATIONS );
		put( ri( "itemgroup.food" ), CreativeTabs.FOOD );
		put( ri( "itemgroup.materials" ), CreativeTabs.MATERIALS );
		put( ri( "itemgroup.redstone" ), CreativeTabs.REDSTONE );
		put( ri( "itemgroup.tools" ), CreativeTabs.TOOLS );
		put( ri( "itemgroup.transportation" ), CreativeTabs.TRANSPORTATION );
		put( ri( "itemgroup.misc" ), CreativeTabs.MISC );
	}};

	@Override
	public void register(@NotNull Block block, @NotNull ResourceIdentifier identifier) {
		this.register( block, identifier, ri( "itemgroup.misc" ) );
	}

	@Override
	public void register(@NotNull Block block, @NotNull ResourceIdentifier identifier, @Nullable ResourceIdentifier itemGroup ) {
		final ForgeBlock forgeBlock = new ForgeBlock( block );
		forgeBlock.setUnlocalizedName( identifier.getNamespace() + '.' + identifier.getPath() );
		this.blocks.put(identifier, forgeBlock);
		if ( itemGroup != null )
			this.items.put(
				identifier,
				new ItemBlock( forgeBlock )
					.setUnlocalizedName( identifier.getNamespace() + '.' + identifier.getPath() )
					.setCreativeTab( getOrCreateItemGroup( itemGroup, identifier ) )
			);
	}

	@Override
	public void register(@NotNull Item item, @NotNull ResourceIdentifier identifier) {
		this.items.put(
			identifier,
			new ForgeItem( item )
				.setUnlocalizedName( identifier.getNamespace() + '.' + identifier.getPath() )
				.setCreativeTab( getOrCreateItemGroup( item.group, identifier ) )
		);
	}

	@Override
	public void register(@NotNull Entity entity, @NotNull ResourceIdentifier identifier) {

	}

	@Override
	public ResourceIdentifier registerItemGroup( @Nullable String name, @NotNull ResourceIdentifier icon ) {
		ResourceIdentifier id = new ResourceIdentifier( icon.getNamespace(), name != null ? name : icon.getNamespace() );
		ITEM_GROUPS.computeIfAbsent(
			id,
			key -> new CreativeTabs( name != null ? icon.getNamespace() + "." + name : icon.getNamespace() ) {
				@SideOnly(Side.CLIENT)
				public @NotNull ItemStack getTabIconItem() {
					return new ItemStack(
						Objects.requireNonNull(
							net.minecraft.item.Item.getByNameOrId( icon.toString() ),
							String.format( "The icon given by %s was invalid", icon.getNamespace() )
						)
					);
				}
			}
		);
		return id;
	}

	@Override
	public boolean isRegistered(@NotNull RegistryKey key, @NotNull String identifier) {
		return false;
	}

	@Override
	public boolean isRegistered(@NotNull RegistryKey key, @NotNull ResourceIdentifier identifier) {
		return false;
	}

	@Override
	public Object getVanillaRegistry(@NotNull RegistryKey type) {
		switch (type) {
			case Item:
				return RegistryManager.ACTIVE.getRegistry(GameData.ITEMS);
			case Block:
				return RegistryManager.ACTIVE.getRegistry(GameData.BLOCKS);
			default:
				return null;
		}
	}

	public void onItemRegistry(RegistryEvent.Register<net.minecraft.item.Item> evt) {
		this.items.forEach( ( id, item ) -> evt.getRegistry().register( item.setRegistryName( id.toString() ) ) );
	}

	public void onBlockRegistry(RegistryEvent.Register<net.minecraft.block.Block> evt) {
		this.blocks.forEach( ( id, block ) -> evt.getRegistry().register( block.setRegistryName( id.toString() ) ) );
	}

	public void onModelRegistry() {
		this.items.forEach( ( id, item ) -> ModelLoader.setCustomModelResourceLocation(
			item,
			0,
			new ModelResourceLocation( id.toString(), "inventory" )
		));
	}

	public static CreativeTabs getOrCreateItemGroup(@Nullable ResourceIdentifier itemGroup, ResourceIdentifier icon ) {
		if ( itemGroup == null )
			return null;
		if (! ITEM_GROUPS.containsKey( itemGroup ) )
			LoaderComplexForge.INSTANCE.getLoader().getRegistry().registerItemGroup( null, icon );
		return ITEM_GROUPS.get( itemGroup );
	}
}
