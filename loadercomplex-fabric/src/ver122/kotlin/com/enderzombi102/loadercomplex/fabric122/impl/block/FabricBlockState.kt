package com.enderzombi102.loadercomplex.fabric122.impl.block

import com.enderzombi102.loadercomplex.api.minecraft.block.BlockState
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier
import net.minecraft.block.Block
import net.minecraft.block.state.BlockState as McBlockState

class FabricBlockState( private val backingState: McBlockState ) : BlockState {
	override fun getBlockType(): ResourceIdentifier {
		val id = Block.REGISTRY.getKey( backingState.block )
		return ResourceIdentifier( id.namespace, id.path )
	}

	override fun getObject(): Any {
		return backingState
	}
}
