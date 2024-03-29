package com.enderzombi102.loadercomplex.forge18.impl;

import com.enderzombi102.loadercomplex.api.Loader;
import com.enderzombi102.loadercomplex.api.Registry;
import com.enderzombi102.loadercomplex.api.utils.FactoryWorld;
import com.enderzombi102.loadercomplex.api.utils.LoaderType;
import com.enderzombi102.loadercomplex.api.utils.Version;
import com.enderzombi102.loadercomplex.forge18.LoaderComplexForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ForgeLoader implements Loader {
	private static final String FORGE_VERSION = FMLLoader.versionInfo().forgeVersion();
	private static final String MINECRAFT_VERSION = FMLLoader.versionInfo().mcVersion();

	private final Registry registry = new ForgeRegistry();

	@Override
	public LoaderType getLoaderType() {
		return LoaderType.Forge;
	}

	@Override
	public Registry getRegistry() {
		return registry;
	}

	@Override
	public String getMinecraftVersion() {
		return MINECRAFT_VERSION;
	}

	@Override
	public String getLoaderVersion() {
		return FORGE_VERSION;
	}

	@Override
	public boolean isModLoaded(String id) {
		return ModList.get().isLoaded( id );
	}

	@Override
	public boolean isDeveloperEnvironment() {
		return ! FMLLoader.isProduction();
	}

	@Override
	public FactoryWorld getFactoryWorld() {
		return new ForgeFactoryWorld();
	}

	@Override
	public boolean isAtLeastMinecraft(String version) {
		LoaderComplexForge.LOGGER.warn( "Someone tried to check for `Loader.isAtLeastMinecraft(\"{}\")`, but its not implemented in Forge!", version );
		return true;
	}

	@Override
	public Version getApiVersion() {
		return new Version( "0.1.3", LocalDateTime.now().format( DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss") ) );
	}
}
