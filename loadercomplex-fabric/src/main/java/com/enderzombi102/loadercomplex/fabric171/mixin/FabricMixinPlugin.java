package com.enderzombi102.loadercomplex.fabric171.mixin;

import com.enderzombi102.loadercomplex.fabric.BaseMixinPlugin;

public class FabricMixinPlugin extends BaseMixinPlugin {

	public FabricMixinPlugin() {
		super( "1.17.1" );
		this.clientMixins.add( "ModResourcepackUtilMixin" );
		this.clientMixins.add( "MinecraftClientMixin" );
		this.commonMixins.add( "ItemMixin" );
	}
}
