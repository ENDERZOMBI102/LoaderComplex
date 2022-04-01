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

public class LCAddonLoader {

	private final Path MODS_PATH;
	private final Path ADDONS_PATH;
	private final Logger logger = LogManager.getLogger("LC-AddonLoader");
	private final ArrayList<AddonContainer> addonContainers = new ArrayList<>();
	private final DynamicClassLoader classLoader = new DynamicClassLoader();

	public LCAddonLoader() {
		MODS_PATH = Paths.get( System.getProperty("user.dir"), "mods" );
		ADDONS_PATH = Paths.get( System.getProperty("user.dir"), "addons" );
	}

	@SuppressWarnings("unchecked")
	public void loadAddons() {
		logger.info("SEARCHING FOR ADDONS");
		logger.info("SCANNING MODS FOLDER");
		for ( File file : Objects.requireNonNull( MODS_PATH.toFile().listFiles() ) ) {
			if( file.getName().endsWith(".lc.jar") ) {
				try {
					classLoader.addURL( file.toURI().toURL() );
					addonContainers.add( new AddonContainer( Paths.get( file.getPath() ) ) );
				} catch (IOException e) {
					logger.error("Failed to load possible LC addon: " + file.getName() );
				}
			}
		}
		logger.info("SCANNING ADDONS FOLDER");
		ADDONS_PATH.toFile().mkdirs();
		for ( File file : Objects.requireNonNull( ADDONS_PATH.toFile().listFiles() ) ) {
			if( file.getName().endsWith(".jar") ) {
				try {
					classLoader.addURL( file.toURI().toURL() );
					addonContainers.add( new AddonContainer( Paths.get( file.getPath() ) ) );
				} catch (IOException e) {
					logger.error("Failed to load possible LC addon: " + file.getName() );
				}
			}
		}

		logger.info("INSTANTIATING ADDONS");
		for ( AddonContainer mod : addonContainers) {
			try {
				Class<?> classToLoad = Class.forName( mod.getMainClass(), true, classLoader );
				if ( Addon.class.isAssignableFrom(classToLoad) ) {
					Class<? extends Addon> addonToLoad = (Class<? extends Addon>) classToLoad;
					mod.implementation = addonToLoad.getDeclaredConstructor().newInstance();
				}
			} catch ( ReflectiveOperationException e ) {
				logger.error( "can't load addon file: " + mod.getPath(), e );
			}
		}

	}

	public ArrayList<AddonContainer> getAddons() {
		return addonContainers;
	}


	private static class DynamicClassLoader extends URLClassLoader {
		public DynamicClassLoader() {
			super( new URL[] {}, LCAddonLoader.class.getClassLoader() );
		}

		@Override
		public void addURL(URL url) {
			super.addURL(url);
		}
	}

}
