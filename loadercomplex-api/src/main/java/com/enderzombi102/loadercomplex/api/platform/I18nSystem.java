package com.enderzombi102.loadercomplex.api.platform;

import org.jetbrains.annotations.NotNull;

/**
 * Translation system.<br/>
 * Gives access to the underlying game's translation system.
 */
public interface I18nSystem {
	/**
	 * Translates a key to its value for the current language.
	 * @param key key to get the translation for.
	 * @return the translated value.
	 */
	@NotNull String translate( @NotNull String key );

	/**
	 * Translates a key to its value for the current language and formats it with the given args.
	 * @param key key to get the translation for.
	 * @param args arguments for the translation.
	 * @return the translated value.
	 */
	@NotNull String translate( @NotNull String key, Object... args );

	/**
	 * Checks if a translation key is present in the system.
	 * @param key key to check for.
	 * @return true if it is, false otherwise.
	 */
	boolean contains( @NotNull String key );

	/**
	 * The langauge the game is currently set to.
	 */
	@NotNull String getCurrentLang();
}
