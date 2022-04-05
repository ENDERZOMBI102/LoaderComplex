package com.enderzombi102.loadercomplex.fabric12.mixin;

import com.enderzombi102.loadercomplex.fabric.BaseMixinPlugin;

public class Fabric12MixinPlugin extends BaseMixinPlugin {
	public Fabric12MixinPlugin() {
		super("1.12.2");
		this.clientMixins.add( "ReloadableResourceManagerImpl12Mixin" );
		this.clientMixins.add( "MinecraftClient12Mixin" );
		this.commonMixins.add( "SimpleRegistryMixin" );
		this.commonMixins.add( "ItemMixin" );
	}
}
