package com.enderzombi102.loadercomplex.fabric17.mixin;

import com.enderzombi102.loadercomplex.fabric.BaseMixinPlugin;

public class Fabric17MixinPlugin extends BaseMixinPlugin {

	public Fabric17MixinPlugin() {
		super("1.17.1");
		this.clientMixins.add( "ModResourcepackUtilMixin" );
		this.clientMixins.add( "MinecraftClient17Mixin" );
		this.commonMixins.add( "ItemMixin" );
	}
}
