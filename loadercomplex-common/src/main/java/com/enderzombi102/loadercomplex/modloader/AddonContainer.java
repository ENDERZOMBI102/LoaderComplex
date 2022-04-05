package com.enderzombi102.loadercomplex.modloader;

import com.enderzombi102.loadercomplex.api.Addon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

public class AddonContainer {

	private final String mainClass;
	private final Path file;
	private final JarFile addonJar;
	private final String ID;
	private final String version;
	Addon implementation;

	public AddonContainer(Path file) throws IOException {
		this.file = file;
		addonJar = new JarFile( file.toFile(), false );
		Attributes attributes = addonJar.getManifest().getMainAttributes();
		mainClass = attributes.getValue("LoaderComplex-Main");
		version = attributes.getValue("Implementation-Version");
		ID = attributes.getValue("LoaderComplex-AddonId");
	}

	public @NotNull String getName() {
		return implementation.getName() != null ?
				implementation.getName() :
				file.getFileName().toString().replace(".lc.jar", "");
	}

	public String getVersion() {
		return version;
	}

	public String getID() {
		return ID;
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

	public Addon getImplementation() {
		return implementation;
	}
}
