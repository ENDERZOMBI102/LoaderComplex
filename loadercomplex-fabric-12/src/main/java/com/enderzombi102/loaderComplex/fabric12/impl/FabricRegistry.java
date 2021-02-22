package com.enderzombi102.loaderComplex.fabric12.impl;

import com.enderzombi102.loaderComplex.fabric12.impl.block.FabricBlock;
import com.enderzombi102.loaderComplex.fabric12.impl.item.FabricItem;
import com.enderzombi102.loadercomplex.common.abstraction.Registry;
import com.enderzombi102.loadercomplex.common.abstraction.block.Block;
import com.enderzombi102.loadercomplex.common.abstraction.block.BlockMaterial;
import com.enderzombi102.loadercomplex.common.abstraction.command.Command;
import com.enderzombi102.loadercomplex.common.abstraction.item.Item;
import com.enderzombi102.loadercomplex.common.abstraction.utils.RegistryType;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;

public class FabricRegistry implements Registry {

	@Override
	public void register(Block block) {
		register(block, true);
	}

	@Override
	public void register(Block block, boolean registerItem) {
		FabricBlock blockImpl = (FabricBlock) block;
		blockImpl.setTranslationKey( blockImpl.getIdentifier().get() );
		net.minecraft.block.Block.REGISTRY.put( new Identifier( blockImpl.getIdentifier().get() ) , blockImpl);
		if (registerItem) {
			net.minecraft.item.Item.REGISTRY.put(
					new Identifier( blockImpl.getIdentifier().get() ),
					new BlockItem( blockImpl ).setTranslationKey( blockImpl.getIdentifier().get() )
			);
		}
	}

	@Override
	public void register(Item item) {
		FabricItem itemImpl = (FabricItem) item;
		itemImpl.setTranslationKey( itemImpl.getIdentifier().get() );
		net.minecraft.item.Item.REGISTRY.put( new Identifier( itemImpl.getIdentifier().get() ), itemImpl );
	}

	@Override
	public void register(Command command) {

	}

	@Override
	public boolean isRegistered(RegistryType type, String id) throws IllegalStateException {
		switch (type) {
			case Item:
				return net.minecraft.item.Item.REGISTRY.getIds().contains( new net.minecraft.util.Identifier(id) );
			case Block:
				return net.minecraft.block.Block.REGISTRY.getIds().contains( new net.minecraft.util.Identifier( id ) );
			default:
				return false;
		}
	}

	@Override
	public Block createBlock() {
		return new FabricBlock(BlockMaterial.ROCK);
	}

	@Override
	public Block createBlock(BlockMaterial mat) {
		return new FabricBlock(mat);
	}

	@Override
	public Item createItem() {
		return new FabricItem();
	}

	@Override
	public Command createCommand() {
		return null;
	}

	@Override
	public Object getVanillaRegistry(RegistryType type) {
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
