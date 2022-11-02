package com.enderzombi102.loadercomplex.quilt.impl.block;

import com.enderzombi102.loadercomplex.minecraft.block.Blockstate;
import com.enderzombi102.loadercomplex.minecraft.util.ResourceIdentifier;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class QuiltBlockstate implements Blockstate {
	private final BlockState backingState;

	public QuiltBlockstate(BlockState state) {
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
