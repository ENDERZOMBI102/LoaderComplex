package com.enderzombi102.loadercomplex.addonloader;

import com.enderzombi102.loadercomplex.api.Addon;
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
public class AddonContainer implements com.enderzombi102.loadercomplex.api.addonloader.AddonContainer {
	private final String mainClass;
	private final Path file;
	private final JarFile addonJar;
	private final String id;
	private final String version;
	Addon implementation = null;

	public AddonContainer(Path file) throws IOException {
		this.file = file;
		addonJar = new JarFile( file.toFile(), false );
		Attributes attributes = addonJar.getManifest().getMainAttributes();
		mainClass = attributes.getValue("LoaderComplex-Main");
		version = attributes.getValue("LoaderComplex-Version");
		id = attributes.getValue("LoaderComplex-AddonId");
		if ( mainClass == null || version == null || id == null )
			throw new IOException(
				"Not a LoaderComplex Addon! ( " + (
					mainClass == null ? "Missing LoaderComplex-Main attribute in manifest" :
					version == null ? "Missing LoaderComplex-Version attribute in manifest" :
					"Missing LoaderComplex-AddonId attribute in manifest"
				) + " )"
			);
	}

	@Override
	public @NotNull String getName() {
		return implementation.getName() != null
			? implementation.getName()
			: file.getFileName().toString().replace(".lc.jar", "");
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public JarFile getAddonJar() {
		return addonJar;
	}

	@Override
	public Path getPath() {
		return file;
	}

	@Override
	public String getMainClass() {
		return mainClass;
	}

	@Override
	public String getAuthors() {
		return implementation.getAuthors();
	}

	@Override
	public String getDescription() {
		return implementation.getDescription();
	}

	@Override
	public @Nullable String getIconPath() {
		return implementation.getIconPath();
	}

	@Override
	public Map<String, String> getLinks() {
		return implementation.getLinks();
	}

	@Override
	public boolean didFailToLoad() {
		return this.implementation == null;
	}

	@Override
	public Addon getImplementation() {
		return implementation;
	}
}
