package com.enderzombi102.loadercomplex.forge12.impl;

import com.enderzombi102.loadercomplex.api.Loader;
import com.enderzombi102.loadercomplex.api.Registry;
import com.enderzombi102.loadercomplex.api.utils.FactoryWorld;
import com.enderzombi102.loadercomplex.api.utils.LoaderType;
import com.enderzombi102.loadercomplex.api.utils.Version;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.ForgeVersion;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ForgeLoader implements Loader {

	private final ForgeRegistry registry;

	public ForgeLoader() {
		this.registry = new ForgeRegistry();
	}

	@Override
	public LoaderType getLoaderType() {
		return LoaderType.ForgeLegacy;
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
	public boolean isDeveloperEnvironment() {
		return (boolean) Launch.blackboard.getOrDefault( "fml.deobfuscatedEnvironment", false );
	}

	@Override
	public FactoryWorld getFactoryWorld() {
		return new ForgeFactoryWorld();
	}

	@Override
	public boolean isAtLeastMinecraft(String version) {
		return true;
	}

	@Override
	public boolean isModLoaded(String id) {
		return net.minecraftforge.fml.common.Loader.isModLoaded(id);
	}

	@Override
	public Version getApiVersion() {
		return new Version(
			"0.1.3",
			LocalDateTime.now().format( DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss") )
		);
	}
}
