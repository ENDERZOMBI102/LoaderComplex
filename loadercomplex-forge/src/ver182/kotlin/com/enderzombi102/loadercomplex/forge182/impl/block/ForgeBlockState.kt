package com.enderzombi102.loadercomplex.forge182.impl.block

import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier
import net.minecraft.block.BlockState
import net.minecraft.util.registry.Registry
import net.minecraftforge.registries.RegistryManager

class ForgeBlockState(private val backingState: BlockState) :
	com.enderzombi102.loadercomplex.api.minecraft.block.BlockState {
	override fun getBlockType(): ResourceIdentifier {
		val id = RegistryManager.ACTIVE.getRegistry(Registry.BLOCK_KEY).getKey(backingState.block)
		return ResourceIdentifier(id!!.namespace, id.path)
	}

	override fun getObject(): Any {
		return backingState
	}
}
