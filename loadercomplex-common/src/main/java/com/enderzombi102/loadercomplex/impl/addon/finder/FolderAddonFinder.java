package com.enderzombi102.loadercomplex.impl.addon.finder;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.function.Function;

/**
 * Finds addons in a specified folder, from a specified extension.
 */
public class FolderAddonFinder implements AddonFinder {
	private final @NotNull Path folderPath;
	private final @NotNull String folder;
	private final @NotNull String ext;

	public FolderAddonFinder( @NotNull String folder, @NotNull String ext ) {
		this.folderPath = Paths.get( System.getProperty( "user.dir" ), folder );
		this.folder = folder;
		this.ext = ext;
	}

	@Override
	public void findAddons( Logger logger, Function<Path, Boolean> jarConsumer ) {
		logger.info( "Scanning {} folder", this.folder );
		int count = 0;
		for ( File file : Objects.requireNonNull( this.folderPath.toFile().listFiles() ) ) {
			if ( file.getName().endsWith( this.ext ) && jarConsumer.apply( file.toPath() ) ) {
				count += 1;
			}
		}
		logger.info( " - Found {} addons", count );
	}
}
