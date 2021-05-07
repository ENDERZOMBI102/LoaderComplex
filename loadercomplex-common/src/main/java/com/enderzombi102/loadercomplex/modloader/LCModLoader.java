package com.enderzombi102.loadercomplex.modloader;

import com.enderzombi102.loadercomplex.api.ContentMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class LCModLoader {

	private final Path MODS_PATH;
	private final Logger logger = LogManager.getLogger("LC-ModLoader");
	private final ArrayList<Mod> mods = new ArrayList<>();
	private final DynamicClassLoader classLoader = new DynamicClassLoader( new URL[] {}, this.getClass().getClassLoader() );

	public LCModLoader() {
		MODS_PATH = Paths.get( System.getProperty("user.dir"), "mods" );
	}

	@SuppressWarnings("unchecked")
	public void loadMods() {
		final ArrayList<URL> urls = new ArrayList<>();

		this.logger.info("FINDING MODS");
		for ( File file : Objects.requireNonNull( this.MODS_PATH.toFile().listFiles() ) ) {
			if( file.getName().endsWith(".lc.jar") ) {
				try {
					this.classLoader.addURL( file.toURI().toURL() );
					this.mods.add( new Mod( Paths.get( file.getPath() ) ) );
				} catch (IOException e) {
					this.logger.error("Failed to load possible LC mod: " + file.getName() );
				}
			}
		}

		this.logger.info("INSTANTIATING MODS");
		for ( Mod mod : this.mods ) {
			try {
				Class<?> classToLoad = Class.forName( mod.getMainClass(), true, classLoader );
				if (
						Arrays.stream( classToLoad.getInterfaces() )
							.anyMatch( iface -> iface.getSimpleName().equals("ContentMod") )
				) {
					Class<? extends ContentMod> modToLoad = (Class<? extends ContentMod>) classToLoad;
					mod.implementation = modToLoad.newInstance();
				}
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				this.logger.error( "can't load mod file: " + mod.getPath() );
			}
		}

	}

	public ArrayList<Mod> getMods() {
		return this.mods;
	}


	private static class DynamicClassLoader extends URLClassLoader {
		public DynamicClassLoader(URL[] urls, ClassLoader parent) {
			super(urls, parent);
		}

		@Override
		public void addURL(URL url) {
			super.addURL(url);
		}
	}

}
