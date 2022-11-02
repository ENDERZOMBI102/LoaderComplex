package com.enderzombi102.loadercomplex.impl.addon;

import com.enderzombi102.loadercomplex.api.addon.Addon;
import com.enderzombi102.loadercomplex.api.addon.AddonContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
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
	Addon implementation = null;

	public AddonContainerImpl( Path file) throws IOException {
		this.file = file;
		addonJar = new JarFile( file.toFile(), false );
		Attributes attributes = addonJar.getManifest().getMainAttributes();
		mainClass = attributes.getValue("LoaderComplex-Main");
		version = attributes.getValue("LoaderComplex-Version");
		id = attributes.getValue("LoaderComplex-AddonId");
		if ( mainClass == null || version == null || id == null )
			throw new IOException(
				"Not a LoaderComplex Addon! ( " +
				( mainClass == null ? "Missing LoaderComplex-Main attribute in manifest, " : "" ) +
				( version == null ? "Missing LoaderComplex-Version attribute in manifest, " : "" ) +
				"Missing LoaderComplex-AddonId attribute in manifest )"
			);
	}

	@Override
	public @NotNull String getName() {
		return implementation.getName() != null
			? implementation.getName()
			: file.getFileName().toString().replace(".lc.jar", "");
	}

	@Override
	public @NotNull String getVersion() {
		return version;
	}

	@Override
	public @NotNull String getId() {
		return id;
	}

	@Override
	public @NotNull JarFile getAddonJar() {
		return addonJar;
	}

	@Override
	public @NotNull Path getPath() {
		return file;
	}

	@Override
	public @NotNull String getMainClass() {
		return mainClass;
	}

	@Override
	public @NotNull String getAuthors() {
		return implementation.getAuthors();
	}

	@Override
	public @NotNull String getDescription() {
		return implementation.getDescription();
	}

	@Override
	public @Nullable String getIconPath() {
		return implementation.getIconPath();
	}

	@Override
	public @NotNull Map<String, String> getLinks() {
		return implementation.getLinks();
	}

	@Override
	public boolean didFailToLoad() {
		return this.implementation == null;
	}

	@Override
	public @NotNull Addon getImplementation() {
		return implementation;
	}
}
