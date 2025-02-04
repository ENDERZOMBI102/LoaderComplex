package com.enderzombi102.loadercomplex.fabric171.impl.block

import com.enderzombi102.loadercomplex.api.minecraft.block.BlockState
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier
import com.enderzombi102.loadercomplex.fabric171.impl.utils.toLC
import net.minecraft.util.registry.Registry
import net.minecraft.block.BlockState as McBlockState


class FabricBlockState( private val backingState: McBlockState ) : BlockState {
	override fun getBlockType(): ResourceIdentifier =
		Registry.BLOCK.getId( backingState.block ).toLC()

	override fun getObject(): Any =
		backingState
}
