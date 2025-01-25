package com.enderzombi102.loadercomplex.forge18;

import com.enderzombi102.loadercomplex.impl.KotlinLoader;
import net.minecraftforge.fml.common.Mod;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("loadercomplex")
public class ForgeBootstrap {
    private final LoaderComplexForge impl = (LoaderComplexForge) KotlinLoader.bootstrap( "com.enderzombi102.loadercomplex.forge18.LoaderComplexForge" );
}
