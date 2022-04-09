package com.enderzombi102.loadercomplex.quilt.impl;

import com.enderzombi102.loadercomplex.api.Registry;
import com.enderzombi102.loadercomplex.api.block.Block;
import com.enderzombi102.loadercomplex.api.entity.Entity;
import com.enderzombi102.loadercomplex.api.item.Item;
import com.enderzombi102.loadercomplex.api.utils.RegistryKey;
import com.enderzombi102.loadercomplex.api.utils.ResourceIdentifier;
import com.enderzombi102.loadercomplex.quilt.impl.block.QuiltBlock;
import com.enderzombi102.loadercomplex.quilt.impl.item.QuiltItem;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;

public class QuiltRegistry implements Registry {

	@Override
	public void register(Block block, ResourceIdentifier identifier) {
		this.register(block, true, identifier);
	}

	@Override
	public void register(Block block, boolean registerItem, ResourceIdentifier identifier) {
		final Identifier id = new Identifier( identifier.toString() );
		final QuiltBlock fabricBlock = new QuiltBlock( block );
		net.minecraft.util.registry.Registry.register(
			net.minecraft.util.registry.Registry.BLOCK,
			id,
			fabricBlock
		);
		if (registerItem) {
			net.minecraft.util.registry.Registry.register(
				net.minecraft.util.registry.Registry.ITEM,
				id,
				new BlockItem( fabricBlock, new net.minecraft.item.Item.Settings() )
			);
		}
	}

	@Override
	public void register(Item item, ResourceIdentifier identifier) {
		net.minecraft.util.registry.Registry.register(
			net.minecraft.util.registry.Registry.ITEM,
			new Identifier( identifier.toString() ),
			new QuiltItem( item )
		);
	}

	@Override
	public void register(Entity entity, ResourceIdentifier identifier) {

	}

	@Override
	public boolean isRegistered(RegistryKey key, String id) {
		return switch (key) {
			case Item -> net.minecraft.util.registry.Registry.ITEM.containsId(new Identifier(id));
			case Block -> net.minecraft.util.registry.Registry.BLOCK.containsId(new Identifier(id));
			default -> false;
		};
	}

	@Override
	public boolean isRegistered(RegistryKey key, ResourceIdentifier id) {
		return this.isRegistered( key, id.toString() );
	}

	@Override
	public Object getVanillaRegistry(RegistryKey type) {
		return switch (type) {
			case Item -> net.minecraft.util.registry.Registry.ITEM;
			case Block -> net.minecraft.util.registry.Registry.BLOCK;
			default -> null;
		};
	}
}
