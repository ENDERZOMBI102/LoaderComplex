package com.enderzombi102.loadercomplex.fabric12.mixin;

import com.enderzombi102.loadercomplex.fabric12.FabricResourcePack;
import com.enderzombi102.loadercomplex.fabric12.LoaderComplexFabric;
import net.minecraft.client.resource.manager.SimpleReloadableResourceManager;
import net.minecraft.client.resource.metadata.ResourceMetadataSerializerRegistry;
import net.minecraft.client.resource.pack.ResourcePack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin( SimpleReloadableResourceManager.class )
public abstract class SimpleReloadableResourceManagerMixin {
	@Shadow
	public abstract void add( ResourcePack pack );

	@Inject( method = "<init>", at = @At( "TAIL" ) )
	public void onConstruct( ResourceMetadataSerializerRegistry metaSerializer, CallbackInfo ci ) {
		for ( FabricResourcePack pack : LoaderComplexFabric.packs ) {
			this.add( pack );
		}
	}
}
