package com.enderzombi102.loadercomplex.quilt;

import com.enderzombi102.loadercomplex.impl.KotlinLoader;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class QuiltBootstrap implements ModInitializer {
    private final LoaderComplexQuilt impl = (LoaderComplexQuilt) KotlinLoader.bootstrap( "com.enderzombi102.loadercomplex.quilt.LoaderComplexQuilt" );

    @Override
    public void onInitialize( ModContainer mod ) {
        impl.onInitialize( mod );
    }
}
