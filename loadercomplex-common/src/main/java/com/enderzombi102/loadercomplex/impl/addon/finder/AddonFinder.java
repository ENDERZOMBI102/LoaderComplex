package com.enderzombi102.loadercomplex.impl.addon.finder;

import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.function.Function;

/**
 * A thing that finds addons to load.
 */
public interface AddonFinder {
	/**
	 * Finds all addons.
	 * @param logger the logger instance for the {@link com.enderzombi102.loadercomplex.api.addon.AddonLoader}.
	 * @param pathConsumer consumes the path to a possible addon.
	 */
	void findAddons( Logger logger, Function<Path, Boolean> pathConsumer );
}
