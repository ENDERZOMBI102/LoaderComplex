package com.enderzombi102.loadercomplex.quilt;

import com.enderzombi102.loadercomplex.LoaderComplex;
import com.enderzombi102.loadercomplex.api.Loader;
import com.enderzombi102.loadercomplex.quilt.impl.QuiltLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.fabricmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

import java.util.ArrayList;

public class LoaderComplexQuilt extends LoaderComplex implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("LoaderComplex");
	public static LoaderComplexQuilt INSTANCE;
	public final ArrayList<QuiltResourcePack> packs = new ArrayList<>();

	public LoaderComplexQuilt() {
		super();
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