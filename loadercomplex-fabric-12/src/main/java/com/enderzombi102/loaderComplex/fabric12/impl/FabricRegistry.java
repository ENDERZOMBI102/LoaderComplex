package com.enderzombi102.loaderComplex.fabric12.impl;

import com.enderzombi102.loaderComplex.fabric12.impl.block.FabricBlock;
import com.enderzombi102.loaderComplex.fabric12.impl.item.FabricItem;
import com.enderzombi102.loadercomplex.api.Registry;
import com.enderzombi102.loadercomplex.api.block.Block;
import com.enderzombi102.loadercomplex.api.entity.Entity;
import com.enderzombi102.loadercomplex.api.item.Item;
import com.enderzombi102.loadercomplex.api.utils.RegistryKey;
import com.enderzombi102.loadercomplex.api.utils.ResourceIdentifier;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;

public class FabricRegistry implements Registry {


	@Override
	public void register(Block block, ResourceIdentifier identifier) {
		this.register(block, true, identifier);
	}

	@Override
	public void register(Block block, boolean registerItem, ResourceIdentifier identifier) {
		final Identifier id = new Identifier( identifier.toString() );
		final FabricBlock fabricBlock = new FabricBlock( block );
		fabricBlock.setTranslationKey( identifier.getNamespace() + '.' + identifier.getPath() );
		net.minecraft.block.Block.REGISTRY.add(
				net.minecraft.block.Block.REGISTRY.getKeySet().size(),
				id,
				fabricBlock
		);
		if (registerItem) net.minecraft.item.Item.REGISTRY.add(
				net.minecraft.item.Item.REGISTRY.getKeySet().size(),
				id,
				new BlockItem( fabricBlock ).setTranslationKey( identifier.getNamespace() + '.' + identifier.getPath() )
		);
	}

	@Override
	public void register(Item item, ResourceIdentifier identifier) {
		net.minecraft.item.Item.REGISTRY.add(
				net.minecraft.item.Item.REGISTRY.getKeySet().size(),
				new Identifier( identifier.toString() ),
				new FabricItem( item ).setTranslationKey( identifier.getNamespace() + '.' + identifier.getPath() )
		);
	}

	@Override
	public void register(Entity entity, ResourceIdentifier identifier) {

	}

	@Override
	public boolean isRegistered(RegistryKey key, String id) {
		switch (key) {
			case Item:
				return net.minecraft.item.Item.REGISTRY.getKeySet().contains( new net.minecraft.util.Identifier(id) );
			case Block:
				return net.minecraft.block.Block.REGISTRY.getKeySet().contains( new net.minecraft.util.Identifier( id ) );
			default:
				return false;
		}
	}

	@Override
	public boolean isRegistered(RegistryKey key, ResourceIdentifier id) {
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
