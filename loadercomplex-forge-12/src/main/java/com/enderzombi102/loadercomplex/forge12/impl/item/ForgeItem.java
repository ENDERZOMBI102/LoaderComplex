package com.enderzombi102.loadercomplex.forge12.impl.item;

import com.enderzombi102.loadercomplex.common.abstraction.item.Item;
import com.enderzombi102.loadercomplex.common.abstraction.utils.CIdentifier;


public class ForgeItem extends net.minecraft.item.Item implements Item {


	@Override
	public void setIdentifier(CIdentifier id) {
		this.setUnlocalizedName( id.get() );
	}

	public void setIdentifier(String id) {
		this.setUnlocalizedName( id );
	}


}
