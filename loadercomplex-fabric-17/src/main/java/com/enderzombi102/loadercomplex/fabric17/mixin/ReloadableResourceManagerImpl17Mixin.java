package com.enderzombi102.loadercomplex.fabric17.mixin;

import com.enderzombi102.loadercomplex.fabric17.FabricResourcePack;
import com.enderzombi102.loadercomplex.fabric17.LoaderComplexFabric;
import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ReloadableResourceManagerImpl.class)
public abstract class ReloadableResourceManagerImpl17Mixin {

	@Shadow public abstract void addPack(ResourcePack par1);

	@Inject( method = "<init>", at = @At( "TAIL" ) )
	public void  onConstruct(ResourceType type, CallbackInfo ci) {
		for ( FabricResourcePack pack : LoaderComplexFabric.packs )
			this.addPack( pack );
	}
}
