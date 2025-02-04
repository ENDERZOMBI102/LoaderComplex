package com.enderzombi102.loadercomplex.forge122.impl.block

import com.enderzombi102.loadercomplex.api.minecraft.block.BlockState
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier
import com.enderzombi102.loadercomplex.forge122.impl.utils.toLC
import net.minecraft.block.state.IBlockState

class ForgeBlockState( private val backingState: IBlockState ) : BlockState {
	override fun getBlockType(): ResourceIdentifier =
		checkNotNull( backingState.block.registryName ) { "Block registry name is null! H O W" }.toLC()

	override fun getObject(): Any =
		this.backingState
}
