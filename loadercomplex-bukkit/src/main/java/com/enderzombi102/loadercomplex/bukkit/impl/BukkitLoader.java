package com.enderzombi102.loadercomplex.bukkit.impl;

import com.enderzombi102.loadercomplex.api.Loader;
import com.enderzombi102.loadercomplex.api.Registry;
import com.enderzombi102.loadercomplex.api.utils.LoaderType;
import org.bukkit.Bukkit;

public class BukkitLoader implements Loader {

	private final BukkitRegistry registry = new BukkitRegistry();


	@Override
	public LoaderType getLoaderType() {
		return LoaderType.Bukkit;
	}

	@Override
	public Registry getRegistry() {
		return this.registry;
	}

	@Override
	public String getMinecraftVersion() {
		return Bukkit.getVersion();
	}

	@Override
	public String getLoaderVersion() {
		return Bukkit.getBukkitVersion();
	}

	@Override
	public boolean isModLoaded(String id) {
		return Bukkit.getPluginManager().getPlugin(id) != null;
	}
}
