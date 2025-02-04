package com.enderzombi102.loadercomplex.fabric122.mixin;

import com.enderzombi102.loadercomplex.fabric.BaseMixinPlugin;

public class FabricMixinPlugin extends BaseMixinPlugin {
	public FabricMixinPlugin() {
		super( "1.12.2" );
		this.clientMixins.add( "SimpleReloadableResourceManagerMixin" );
		this.clientMixins.add( "ItemRendererMixin" );
		this.clientMixins.add( "ModelBakeryMixin" );
		this.clientMixins.add( "MinecraftMixin" );
		this.commonMixins.add( "CreativeModeTabMixin" );
		this.commonMixins.add( "IdRegistryMixin" );
		this.commonMixins.add( "ItemMixin" );
	}
}
