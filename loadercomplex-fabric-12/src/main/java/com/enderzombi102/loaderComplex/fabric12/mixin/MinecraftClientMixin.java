package com.enderzombi102.loaderComplex.fabric12.mixin;

import com.enderzombi102.loaderComplex.fabric12.LoaderComplexFabric;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourcePack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
	// this is actually reloadResources
	@Inject(method = "stitchTextures", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ReloadableResourceManager;reload(Ljava/util/List;)V", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
	public void onReloadResources(CallbackInfo ci, List<ResourcePack> list) {
		list.addAll(LoaderComplexFabric.packs);
	}
}
