package com.enderzombi102.loaderComplex.fabric12.mixin;

import com.enderzombi102.loaderComplex.fabric12.FabricResourcePack;
import com.enderzombi102.loaderComplex.fabric12.LoaderComplexFabric;
import net.minecraft.class_6057;
import net.minecraft.resource.NamespaceResourceManager;
import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourcePack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(ReloadableResourceManagerImpl.class)
public abstract class ReloadableResourceManagerImplMixin {

	@Shadow public abstract void addPack(ResourcePack pack);
	@Shadow @Final private Map<String, NamespaceResourceManager> namespaceManagers;

	@Shadow @Final private class_6057 type;

	private final HashMap<String, NamespaceResourceManager> lcNamespaceManagers = new HashMap<>();

	@Inject( method = "<init>", at = @At( "TAIL" ) )
	public void  onConstruct(class_6057 type, CallbackInfo ci) {
		for ( FabricResourcePack pack : LoaderComplexFabric.packs ) {
			this.addPack( pack );
			NamespaceResourceManager manager = new NamespaceResourceManager(this.type);
			manager.addPack( pack );
			this.namespaceManagers.put( pack.getModID(), manager );
			this.lcNamespaceManagers.put( pack.getModID(), manager );
		}
	}

	@Inject( method = "getAllResources", at = @At( "INVOKE" ), cancellable = true)
	public void onGetAllResources(Identifier id, CallbackInfoReturnable< List< Resource > > cir) throws IOException {
		if ( this.lcNamespaceManagers.containsKey( id.getNamespace() ) ) {
			cir.setReturnValue(
					this.lcNamespaceManagers
							.get( id.getNamespace() )
							.getAllResources(id)
			);
		}
	}

	@Inject( method = "getResource", at = @At( "INVOKE" ), cancellable = true)
	public void onGetResource(Identifier id, CallbackInfoReturnable< Resource > cir) throws IOException {
		if ( this.lcNamespaceManagers.containsKey( id.getNamespace() ) ) {
			cir.setReturnValue(
					this.lcNamespaceManagers
							.get( id.getNamespace() )
							.getResource(id)
			);
		}
	}

}
