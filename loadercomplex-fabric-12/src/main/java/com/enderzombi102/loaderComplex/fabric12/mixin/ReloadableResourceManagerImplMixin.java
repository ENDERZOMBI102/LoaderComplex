package com.enderzombi102.loaderComplex.fabric12.mixin;

import com.enderzombi102.loaderComplex.fabric12.FabricResourcePack;
import com.enderzombi102.loaderComplex.fabric12.LoaderComplexFabric;
import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.resource.ResourcePack;
import net.minecraft.util.MetadataSerializer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ReloadableResourceManagerImpl.class)
public abstract class ReloadableResourceManagerImplMixin {

	@Shadow public abstract void add(ResourcePack pack);

	@Inject( method = "<init>", at = @At( "TAIL" ) )
	public void  onConstruct(MetadataSerializer metaSerializer, CallbackInfo ci) {
		for ( FabricResourcePack pack : LoaderComplexFabric.packs )
			this.add( pack );
	}
}
