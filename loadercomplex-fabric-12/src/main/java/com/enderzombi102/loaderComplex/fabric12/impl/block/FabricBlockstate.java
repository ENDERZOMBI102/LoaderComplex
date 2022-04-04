package com.enderzombi102.loaderComplex.fabric12.impl.block;

import com.enderzombi102.loadercomplex.api.block.Blockstate;
import com.enderzombi102.loadercomplex.api.utils.ResourceIdentifier;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;

public class FabricBlockstate implements Blockstate {
	private final BlockState backingState;

	public FabricBlockstate(BlockState state) {
		backingState = state;
	}

	@Override
	public ResourceIdentifier getBlockType() {
		Identifier id = Block.REGISTRY.getIdentifier( backingState.getBlock() );
		return new ResourceIdentifier( id.getNamespace(), id.getPath() );
	}

	@Override
	public Object getObject() {
		return backingState;
	}
}
