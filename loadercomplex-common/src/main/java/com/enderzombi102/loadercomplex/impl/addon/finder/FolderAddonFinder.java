package com.enderzombi102.loadercomplex.impl.addon.finder;

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
	private final Path folderPath;
	private final String folder;

	public FolderAddonFinder( String folder ) {
		this.folderPath = Paths.get( System.getProperty( "user.dir" ), folder );
		this.folder = folder;
	}

	@Override
	public void findAddons( Logger logger, Function<Path, Boolean> jarConsumer ) {
		logger.info( "Scanning {} folder", folder );
		int count = 0;
		for ( File file : Objects.requireNonNull( folderPath.toFile().listFiles() ) )
			if ( file.getName().endsWith( ".lc.jar" ) && jarConsumer.apply( file.toPath() ) )
				count++;
		logger.info( " - Found {} addons", count );
	}
}
