package com.enderzombi102.loadercomplex.fabric122.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.util.CrudeIncrementalIntIdentityHashMap;
import net.minecraft.util.registry.IdRegistry;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

import static com.enderzombi102.loadercomplex.fabric122.LoaderComplexFabric.LOGGER;


@Mixin( IdRegistry.class )
public class IdRegistryMixin<K, V> {
	@Mutable
	@Shadow
	@Final
	protected CrudeIncrementalIntIdentityHashMap<V> ids;

	@WrapOperation( method = "<init>", at = @At( value = "INVOKE", target = "Lnet/minecraft/util/CrudeIncrementalIntIdentityHashMap;<init>(I)V" ) )
	public CrudeIncrementalIntIdentityHashMap<V> onSet( IdRegistry<K, V> instance, int size, Operation<CrudeIncrementalIntIdentityHashMap<V>> original ) {
		if ( size < 2048 ) {
			LOGGER.debug( "Extending registry from {} to 2048 ids", size );
			size = 2048;
		} else  {
			LOGGER.debug( "Not extending registry range, {} >= 2048", size );
		}
		return original.call( instance, size );
	}
}
