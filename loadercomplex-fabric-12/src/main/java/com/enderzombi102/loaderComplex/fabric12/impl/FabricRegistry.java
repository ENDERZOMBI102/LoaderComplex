package com.enderzombi102.loaderComplex.fabric12.impl;

import com.enderzombi102.loaderComplex.fabric12.impl.block.FabricBlock;
import com.enderzombi102.loaderComplex.fabric12.impl.item.FabricItem;
import com.enderzombi102.loadercomplex.abstraction.Registry;
import com.enderzombi102.loadercomplex.abstraction.block.Block;
import com.enderzombi102.loadercomplex.abstraction.entity.Entity;
import com.enderzombi102.loadercomplex.abstraction.item.Item;
import com.enderzombi102.loadercomplex.abstraction.utils.RegistryKey;
import com.enderzombi102.loadercomplex.abstraction.utils.ResourceIdentifier;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;

import javax.naming.OperationNotSupportedException;

public class FabricRegistry implements Registry {


	@Override
	public void register(Block block, ResourceIdentifier identifier) {
		this.register(block, true, identifier);
	}

	@Override
	public void register(Block block, boolean registerItem, ResourceIdentifier identifier) {
		final Identifier id = new Identifier( identifier.toString() );
		final FabricBlock fabricBlock = new FabricBlock( block );
		fabricBlock.setTranslationKey( identifier.toString() );
		net.minecraft.block.Block.REGISTRY.set(
				net.minecraft.block.Block.REGISTRY.getIds().size(),
				id,
				fabricBlock
		);
		if (registerItem) net.minecraft.item.Item.REGISTRY.set(
				net.minecraft.item.Item.REGISTRY.getIds().size(),
				id,
				new BlockItem( fabricBlock ).setTranslationKey( identifier.toString() )
		);
	}

	@Override
	public void register(Item item, ResourceIdentifier identifier) {
		net.minecraft.item.Item.REGISTRY.set(
				net.minecraft.item.Item.REGISTRY.getIds().size(),
				new Identifier( identifier.toString() ),
				new FabricItem( item ).setTranslationKey( identifier.toString() )
		);
	}

	@Override
	public void register(Entity entity, ResourceIdentifier identifier) {

	}

	@Override
	public boolean isRegistered(RegistryKey key, String id) throws OperationNotSupportedException {
		switch (key) {
			case Item:
				return net.minecraft.item.Item.REGISTRY.getIds().contains( new net.minecraft.util.Identifier(id) );
			case Block:
				return net.minecraft.block.Block.REGISTRY.getIds().contains( new net.minecraft.util.Identifier( id ) );
			default:
				return false;
		}
	}

	@Override
	public boolean isRegistered(RegistryKey key, ResourceIdentifier id) throws OperationNotSupportedException {
		return this.isRegistered( key, id.toString() );
	}

	@Override
	public Object getVanillaRegistry(RegistryKey type) {
		switch (type) {
			case Item:
				return net.minecraft.item.Item.REGISTRY;
			case Block:
				return net.minecraft.block.Block.REGISTRY;
			default:
				return null;
		}
	}
}
