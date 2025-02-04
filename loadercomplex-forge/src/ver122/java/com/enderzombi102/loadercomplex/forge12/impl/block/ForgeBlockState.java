package com.enderzombi102.loadercomplex.forge12.impl.block;

import com.enderzombi102.loadercomplex.api.minecraft.block.BlockState;
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public class ForgeBlockState implements BlockState {
	private final IBlockState backingState;

	public ForgeBlockState( IBlockState blockState ) {
		backingState = blockState;
	}

	@Override
	public ResourceIdentifier getBlockType() {
		ResourceLocation loc = this.backingState.getBlock().getRegistryName();
		if ( loc == null ) {
			throw new IllegalStateException( "Block registry name is null! H O W" );
		}
		return new ResourceIdentifier( loc.getResourceDomain(), loc.getResourcePath() );
	}

	@Override
	public Object getObject() {
		return this.backingState;
	}
}
