package com.enderzombi102.loadercomplex.forge12.impl;

import com.enderzombi102.loadercomplex.api.Registry;
import com.enderzombi102.loadercomplex.api.block.Block;
import com.enderzombi102.loadercomplex.api.entity.Entity;
import com.enderzombi102.loadercomplex.api.item.Item;
import com.enderzombi102.loadercomplex.api.utils.RegistryKey;
import com.enderzombi102.loadercomplex.api.utils.ResourceIdentifier;
import com.enderzombi102.loadercomplex.forge12.impl.block.ForgeBlock;
import com.enderzombi102.loadercomplex.forge12.impl.item.ForgeItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.RegistryManager;

import javax.naming.OperationNotSupportedException;
import java.util.HashMap;

public class ForgeRegistry implements Registry {

	private final HashMap<ResourceIdentifier, net.minecraft.block.Block> blocks = new HashMap<>();
	private final HashMap<ResourceIdentifier, net.minecraft.item.Item> items = new HashMap<>();

	@Override
	public void register(Block block, ResourceIdentifier identifier) {
		this.register(block, true, identifier);
	}

	@Override
	public void register(Block block, boolean registerItem, ResourceIdentifier identifier) {
		final ForgeBlock forgeBlock = new ForgeBlock( block );
		forgeBlock.setUnlocalizedName( identifier.getNamespace() + '.' + identifier.getPath() );
		this.blocks.put(identifier, forgeBlock);
		if (registerItem) this.items.put(
				identifier,
				new ItemBlock( forgeBlock ).setUnlocalizedName( identifier.getNamespace() + '.' + identifier.getPath() )
		);
	}

	@Override
	public void register(Item item, ResourceIdentifier identifier) {
		this.items.put(
				identifier,
				new ForgeItem( item ).setUnlocalizedName( identifier.getNamespace() + '.' + identifier.getPath() )
		);
	}

	@Override
	public void register(Entity entity, ResourceIdentifier identifier) {

	}

	@Override
	public boolean isRegistered(RegistryKey key, String identifier) throws OperationNotSupportedException {
		return false;
	}

	@Override
	public boolean isRegistered(RegistryKey key, ResourceIdentifier identifier) throws OperationNotSupportedException {
		return false;
	}

	@Override
	public Object getVanillaRegistry(RegistryKey type) {
		switch (type) {
			case Item:
				return RegistryManager.VANILLA.getRegistry(GameData.ITEMS);
			case Block:
				return RegistryManager.VANILLA.getRegistry(GameData.BLOCKS);
			default:
				return null;
		}
	}

	public void onItemRegistry(RegistryEvent.Register<net.minecraft.item.Item> evt) {
		this.items.forEach( ( id, item ) -> {
			evt.getRegistry().register( item.setRegistryName( id.toString() ) );
		});
	}

	public void onBlockRegistry(RegistryEvent.Register<net.minecraft.block.Block> evt) {
		this.blocks.forEach( ( id, block ) -> {
			evt.getRegistry().register( block.setRegistryName( id.toString() ) );
		});
	}

}
