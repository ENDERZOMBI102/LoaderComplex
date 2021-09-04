package com.enderzombi102.loadercomplex.modloader;

import com.enderzombi102.loadercomplex.api.Addon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Objects;

public class LCModLoader {

	private final Path MODS_PATH;
	private final Logger logger = LogManager.getLogger("LC-ModLoader");
	private final ArrayList<Mod> addons = new ArrayList<>();
	private final DynamicClassLoader classLoader = new DynamicClassLoader();

	public LCModLoader() {
		MODS_PATH = Paths.get( System.getProperty("user.dir"), "mods" );
	}

	@SuppressWarnings("unchecked")
	public void loadMods() {
		logger.info("FINDING ADDONS");
		for ( File file : Objects.requireNonNull( MODS_PATH.toFile().listFiles() ) ) {
			if( file.getName().endsWith(".lc.jar") ) {
				try {
					classLoader.addURL( file.toURI().toURL() );
					addons.add( new Mod( Paths.get( file.getPath() ) ) );
				} catch (IOException e) {
					logger.error("Failed to load possible LC addon: " + file.getName() );
				}
			}
		}

		logger.info("INSTANTIATING ADDONS");
		for ( Mod mod : addons) {
			try {
				Class<?> classToLoad = Class.forName( mod.getMainClass(), true, classLoader );
				if ( Addon.class.isAssignableFrom(classToLoad) ) {
					Class<? extends Addon> modToLoad = (Class<? extends Addon>) classToLoad;
					mod.implementation = modToLoad.getDeclaredConstructor().newInstance();
				}
			} catch ( ReflectiveOperationException e ) {
				logger.error( "can't load addon file: " + mod.getPath(), e );
			}
		}

	}

	public ArrayList<Mod> getAddons() {
		return addons;
	}


	private static class DynamicClassLoader extends URLClassLoader {
		public DynamicClassLoader() {
			super( new URL[] {}, LCModLoader.class.getClassLoader() );
		}

		@Override
		public void addURL(URL url) {
			super.addURL(url);
		}
	}

}
