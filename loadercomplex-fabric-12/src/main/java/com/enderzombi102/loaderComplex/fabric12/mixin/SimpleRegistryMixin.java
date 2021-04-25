package com.enderzombi102.loaderComplex.fabric12.mixin;

import net.minecraft.util.Int2ObjectBiMap;
import net.minecraft.util.registry.SimpleRegistry;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(SimpleRegistry.class)
public class SimpleRegistryMixin<K, V> {

	@Shadow
	@Final
	@Mutable
	protected Int2ObjectBiMap<V> indexedEntries;

	@Inject( method = "<init>", at = @At("RETURN") )
	public void onConstruct(CallbackInfo ci) {
		 this.indexedEntries = new Int2ObjectBiMap<>(2048);
	}

}
