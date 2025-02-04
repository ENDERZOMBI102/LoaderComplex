package com.enderzombi102.loadercomplex.fabric171.mixin;

import com.enderzombi102.loadercomplex.fabric171.LoaderComplexFabric;
import net.fabricmc.fabric.api.resource.ModResourcePack;
import net.fabricmc.fabric.impl.resource.loader.ModResourcePackUtil;
import net.minecraft.resource.ResourceType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;


@Mixin( ModResourcePackUtil.class )
public class ModResourcepackUtilMixin {
	@Inject( method = "appendModResourcePacks", at = @At( "TAIL" ) )
	private static void onAppendModResourcepacks( List<ModResourcePack> packs, ResourceType type, @Nullable String subPath, CallbackInfo ci ) {
		packs.addAll( LoaderComplexFabric.packs );
	}
}
