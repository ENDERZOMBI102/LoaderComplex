package com.enderzombi102.loadercomplex.common.abstraction;

import com.enderzombi102.loadercomplex.common.abstraction.block.Block;
import com.enderzombi102.loadercomplex.common.abstraction.block.BlockMaterial;
import com.enderzombi102.loadercomplex.common.abstraction.command.Command;
import com.enderzombi102.loadercomplex.common.abstraction.item.Item;
import com.enderzombi102.loadercomplex.common.abstraction.utils.Identifer;

public interface Registry {

	void register(Block block);
	void register(Block block, boolean registerItem);
	void register(Item Item);
	void register(Command command);

	boolean isRegistered(Identifer.IdType type, String id ) throws IllegalStateException;

	Block createBlock();
	Block createBlock(BlockMaterial mat);
	Item createItem();
	Command createCommand();

	Object getVanillaRegistry(Identifer.IdType type);


}
