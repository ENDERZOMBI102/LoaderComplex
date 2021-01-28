package com.enderzombi102.loadercomplex.forge12.impl.block;

import com.enderzombi102.loadercomplex.common.abstraction.block.Block;
import com.enderzombi102.loadercomplex.common.abstraction.block.BlockMaterial;
import com.enderzombi102.loadercomplex.forge12.impl.utils.BlockUtils;
import com.enderzombi102.loadercomplex.common.abstraction.utils.Identifer;
import net.minecraft.block.material.MapColor;

public class ForgeBlock extends net.minecraft.block.Block implements Block {

	public ForgeBlock(BlockMaterial mat, MapColor col) {
		super( BlockUtils.getMat(mat), col);
	}

	public ForgeBlock(BlockMaterial mat) {
		super( BlockUtils.getMat(mat) );
	}

	@Override
	public BlockMaterial getMaterial() {
		return BlockUtils.getBLockMat(this.blockMaterial);
	}

	@Override
	public void setIdentifier(Identifer id) {
		this.setUnlocalizedName( id.get() );
	}
}
