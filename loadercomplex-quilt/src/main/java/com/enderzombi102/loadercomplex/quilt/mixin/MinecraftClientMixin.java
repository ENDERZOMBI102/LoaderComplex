package com.enderzombi102.loadercomplex.quilt.mixin;

import com.enderzombi102.loadercomplex.quilt.LoaderComplexQuilt;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.pack.ResourcePack;
import net.minecraft.util.Unit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
	@Inject(method = "reloadResources(Z)Ljava/util/concurrent/CompletableFuture;", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ReloadableResourceManager;reload(Ljava/util/concurrent/Executor;Ljava/util/concurrent/Executor;Ljava/util/concurrent/CompletableFuture;Ljava/util/List;)Lnet/minecraft/resource/ResourceReload;", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
	public void onReloadResources(boolean force, CallbackInfoReturnable<CompletableFuture<Void>> cir, CompletableFuture<Unit> completableFuture, List<ResourcePack> list) {
		list.addAll(LoaderComplexQuilt.INSTANCE.packs);
	}
}
