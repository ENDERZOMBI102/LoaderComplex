package com.enderzombi102.loadercomplex.forge12.impl.block;

import com.enderzombi102.loadercomplex.abstraction.utils.ResourceIdentifier;
import net.minecraft.block.material.Material;

public class ForgeBlock extends net.minecraft.block.Block {

	public ForgeBlock() {
		super( Material.CLOTH );
	}

	public void setIdentifier(ResourceIdentifier id) {
		this.setUnlocalizedName( id.toString() );
	}

	public void setIdentifier(String id) {
		this.setUnlocalizedName( id );
	}
}
