package com.enderzombi102.loadercomplex.api.addon;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.Map;
import java.util.jar.JarFile;

/**
 * Represents a container for an addon
 */
@ApiStatus.AvailableSince("0.1.4")
public interface AddonContainer {
	/** The name of this addon. */
	@NotNull String getName();

	/** The version of this addon. */
	@NotNull String getVersion();

	/** The ID of this addon. */
	@NotNull String getId();

	/** The jar file of this addon. */
	@NotNull JarFile getAddonJar();

	/** The path of this addon's jar file. */
	@NotNull Path getPath();

	/** The main class of this addon. */
	@NotNull String getMainClass();

	/** The authors of this addon. */
	@NotNull String getAuthors();

	/** The description of this addon. */
	@NotNull String getDescription();

	/** The path of the icon of this addon. */
	@Nullable String getIconPath();

	/** The links for this addon. */
	@NotNull Map<String, String> getLinks();

	/** If this addon has failed to load. */
	boolean didFailToLoad();

	/** The implementing class of this addon. */
	@Nullable Addon getImplementation();
}
