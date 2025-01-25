package com.enderzombi102.loadercomplex.quilt.mixin;

import com.enderzombi102.loadercomplex.quilt.LoaderComplexQuilt;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.pack.ResourcePack;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.resource.loader.impl.ResourceLoaderImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@SuppressWarnings( "UnstableApiUsage" )
@Mixin( ResourceLoaderImpl.class )
public class ModResourcepackUtilMixin {
	@Inject( method = "appendModResourcePacks", at = @At( "TAIL" ), remap = false )
	private static void onAppendModResourcepacks( List<ResourcePack> packs, ResourceType type, @Nullable String subPath, CallbackInfo ci ) {
		packs.addAll( LoaderComplexQuilt.INSTANCE.packs );
	}
}
