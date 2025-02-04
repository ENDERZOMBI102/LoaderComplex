package com.enderzombi102.loadercomplex.impl.addon;

import com.enderzombi102.loadercomplex.api.addon.Addon;
import com.enderzombi102.loadercomplex.api.addon.AddonContainer;
import com.enderzombi102.loadercomplex.api.addon.AddonLoader;
import com.enderzombi102.loadercomplex.api.annotation.Instance;
import com.enderzombi102.loadercomplex.impl.addon.finder.AddonFinder;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.reflect.Modifier.isStatic;

/**
 * Internal class used to load jars and instantiate Addon implementations
 */
@Internal
public class AddonLoaderImpl implements AddonLoader {
	private final Logger logger = LoggerFactory.getLogger( "LoaderComplex|AddonLoader" );
	private final List<AddonContainerImpl> containers = new ArrayList<>();
	private final DynamicClassLoader classLoader = new DynamicClassLoader();

	@SuppressWarnings( "unchecked" )
	public void loadAddons( @NotNull List<AddonFinder> finders ) {
		this.logger.info( "Searching for addons" );
		for ( AddonFinder finder : finders ) {
			finder.findAddons( this.logger, path -> {
				try {
					this.logger.info( " - Found possible LoaderComplex addon: {}", path );
					this.containers.add( new AddonContainerImpl( path ) );
					this.classLoader.addURL( path.toUri().toURL() );
					return true;
				} catch ( IOException | IllegalStateException e ) {
					this.logger.error( "Failed to load possible LC addon {}: {}", path, e.getMessage() );
					return false;
				}
			} );
		}
		this.logger.info( "Instantiating {} addons", this.containers.size() );
		for ( AddonContainerImpl container : this.containers ) {
			try {
				Class<?> clazz = Class.forName( container.getMainClass(), true, this.classLoader );
				if ( Addon.class.isAssignableFrom( clazz ) )
					construct( container, (Class<? extends Addon>) clazz );
				else
					logger.error( "`{}`'s main class does not implement the `Addon` interface!", container.getId() );
			} catch ( ReflectiveOperationException e ) {
				logger.error( "can't load addon file: {}", container.getPath(), e );
			}
		}
		List<AddonContainer> list = this.containers
			.stream()
			.filter( AddonContainer::didFailToLoad )
			.collect( Collectors.toList() );
		logger.info( "Finished loading {} addons with {} fails", this.containers.size(), list.size() );
		if ( Boolean.getBoolean( "lc.debug.crashOnFail" ) && !list.isEmpty() ) {
			throw new RuntimeException( String.format( "LoaderComplex: %s addons failed to load!", list.size() ) );
		}
	}

	@Override
	public List<AddonContainer> getAddons() {
		return this.containers
			.stream()
			.map( AddonContainer.class::cast )
			.collect( Collectors.toList() );
	}

	/**
	 * Handles the fields of this addon implementation class, setting and getting requested stuff.
	 *
	 * @param container the addon's container.
	 * @param clazz     the addon's class.
	 */
	private void construct( AddonContainerImpl container, Class<? extends Addon> clazz ) throws ReflectiveOperationException {
		for ( Field field : clazz.getFields() ) {
			if ( field.isSynthetic() )
				continue;

			// set the first Instance-annotated static field to the instance
			if ( isStatic( field.getModifiers() ) && field.isAnnotationPresent( Instance.class ) ) {
				logger.info( " - Addon {} is using the Instance annotation! Using their provided instance.", container.getId() );
				field.setAccessible( true );
				container.implementation = (Addon) field.get( null );
				break;
			}
		}

		if ( container.implementation == null )
			container.implementation = clazz.getDeclaredConstructor().newInstance();
	}
}
