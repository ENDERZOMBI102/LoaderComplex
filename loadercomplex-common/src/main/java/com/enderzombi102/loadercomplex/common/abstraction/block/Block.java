package com.enderzombi102.loadercomplex.common.abstraction.block;

import com.enderzombi102.loadercomplex.common.abstraction.utils.Identifer;

public interface Block {
	BlockMaterial getMaterial();
	void setIdentifier(Identifer id);
}
