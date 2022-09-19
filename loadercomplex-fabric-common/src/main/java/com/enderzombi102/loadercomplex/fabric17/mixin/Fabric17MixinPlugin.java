package com.enderzombi102.loadercomplex.fabric17.mixin;

import com.enderzombi102.loadercomplex.fabric.BaseMixinPlugin;

public class Fabric17MixinPlugin extends BaseMixinPlugin {

	public Fabric17MixinPlugin() {
		super("1.17.1");
		this.client( "ModResourcepackUtilMixin" );
		this.client( "MinecraftClient17Mixin" );
		this.client( "ScreenMixin" );
		this.common( "ItemMixin" );
		this.common( "MessageMixin" );
		this.common( "ServerPlayNetworkHandlerMixin" );
	}
}
