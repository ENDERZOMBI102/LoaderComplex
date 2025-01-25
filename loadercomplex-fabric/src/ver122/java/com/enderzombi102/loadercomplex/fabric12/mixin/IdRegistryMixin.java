package com.enderzombi102.loadercomplex.fabric12.mixin;

import net.minecraft.util.CrudeIncrementalIntIdentityHashMap;
import net.minecraft.util.registry.IdRegistry;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin( IdRegistry.class )
public class IdRegistryMixin<K, V> {
	@Mutable
	@Shadow
	@Final
	protected CrudeIncrementalIntIdentityHashMap<V> ids;

	@Inject( method = "<init>", at = @At( "RETURN" ) )
	public void onConstruct( CallbackInfo ci ) {
		this.ids = new CrudeIncrementalIntIdentityHashMap<>( 2048 );
	}
}
