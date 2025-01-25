package com.enderzombi102.loadercomplex.fabric12.mixin;

import com.enderzombi102.loadercomplex.fabric12.LoaderComplexFabric;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resource.pack.ResourcePack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin( Minecraft.class )
public abstract class MinecraftMixin {
	// this is actually reloadResources
	@Inject( method = "reloadResources", at = @At( value = "INVOKE", target = "Lnet/minecraft/client/resource/manager/ReloadableResourceManager;reload(Ljava/util/List;)V", ordinal = 0 ) )
	public void onReloadResources( CallbackInfo ci, @Local List<ResourcePack> list ) {
		list.addAll( LoaderComplexFabric.packs );
	}
}
