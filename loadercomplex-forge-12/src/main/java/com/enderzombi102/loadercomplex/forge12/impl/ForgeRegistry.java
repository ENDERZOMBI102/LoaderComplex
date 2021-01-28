package com.enderzombi102.loadercomplex.forge12.impl;

import com.enderzombi102.loadercomplex.common.abstraction.Registry;
import com.enderzombi102.loadercomplex.common.abstraction.block.Block;
import com.enderzombi102.loadercomplex.common.abstraction.block.BlockMaterial;
import com.enderzombi102.loadercomplex.common.abstraction.command.Command;
import com.enderzombi102.loadercomplex.common.abstraction.item.Item;
import com.enderzombi102.loadercomplex.common.abstraction.utils.Identifer;
import com.enderzombi102.loadercomplex.forge12.impl.block.ForgeBlock;
import com.enderzombi102.loadercomplex.forge12.impl.item.ForgeItem;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.RegistryManager;

import java.util.ArrayList;

public class ForgeRegistry implements Registry {

	private final ArrayList<net.minecraft.block.Block> blocks = new ArrayList<>();
	private final ArrayList<net.minecraft.item.Item> items = new ArrayList<>();

	@Override
	public void register(Block block) {
		this.register(block, true);
	}

	@Override
	public void register(Block block, boolean registerItem) {
		this.blocks.add( (ForgeBlock) block );
		if (registerItem) this.items.add( new ItemBlock( (ForgeBlock) block ) );
	}

	@Override
	public void register(Item item) {
		this.items.add( (ForgeItem) item );
	}

	@Override
	public void register(Command command) {

	}

	@Override
	public boolean isRegistered(Identifer.IdType type, String id) throws IllegalStateException{
		throw new IllegalStateException("Forge doesn't support this, please, check the loader type before calling this method");
	}

	@Override
	public Block createBlock() {
		return new ForgeBlock(BlockMaterial.ROCK);
	}

	@Override
	public Block createBlock(BlockMaterial mat) {
		return new ForgeBlock(mat);
	}

	@Override
	public Item createItem() {
		return new ForgeItem();
	}

	@Override
	public Command createCommand() {
		return null;
	}

	@Override
	public Object getVanillaRegistry(Identifer.IdType type) {
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
		for ( net.minecraft.item.Item item : this.items ) {
			evt.getRegistry().register( item.setRegistryName( item.getUnlocalizedName().replace("tile.", "").replace(":", ".") ) );
		}
	}

	public void onBlockRegistry(RegistryEvent.Register<net.minecraft.block.Block> evt) {
		for ( net.minecraft.block.Block block : this.blocks ) {
			evt.getRegistry().register( block.setRegistryName( block.getUnlocalizedName().replace("tile.", "").replace(":", ".") ) );
		}
	}

}
