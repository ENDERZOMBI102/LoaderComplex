package com.enderzombi102.loadercomplex.bukkit;

import com.enderzombi102.loadercomplex.LoaderComplex;
import com.enderzombi102.loadercomplex.bukkit.impl.BukkitLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.plugin.java.JavaPlugin;

public final class LoaderComplexBukkit extends JavaPlugin {

	public static final Logger LOGGER = LogManager.getLogger("[LoaderComplex-Bukkit]");

	private static class LoaderComplexBukkitImpl extends LoaderComplex {

		private boolean isLoaded = false;

		public LoaderComplexBukkitImpl() {
			super();
			this.loader = new BukkitLoader();
		}

		@Override
		public void initMods() {
			super.initMods();
			this.isLoaded = true;
		}

	}

	private final LoaderComplexBukkitImpl impl = new LoaderComplexBukkitImpl();

	@Override
	public void onLoad() {
		if ( this.impl.isLoaded )
			throw new IllegalPluginAccessException("LoaderComplexBukkit can't be re-loaded!");
		this.impl.initMods();
	}

	@Override
	public void onEnable() {

	}

	@Override
	public void onDisable() {
		LOGGER.warn("LoaderComplexBukkit can't be unloaded, just terminated at server shutdown");
	}
}
