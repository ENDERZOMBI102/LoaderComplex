package com.enderzombi102.loadercomplex.forge12.impl.utils;

import com.enderzombi102.loadercomplex.api.utils.Platform;
import com.enderzombi102.loadercomplex.api.utils.Version;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.ForgeVersion;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ForgeLegacyPlatform implements Platform {
	@Override
	public @NotNull String name() {
		return "Forge";
	}

	@Override
	public @NotNull String version() {
		return ForgeVersion.getVersion();
	}

	@Override
	public @NotNull String id() {
		return "forge-legacy";
	}

	@Override
	public @NotNull String minecraftVersion() {
		return "1.12.2";
	}

	@Override
	public @NotNull Version getApiVersion() {
		return new Version( "0.1.3", LocalDateTime.now().format( DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss") ) );
	}

	@Override
	public boolean isDeveloperEnvironment() {
		return (boolean) Launch.blackboard.getOrDefault( "fml.deobfuscatedEnvironment", false );
	}
}
