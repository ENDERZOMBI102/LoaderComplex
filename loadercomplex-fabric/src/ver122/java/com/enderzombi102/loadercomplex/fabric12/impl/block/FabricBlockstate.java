package com.enderzombi102.loadercomplex.fabric12.impl.block;

import com.enderzombi102.loadercomplex.api.minecraft.block.Blockstate;
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.resource.Identifier;

public class FabricBlockstate implements Blockstate {
	private final BlockState backingState;

	public FabricBlockstate( BlockState state ) {
		backingState = state;
	}

	@Override
	public ResourceIdentifier getBlockType() {
		Identifier id = Block.REGISTRY.getKey( backingState.getBlock() );
		return new ResourceIdentifier( id.getNamespace(), id.getPath() );
	}

	@Override
	public Object getObject() {
		return backingState;
	}
}
