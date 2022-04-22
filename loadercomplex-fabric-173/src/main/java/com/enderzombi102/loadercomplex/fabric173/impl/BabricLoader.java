package com.enderzombi102.loadercomplex.fabric173.impl;

import com.enderzombi102.loadercomplex.Utils;
import com.enderzombi102.loadercomplex.api.Loader;
import com.enderzombi102.loadercomplex.api.Registry;
import com.enderzombi102.loadercomplex.api.utils.FactoryWorld;
import com.enderzombi102.loadercomplex.api.utils.LoaderType;
import com.enderzombi102.loadercomplex.api.utils.Version;
import net.fabricmc.loader.api.FabricLoader;

public class BabricLoader implements Loader {
	@Override
	public LoaderType getLoaderType() {
		return LoaderType.Babric;
	}

	@Override
	public Registry getRegistry() {
		return new BabricRegistry();
	}

	@Override
	public String getMinecraftVersion() {
		return "1.0.0-beta.7.3";
	}

	@Override
	public String getLoaderVersion() {
		//noinspection OptionalGetWithoutIsPresent
		return FabricLoader.getInstance().getModContainer("fabricloader")
				.get()
				.getMetadata()
				.getVersion()
				.getFriendlyString();
	}

	@Override
	public boolean isModLoaded(String id) {
		return FabricLoader.getInstance().isModLoaded( id );
	}

	@Override
	public boolean isDeveloperEnvironment() {
		return FabricLoader.getInstance().isDevelopmentEnvironment();
	}

	@Override
	public FactoryWorld getFactoryWorld() {
		return new BabricFactoryWorld();
	}

	@Override
	public boolean isAtLeastMinecraft(String version) {
		return true;
	}

	@Override
	public Version getApiVersion() {
		return new Version( "0.1.3", Utils.getApiVersion( isDeveloperEnvironment() ).getBuildDate() );
	}
}
