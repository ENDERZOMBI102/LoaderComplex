package com.enderzombi102.loaderComplex.fabric12.impl.item;


import com.enderzombi102.loadercomplex.abstraction.item.Item;
import com.enderzombi102.loadercomplex.abstraction.utils.ResourceIdentifier;

public class FabricItem extends net.minecraft.item.Item implements Item {

	private ResourceIdentifier identifier;

	public void setIdentifier(ResourceIdentifier id) {
		this.identifier = id;
	}

	public void setIdentifier(String namespace, String id) {
		this.identifier = new ResourceIdentifier(namespace, id);
	}

	public ResourceIdentifier getIdentifier() {
		return this.identifier;
	}
}
