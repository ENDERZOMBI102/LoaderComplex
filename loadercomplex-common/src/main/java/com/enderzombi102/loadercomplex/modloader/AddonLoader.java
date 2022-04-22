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
import java.util.List;
import java.util.Objects;

/**
 * Internal class used to load jars and instantiate Addon implementations
 */
public class AddonLoader {
	private final Logger logger = LogManager.getLogger("LoaderComplex | AddonLoader");
	private final List<AddonContainer> addonContainers = new ArrayList<>();
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
				logger.info( " - Found possible LoaderComplex addon: {}", file );
				try {
					classLoader.addURL( file.toURI().toURL() );
					addonContainers.add( new AddonContainer( Paths.get( file.getPath() ) ) );
				} catch ( IOException e ) {
					logger.error( "Failed to load possible LC addon {}: {}", file, e.getMessage() );
				}
			}
		}
		int addonFromModsFolder = addonContainers.size();
		logger.info( " - Found {} addons", addonFromModsFolder );
		logger.info("SCANNING ADDONS FOLDER");
		//noinspection ResultOfMethodCallIgnored
		addonsPath.toFile().mkdirs();
		for ( File file : Objects.requireNonNull( addonsPath.toFile().listFiles() ) ) {
			if( file.getName().endsWith(".jar") ) {
				logger.info( " - Found possible LoaderComplex addon: {}", file );
				try {
					addonContainers.add( new AddonContainer( Paths.get( file.getPath() ) ) );
					classLoader.addURL( file.toURI().toURL() );  // add to classloader only _after_ we made sure that it's an LC addon
				} catch (IOException e) {
					logger.error( "Failed to load possible LC addon: " + file.getName() );
				}
			}
		}
		logger.info( " - Found {} addons", addonContainers.size() - addonFromModsFolder );

		logger.info("INSTANTIATING {} ADDONS", addonContainers.size() );
		for ( AddonContainer addon : addonContainers) {
			try {
				Class<?> classToLoad = Class.forName( addon.getMainClass(), true, classLoader );
				if ( Addon.class.isAssignableFrom(classToLoad) ) {
					Class<? extends Addon> addonToLoad = (Class<? extends Addon>) classToLoad;
					// set the first Instance-annotated static field to the instance
					for ( Field field : classToLoad.getFields() ) {
						if ( Modifier.isStatic( field.getModifiers() ) && field.isAnnotationPresent( Instance.class ) ) {
							logger.info(" - Addon {} is using the Instance annotation! Using their provided instance.", addon.getId() );
							field.setAccessible(true);
							addon.implementation = (Addon) field.get( null );
							break;
						}
					}
					if ( addon.implementation == null )
						addon.implementation = addonToLoad.getDeclaredConstructor().newInstance();
				} else {
					logger.error( "Addon " + addon.getId() + " has a main class that doesn't implement the `Addon` interface!" );
				}
			} catch ( ReflectiveOperationException e ) {
				logger.error( "can't load addon file: " + addon.getPath(), e );
			}
		}
		logger.info(
			"FINISHED LOADING {} ADDONS WITH {} FAILS",
			addonContainers.size(),
			addonContainers.stream().filter( AddonContainer::didFailToLoad ).count()
		);
	}

	public List<AddonContainer> getAddons() {
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
