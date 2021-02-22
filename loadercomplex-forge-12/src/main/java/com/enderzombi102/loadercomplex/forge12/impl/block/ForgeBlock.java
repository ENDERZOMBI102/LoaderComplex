package com.enderzombi102.loadercomplex.forge12.impl.block;

import com.enderzombi102.loadercomplex.common.abstraction.block.Block;
import com.enderzombi102.loadercomplex.common.abstraction.block.BlockMaterial;
import com.enderzombi102.loadercomplex.forge12.impl.utils.BlockUtils;
import com.enderzombi102.loadercomplex.common.abstraction.utils.CIdentifier;

public class ForgeBlock extends net.minecraft.block.Block implements Block {

	public ForgeBlock(BlockMaterial mat) {
		super( BlockUtils.getMat(mat) );
	}

	@Override
	public BlockMaterial getMaterial() {
		return BlockUtils.getBLockMat(this.blockMaterial);
	}

	@Override
	public void setIdentifier(CIdentifier id) {
		this.setUnlocalizedName( id.get() );
	}

	public void setIdentifier(String id) {
		this.setUnlocalizedName( id );
	}

}
