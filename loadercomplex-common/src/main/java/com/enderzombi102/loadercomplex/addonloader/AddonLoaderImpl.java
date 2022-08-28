package com.enderzombi102.loadercomplex.addonloader;

import com.enderzombi102.loadercomplex.api.Addon;
import com.enderzombi102.loadercomplex.api.addonloader.AddonLoader;
import com.enderzombi102.loadercomplex.api.annotation.Instance;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
@ApiStatus.Internal
public class AddonLoaderImpl implements AddonLoader {
	private final Logger logger = LoggerFactory.getLogger("LoaderComplex | AddonLoader");
	private final List<AddonContainerImpl> addonContainerImpls = new ArrayList<>();
	private final DynamicClassLoader classLoader = new DynamicClassLoader();
	private final Path modsPath;
	private final Path addonsPath;

	public AddonLoaderImpl() {
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
					addonContainerImpls.add( new AddonContainerImpl( Paths.get( file.getPath() ) ) );
				} catch ( IOException e ) {
					logger.error( "Failed to load possible LC addon {}: {}", file, e.getMessage() );
				}
			}
		}
		int addonFromModsFolder = addonContainerImpls.size();
		logger.info( " - Found {} addons", addonFromModsFolder );
		logger.info("SCANNING ADDONS FOLDER");
		//noinspection ResultOfMethodCallIgnored
		addonsPath.toFile().mkdirs();
		for ( File file : Objects.requireNonNull( addonsPath.toFile().listFiles() ) ) {
			if( file.getName().endsWith(".jar") ) {
				logger.info( " - Found possible LoaderComplex addon: {}", file );
				try {
					addonContainerImpls.add( new AddonContainerImpl( Paths.get( file.getPath() ) ) );
					classLoader.addURL( file.toURI().toURL() );  // add to classloader only _after_ we made sure that it's an LC addon
				} catch (IOException e) {
					logger.error( "Failed to load possible LC addon: " + file.getName() );
				}
			}
		}
		logger.info( " - Found {} addons", addonContainerImpls.size() - addonFromModsFolder );

		logger.info("INSTANTIATING {} ADDONS", addonContainerImpls.size() );
		for ( AddonContainerImpl addon : addonContainerImpls ) {
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
			addonContainerImpls.size(),
			addonContainerImpls.stream().filter( AddonContainerImpl::didFailToLoad ).count()
		);
	}

	@Override
	public List<AddonContainerImpl> getAddons() {
		return addonContainerImpls;
	}

	private static class DynamicClassLoader extends URLClassLoader {
		public DynamicClassLoader() {
			super( new URL[] {}, AddonLoaderImpl.class.getClassLoader() );
		}

		@Override
		public void addURL(URL url) {
			super.addURL(url);
		}
	}

}
