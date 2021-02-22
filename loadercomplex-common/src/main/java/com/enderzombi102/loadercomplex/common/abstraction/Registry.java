package com.enderzombi102.loadercomplex.common.abstraction;

import com.enderzombi102.loadercomplex.common.abstraction.block.Block;
import com.enderzombi102.loadercomplex.common.abstraction.block.BlockMaterial;
import com.enderzombi102.loadercomplex.common.abstraction.command.Command;
import com.enderzombi102.loadercomplex.common.abstraction.item.Item;
import com.enderzombi102.loadercomplex.common.abstraction.utils.RegistryType;

import javax.naming.OperationNotSupportedException;

public interface Registry {

	void register(Block block);
	void register(Block block, boolean registerItem);
	void register(Item Item);
	void register(Command command);

	boolean isRegistered(RegistryType type, String id ) throws OperationNotSupportedException;

	Block createBlock();
	Block createBlock(BlockMaterial mat);
	Item createItem();
	Command createCommand();

	Object getVanillaRegistry(RegistryType type);


}
