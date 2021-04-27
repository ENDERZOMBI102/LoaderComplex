package com.enderzombi102.loadercomplex.forge12.impl;

import com.enderzombi102.loadercomplex.abstraction.Registry;
import com.enderzombi102.loadercomplex.abstraction.block.Block;
import com.enderzombi102.loadercomplex.abstraction.entity.Entity;
import com.enderzombi102.loadercomplex.abstraction.item.Item;
import com.enderzombi102.loadercomplex.abstraction.utils.RegistryKey;
import com.enderzombi102.loadercomplex.abstraction.utils.ResourceIdentifier;
import com.enderzombi102.loadercomplex.forge12.impl.block.ForgeBlock;
import com.enderzombi102.loadercomplex.forge12.impl.item.ForgeItem;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.RegistryManager;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
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
//		this.blocks.put(identifier, (ForgeBlock) block);
//		this.items.put( identifier, new ItemBlock( (ForgeBlock) block ) );
	}

	@Override
	public void register(Item item, ResourceIdentifier identifier) {
//		this.items.put( identifier, (ForgeItem) item );
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
