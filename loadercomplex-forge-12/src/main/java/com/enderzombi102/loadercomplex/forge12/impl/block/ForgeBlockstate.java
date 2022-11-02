package com.enderzombi102.loadercomplex.forge12.impl.block;

import com.enderzombi102.loadercomplex.minecraft.block.Blockstate;
import com.enderzombi102.loadercomplex.minecraft.util.ResourceIdentifier;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public class ForgeBlockstate implements Blockstate {
	private final IBlockState backingState;

	public ForgeBlockstate(IBlockState blockState) {
		backingState = blockState;
	}

	@Override
	public ResourceIdentifier getBlockType() {
		ResourceLocation loc = this.backingState.getBlock().getRegistryName();
		if ( loc == null )
			throw new IllegalStateException( "Block registry name is null! H O W" );
		return new ResourceIdentifier( loc.getResourceDomain(), loc.getResourcePath() );
	}

	@Override
	public Object getObject() {
		return this.backingState;
	}
}
