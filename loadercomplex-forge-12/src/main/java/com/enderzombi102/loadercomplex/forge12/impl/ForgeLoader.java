package com.enderzombi102.loadercomplex.forge12.impl;

import com.enderzombi102.loadercomplex.common.abstraction.Loader;
import com.enderzombi102.loadercomplex.common.abstraction.Registry;
import com.enderzombi102.loadercomplex.common.abstraction.utils.LoaderType;
import net.minecraftforge.common.ForgeVersion;

public class ForgeLoader implements Loader {

	private final ForgeRegistry registry;

	public ForgeLoader(ForgeRegistry registry) {
		this.registry = registry;
	}

	@Override
	public LoaderType getLoaderType() {
		return LoaderType.Forge12;
	}

	@Override
	public Registry getRegistry() {
		return this.registry;
	}

	@Override
	public String getMinecraftVersion() {
		return "1.12.2";
	}

	@Override
	public String getLoaderVersion() {
		return ForgeVersion.getVersion();
	}

	@Override
	public boolean isModLoaded(String id) {
		return net.minecraftforge.fml.common.Loader.isModLoaded(id);
	}
}
