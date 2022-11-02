package com.enderzombi102.loadercomplex.quilt.impl;

import com.enderzombi102.loadercomplex.api.Loader;
import com.enderzombi102.loadercomplex.api.utils.Registry;
import com.enderzombi102.loadercomplex.api.utils.FactoryWorld;
import com.enderzombi102.loadercomplex.api.utils.LoaderType;
import com.enderzombi102.loadercomplex.api.utils.Version;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class QuiltLoader implements Loader {
	private static final String QUILT_VERSION;
	public static final org.quiltmc.loader.api.Version MINECRAFT_VERSION = org.quiltmc.loader.api.Version.of("1.18.2");
	static {
		//noinspection OptionalGetWithoutIsPresent
		QUILT_VERSION = org.quiltmc.loader.api.QuiltLoader.getModContainer("quilt_loader")
				.get()
				.metadata()
				.version()
				.raw();
	}
	private final Registry registry = new QuiltRegistry();

	@Override
	public @NotNull LoaderType getPlatform() {
		return LoaderType.Quilt;
	}

	@Override
	public @NotNull Registry getRegistry() {
		return registry;
	}

	@Override
	public String getMinecraftVersion() {
		return MINECRAFT_VERSION.raw();
	}

	@Override
	public String getLoaderVersion() {
		return QUILT_VERSION;
	}

	@Override
	public boolean isModLoaded(String id) {
		return org.quiltmc.loader.api.QuiltLoader.isModLoaded( id );
	}

	@Override
	public boolean isDeveloperEnvironment() {
		return org.quiltmc.loader.api.QuiltLoader.isDevelopmentEnvironment();
	}

	@Override
	public @NotNull FactoryWorld getFactoryWorld() {
		return new QuiltFactoryWorld();
	}

	@Override
	public boolean isAtLeastMinecraft(String version) {
		return org.quiltmc.loader.api.Version.of(version).semantic().compareTo( MINECRAFT_VERSION.semantic() ) >= 0;
	}

	@Override
	public @NotNull Version getApiVersion() {
		return new Version( "0.1.3", LocalDateTime.now().format( DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss") ) );
	}
}
