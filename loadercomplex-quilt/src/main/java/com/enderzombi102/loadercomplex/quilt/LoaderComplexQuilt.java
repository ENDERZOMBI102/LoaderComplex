package com.enderzombi102.loadercomplex.quilt;

import com.enderzombi102.loadercomplex.api.Loader;
import com.enderzombi102.loadercomplex.impl.LoaderComplex;
import com.enderzombi102.loadercomplex.quilt.impl.QuiltLoader;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class LoaderComplexQuilt extends LoaderComplex implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("LoaderComplex | Quilt");
	public static LoaderComplexQuilt INSTANCE;
	public final List<QuiltResourcePack> packs = new ArrayList<>();

	public LoaderComplexQuilt() {
		loader = new QuiltLoader();
		resourceHelper = mod -> packs.add( new QuiltResourcePack(mod) );
		INSTANCE = this;
	}

	@Override
	public void onInitialize(ModContainer mod) {
		initAddons();
	}

	public Loader getLoader() {
		return loader;
	}
}
