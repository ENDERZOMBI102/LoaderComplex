package com.enderzombi102.loadercomplex.forge18.impl;

import com.enderzombi102.eventsystem.EventSystem;
import com.enderzombi102.loadercomplex.api.Loader;
import com.enderzombi102.loadercomplex.api.LoaderComplex;
import com.enderzombi102.loadercomplex.api.platform.*;
import com.enderzombi102.loadercomplex.forge18.LoaderComplexForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ForgeLoader implements Loader {
	private static final String FORGE_VERSION = FMLLoader.versionInfo().forgeVersion();
	private static final String MINECRAFT_VERSION = FMLLoader.versionInfo().mcVersion();

	private final Registry registry = new ForgeRegistry();

	@Override
	public @NotNull Platform getPlatform() {
		return LoaderType.Forge;
	}

	@Override
	public @NotNull Registry getRegistry() {
		return registry;
	}

	@Override
	public boolean isModLoaded(String id) {
		return ModList.get().isLoaded( id );
	}

	@Override
	public @NotNull FactoryWorld getFactoryWorld() {
		return new ForgeFactoryWorld();
	}

	@Override
	public boolean isAtLeastMinecraft(String version) {
		LoaderComplexForge.LOGGER.warn( "Someone tried to check for `Loader.isAtLeastMinecraft(\"{}\")`, but its not implemented in Forge!", version );
		return true;
	}

	@Override
	public boolean isDedicatedServer() {
		return false;
	}

	@Override
	public @NotNull LoaderComplex getLoaderComplex() {
		return LoaderComplexForge.instance;
	}

	@NotNull
	@Override
	public EventSystem getEventSystem() {
		return null;
	}

	@Override
	public @NotNull I18nSystem getI18nSystem() {
		return null;
	}

	@Override
	public @NotNull ResourceLoader getResourceLoader() {
		return null;
	}

	@Override
	public @NotNull Path getConfigDir() {
		return null;
	}
}
