package com.enderzombi102.loadercomplex.fabric12.mixin;

import com.enderzombi102.loadercomplex.fabric.BaseMixinPlugin;

public class Fabric12MixinPlugin extends BaseMixinPlugin {
	public Fabric12MixinPlugin() {
		super("1.12.2");
		this.clientMixins.add( "SimpleReloadableResourceManagerMixin" );
		this.clientMixins.add( "ItemRendererMixin" );
		this.clientMixins.add( "ModelBakeryMixin" );
		this.clientMixins.add( "MinecraftMixin" );
		this.commonMixins.add( "CreativeModeTabMixin" );
		this.commonMixins.add( "IdRegistryMixin" );
		this.commonMixins.add( "ItemMixin" );
	}
}
