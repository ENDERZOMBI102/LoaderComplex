package com.enderzombi102.loadercomplex.common.abstraction.block;

import com.enderzombi102.loadercomplex.common.abstraction.utils.CIdentifier;

public interface Block {
	BlockMaterial getMaterial();
	void setIdentifier(CIdentifier id);
	void setIdentifier(String id);
}
