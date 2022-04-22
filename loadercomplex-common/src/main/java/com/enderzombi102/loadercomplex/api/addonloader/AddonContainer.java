package com.enderzombi102.loadercomplex.api.addonloader;

import com.enderzombi102.loadercomplex.api.Addon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.Map;
import java.util.jar.JarFile;

/**
 * Represents a container for an addon
 */
public interface AddonContainer {
	/**
	 *
	 */
	@NotNull String getName();

	/**
	 *
	 */
	String getVersion();

	/**
	 *
	 */
	String getId();

	/**
	 *
	 */
	JarFile getAddonJar();

	/**
	 *
	 */
	Path getPath();

	/**
	 *
	 */
	String getMainClass();

	/**
	 *
	 */
	String getAuthors();

	/**
	 *
	 */
	String getDescription();

	/**
	 *
	 */
	@Nullable String getIconPath();

	/**
	 *
	 */
	Map<String, String> getLinks();

	/**
	 *
	 */
	boolean didFailToLoad();

	/**
	 *
	 */
	Addon getImplementation();
}
