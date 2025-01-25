package com.enderzombi102.loadercomplex.impl.addon;

import com.enderzombi102.loadercomplex.api.addon.Addon;
import com.enderzombi102.loadercomplex.api.addon.AddonContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

/**
 * Container for an addon implementation.<br/>
 * <br/>
 * Contains various info about an addon such as id and description.
 */
public class AddonContainerImpl implements AddonContainer {
	private final String mainClass;
	private final Path file;
	private final JarFile addonJar;
	private final String id;
	private final String version;
	private final String buildDate;
	Addon implementation = null;

	public AddonContainerImpl( Path file ) throws IOException, IllegalStateException {
		this.file = file;
		this.addonJar = new JarFile( file.toFile(), false );
		Attributes attributes = this.addonJar.getManifest().getMainAttributes();
		this.id        = attributes.getValue( "LoaderComplex-Id" );
		this.mainClass = attributes.getValue( "LoaderComplex-Main" );
		this.version   = attributes.getValue( "LoaderComplex-Version" );
		this.buildDate = attributes.getValue( "LoaderComplex-BuildDate" );
		List<String> errors = new ArrayList<>( 4 );
		if ( this.mainClass == null ) {
			errors.add( "Missing LoaderComplex-Main attribute in manifest" );
		}
		if ( this.version == null ) {
			errors.add( "Missing LoaderComplex-Version attribute in manifest" );
		}
		if ( this.buildDate == null ) {
			errors.add( "Missing LoaderComplex-BuildDate attribute in manifest" );
		}
		if ( this.id == null ) {
			errors.add( "Missing LoaderComplex-Id attribute in manifest" );
		}
		if ( !errors.isEmpty() ) {
			throw new IllegalStateException( "Not a LoaderComplex Addon! ( " + String.join( ", ", errors ) + ")" );
		}
	}

	@Override
	public @NotNull String getName() {
		if ( this.implementation.getName() != null ) {
			return this.implementation.getName();
		}
		String filename = this.file.getFileName().toString();
		return filename.substring( 0, filename.length() - 8 );
	}

	@Override
	public @NotNull String getVersion() {
		return this.version;
	}

	@Override
	public @NotNull String getBuildDate() {
		return this.buildDate;
	}

	@Override
	public @NotNull String getId() {
		return this.id;
	}

	@Override
	public @NotNull JarFile getAddonJar() {
		return this.addonJar;
	}

	@Override
	public @NotNull Path getPath() {
		return this.file;
	}

	@Override
	public @NotNull String getMainClass() {
		return this.mainClass;
	}

	@Override
	public @NotNull String getAuthors() {
		return this.implementation.getAuthors();
	}

	@Override
	public @NotNull String getDescription() {
		return this.implementation.getDescription();
	}

	@Override
	public @Nullable String getIconPath() {
		return this.implementation.getIconPath();
	}

	@Override
	public @NotNull Map<String, String> getLinks() {
		return this.implementation.getLinks();
	}

	@Override
	public boolean didFailToLoad() {
		return this.implementation == null;
	}

	@Override
	public @NotNull Addon getImplementation() {
		return this.implementation;
	}
}
