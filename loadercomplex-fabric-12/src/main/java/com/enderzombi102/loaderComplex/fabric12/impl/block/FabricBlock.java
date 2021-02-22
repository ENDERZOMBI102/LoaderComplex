package com.enderzombi102.loaderComplex.fabric12.impl.block;

import com.enderzombi102.loaderComplex.fabric12.impl.utils.BlockUtils;
import com.enderzombi102.loadercomplex.common.abstraction.block.Block;
import com.enderzombi102.loadercomplex.common.abstraction.block.BlockMaterial;
import com.enderzombi102.loadercomplex.common.abstraction.utils.CIdentifier;

public class FabricBlock extends net.minecraft.block.Block implements Block {

	private CIdentifier cIdentifier;

	public FabricBlock(BlockMaterial mat) {
		super( BlockUtils.getMat(mat) );
	}

	@Override
	public BlockMaterial getMaterial() {
		return BlockUtils.getBLockMat(this.material);
	}

	@Override
	public void setIdentifier(CIdentifier id) {
		this.cIdentifier = id;
	}

	public void setIdentifier(String id) {
		this.cIdentifier = CIdentifier.fromString(id);
	}


	public CIdentifier getIdentifier() {
		return this.cIdentifier;
	}
}
