package com.enderzombi102.loaderComplex.fabric12.mixin;

import net.minecraft.class_2929;
import net.minecraft.util.registry.SimpleRegistry;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(SimpleRegistry.class)
public class SimpleRegistryMixin<K, V> {

	@Mutable
	@Shadow
	@Final
	protected class_2929<V> field_13718;

	@Inject( method = "<init>", at = @At("RETURN") )
	public void onConstruct(CallbackInfo ci) {
		 this.field_13718 = new class_2929<>(2048);
	}

}
