package com.enderzombi102.loadercomplex.fabric12.mixin;

import com.enderzombi102.loadercomplex.fabric.BaseMixinPlugin;

public class Fabric12MixinPlugin extends BaseMixinPlugin {
	public Fabric12MixinPlugin() {
		super("1.12.2");
		this.client( "ReloadableResourceManagerImpl12Mixin" );
		this.client( "MinecraftClient12Mixin" );
		this.client( "ModelLoaderMixin" );
		this.common( "ItemGroupMixin" );
		this.common( "SimpleRegistryMixin" );
		this.common( "ItemMixin" );
	}
}
