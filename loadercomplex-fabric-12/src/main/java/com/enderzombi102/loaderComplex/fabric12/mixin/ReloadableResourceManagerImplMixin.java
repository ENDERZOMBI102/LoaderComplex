package com.enderzombi102.loaderComplex.fabric12.mixin;

import com.enderzombi102.loaderComplex.fabric12.LoaderComplexFabric;
import com.google.common.collect.Maps;
import net.minecraft.class_6057;
import net.minecraft.resource.NamespaceResourceManager;
import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.resource.ResourcePack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ReloadableResourceManagerImpl.class)
public abstract class ReloadableResourceManagerImplMixin {

	@Shadow public abstract void addPack(ResourcePack pack);
	@Shadow @Final private Map<String, NamespaceResourceManager> namespaceManagers;

	@Shadow @Final private class_6057 type;

	@Inject( method = "<init>", at = @At( "TAIL" ) )
	public void  onConstruct(class_6057 type, CallbackInfo ci) {
		for ( ResourcePack pack : LoaderComplexFabric.packs ) {
			this.addPack( pack );
			NamespaceResourceManager namespaceResourceManager = new NamespaceResourceManager(this.type);
			namespaceResourceManager.addPack( pack );
			this.namespaceManagers.put( pack.getName(), namespaceResourceManager );
		}
	}

}
