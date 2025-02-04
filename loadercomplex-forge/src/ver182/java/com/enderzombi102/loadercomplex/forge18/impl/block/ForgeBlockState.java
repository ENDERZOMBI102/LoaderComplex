package com.enderzombi102.loadercomplex.forge18.impl.block;

import com.enderzombi102.loadercomplex.api.minecraft.block.BlockState;
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.RegistryManager;

public class ForgeBlockState implements BlockState {
	private final net.minecraft.block.BlockState backingState;

	public ForgeBlockState( net.minecraft.block.BlockState state) {
		backingState = state;
	}

	@Override
	public ResourceIdentifier getBlockType() {
		Identifier id = RegistryManager.ACTIVE.getRegistry( Registry.BLOCK_KEY ).getKey( backingState.getBlock() );
		return new ResourceIdentifier( id.getNamespace(), id.getPath() );
	}

	@Override
	public Object getObject() {
		return backingState;
	}
}
