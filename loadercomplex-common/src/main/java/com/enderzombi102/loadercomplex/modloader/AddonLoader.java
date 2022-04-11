package com.enderzombi102.loadercomplex.modloader;

import com.enderzombi102.loadercomplex.api.Addon;
import com.enderzombi102.loadercomplex.api.annotation.Instance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Internal class used to load jars and instantiate Addon implementations
 */
public class AddonLoader {
	private final Logger logger = LogManager.getLogger("LoaderComplex | AddonLoader");
	private final ArrayList<AddonContainer> addonContainers = new ArrayList<>();
	private final DynamicClassLoader classLoader = new DynamicClassLoader();
	private final Path modsPath;
	private final Path addonsPath;

	public AddonLoader() {
		modsPath = Paths.get( System.getProperty("user.dir"), "mods" );
		addonsPath = Paths.get( System.getProperty("user.dir"), "addons" );
	}

	@SuppressWarnings("unchecked")
	public void loadAddons() {
		logger.info("SEARCHING FOR ADDONS");
		logger.info("SCANNING MODS FOLDER");
		for ( File file : Objects.requireNonNull( modsPath.toFile().listFiles() ) ) {
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
		//noinspection ResultOfMethodCallIgnored
		addonsPath.toFile().mkdirs();
		for ( File file : Objects.requireNonNull( addonsPath.toFile().listFiles() ) ) {
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
		for ( AddonContainer addon : addonContainers) {
			try {
				Class<?> classToLoad = Class.forName( addon.getMainClass(), true, classLoader );
				if ( Addon.class.isAssignableFrom(classToLoad) ) {
					Class<? extends Addon> addonToLoad = (Class<? extends Addon>) classToLoad;
					// set the first Instance-annotated static field to the instance
					for ( Field field : classToLoad.getFields() ) {
						if ( Modifier.isStatic( field.getModifiers() ) && field.isAnnotationPresent( Instance.class ) ) {
							logger.info("Addon {} is using the Instance annotation! Using their provided instance.", addon.getID() );
							field.setAccessible(true);
							addon.implementation = (Addon) field.get( null );
							break;
						}
					}
					if ( addon.implementation == null )
						addon.implementation = addonToLoad.getDeclaredConstructor().newInstance();
				}
			} catch ( ReflectiveOperationException e ) {
				logger.error( "can't load addon file: " + addon.getPath(), e );
			}
		}

	}

	public ArrayList<AddonContainer> getAddons() {
		return addonContainers;
	}

	private static class DynamicClassLoader extends URLClassLoader {
		public DynamicClassLoader() {
			super( new URL[] {}, AddonLoader.class.getClassLoader() );
		}

		@Override
		public void addURL(URL url) {
			super.addURL(url);
		}
	}

}
