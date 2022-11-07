package com.enderzombi102.loadercomplex.impl.addon;

import com.enderzombi102.loadercomplex.api.addon.Addon;
import com.enderzombi102.loadercomplex.api.addon.AddonContainer;
import com.enderzombi102.loadercomplex.api.addon.AddonLoader;
import com.enderzombi102.loadercomplex.api.annotation.Instance;
import org.jetbrains.annotations.ApiStatus.Internal;
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
import java.util.stream.Collectors;

import static com.enderzombi102.enderlib.reflection.Getters.get;
import static com.enderzombi102.enderlib.reflection.Setters.set;

/**
 * Internal class used to load jars and instantiate Addon implementations
 */
@Internal
public class AddonLoaderImpl implements AddonLoader {
	private final Logger logger = LoggerFactory.getLogger( "LoaderComplex | AddonLoader" );
	private final List<AddonContainer> addonContainerImpls = new ArrayList<>();
	private final DynamicClassLoader classLoader = new DynamicClassLoader();
	private final Path addonsPath;
	private final Path modsPath;

	public AddonLoaderImpl() {
		modsPath = Paths.get( System.getProperty( "user.dir" ), "mods" );
		addonsPath = Paths.get( System.getProperty( "user.dir" ), "addons" );
	}

	@SuppressWarnings("unchecked")
	public void loadAddons() {
		logger.info( "Searching for addons" );
		logger.info( "Scanning mods folder" );
		for ( File file : Objects.requireNonNull( modsPath.toFile().listFiles() ) ) {
			if ( file.getName().endsWith( ".lc.jar" ) ) {
				logger.info( " - Found possible LoaderComplex addon: {}", file );
				try {
					classLoader.addURL( file.toURI().toURL() );
					addonContainerImpls.add( new AddonContainerImpl( Paths.get( file.getPath() ) ) );
				} catch ( IOException | IllegalStateException e ) {
					logger.error( "Failed to load possible LC addon {}: {}", file, e.getMessage() );
				}
			}
		}
		int addonFromModsFolder = addonContainerImpls.size();
		logger.info( " - Found {} addons", addonFromModsFolder );
		logger.info( "Scanning addons folder" );
		//noinspection ResultOfMethodCallIgnored
		addonsPath.toFile().mkdirs();
		for ( File file : Objects.requireNonNull( addonsPath.toFile().listFiles() ) ) {
			if ( file.getName().endsWith( ".jar" ) ) {
				logger.info( " - Found possible LoaderComplex addon: {}", file );
				try {
					addonContainerImpls.add( new AddonContainerImpl( Paths.get( file.getPath() ) ) );
					classLoader.addURL( file.toURI().toURL() );  // add to classloader only _after_ we made sure that it's an LC addon
				} catch ( IOException | IllegalStateException e ) {
					logger.error( "Failed to load possible LC addon {}: {}", file, e.getMessage() );
				}
			}
		}
		logger.info( " - Found {} addons", addonContainerImpls.size() - addonFromModsFolder );

		logger.info( "Instantiating {} addons", addonContainerImpls.size() );
		for ( AddonContainer container : addonContainerImpls ) {
			try {
				Class<?> classToLoad = Class.forName( container.getMainClass(), true, classLoader );
				if ( Addon.class.isAssignableFrom( classToLoad ) ) {
					Class<? extends Addon> addonToLoad = (Class<? extends Addon>) classToLoad;
					// set the first Instance-annotated static field to the instance
					for ( Field field : classToLoad.getFields() ) {
						if ( Modifier.isStatic( field.getModifiers() ) && field.isAnnotationPresent( Instance.class ) ) {
							logger.info( " - Addon {} is using the Instance annotation! Using their provided instance.", container.getId() );
							field.setAccessible( true );
							set( container, "implementation", (Addon) field.get( null ), Addon.class );
							break;
						}
					}
					if ( get( container, "implementation", Addon.class ) == null )
						set( container, "implementation", addonToLoad.getDeclaredConstructor().newInstance(), Addon.class );
				} else {
					logger.error( "Addon " + container.getId() + " has a main class that doesn't implement the `Addon` interface!" );
				}
			} catch ( ReflectiveOperationException e ) {
				logger.error( "can't load addon file: " + container.getPath(), e );
			}
		}
		logger.info(
			"Finished loading {} addons with {} fails",
			addonContainerImpls.size(),
			addonContainerImpls.stream().filter( AddonContainer::didFailToLoad ).count()
		);
	}

	@Override
	public List<AddonContainer> getAddons() {
		return this.addonContainerImpls.stream().map( AddonContainer.class::cast ).collect( Collectors.toList() );
	}

	private static class DynamicClassLoader extends URLClassLoader {
		public DynamicClassLoader() {
			super( new URL[] {}, AddonLoaderImpl.class.getClassLoader() );
		}

		@Override
		public void addURL( URL url ) {
			super.addURL( url );
		}
	}

}
