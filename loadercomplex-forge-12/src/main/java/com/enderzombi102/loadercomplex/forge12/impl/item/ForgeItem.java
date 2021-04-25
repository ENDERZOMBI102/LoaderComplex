package com.enderzombi102.loadercomplex.forge12.impl.item;


import com.enderzombi102.loadercomplex.abstraction.item.Item;
import com.enderzombi102.loadercomplex.abstraction.utils.ResourceIdentifier;

public class ForgeItem extends net.minecraft.item.Item implements Item {


	public void setIdentifier(ResourceIdentifier id) {
		this.setUnlocalizedName( id.toString() );
	}

	public void setIdentifier(String id) {
		this.setUnlocalizedName( id );
	}


}
