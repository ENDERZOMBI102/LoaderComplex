package com.enderzombi102.loadercomplex.forge18.impl.block;

import com.enderzombi102.loadercomplex.api.block.Blockstate;
import com.enderzombi102.loadercomplex.api.utils.ResourceIdentifier;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ForgeBlockstate implements Blockstate {
	private final BlockState backingState;

	public ForgeBlockstate(BlockState state) {
		backingState = state;
	}

	@Override
	public ResourceIdentifier getBlockType() {
		Identifier id = Registry.BLOCK.getId( backingState.getBlock() );
		return new ResourceIdentifier( id.getNamespace(), id.getPath() );
	}

	@Override
	public Object getObject() {
		return backingState;
	}
}
