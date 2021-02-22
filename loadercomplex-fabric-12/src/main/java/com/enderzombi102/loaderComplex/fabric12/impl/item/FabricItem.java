package com.enderzombi102.loaderComplex.fabric12.impl.item;

import com.enderzombi102.loadercomplex.common.abstraction.item.Item;
import com.enderzombi102.loadercomplex.common.abstraction.utils.CIdentifier;

public class FabricItem extends net.minecraft.item.Item implements Item {

	private CIdentifier cIdentifier;

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
