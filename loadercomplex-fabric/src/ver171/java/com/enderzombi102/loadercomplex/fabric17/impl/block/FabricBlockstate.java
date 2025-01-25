package com.enderzombi102.loadercomplex.fabric17.impl.block;

import com.enderzombi102.loadercomplex.api.minecraft.block.Blockstate;
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FabricBlockstate implements Blockstate {
	private final BlockState backingState;

	public FabricBlockstate(BlockState state) {
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
