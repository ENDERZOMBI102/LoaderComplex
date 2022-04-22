package com.enderzombi102.loadercomplex.modloader;

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
public class AddonContainer {
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

	public @NotNull String getName() {
		return implementation.getName() != null
			? implementation.getName()
			: file.getFileName().toString().replace(".lc.jar", "");
	}

	public String getVersion() {
		return version;
	}

	public String getId() {
		return id;
	}

	public JarFile getAddonJar() {
		return addonJar;
	}

	public Path getPath() {
		return file;
	}

	public String getMainClass() {
		return mainClass;
	}

	public String getAuthors() {
		return implementation.getAuthors();
	}

	public String getDescription() {
		return implementation.getDescription();
	}

	public @Nullable String getIconPath() {
		return implementation.getIconPath();
	}

	public Map<String, String> getLinks() {
		return implementation.getLinks();
	}

	public boolean didFailToLoad() {
		return this.implementation == null;
	}

	public Addon getImplementation() {
		return implementation;
	}
}
