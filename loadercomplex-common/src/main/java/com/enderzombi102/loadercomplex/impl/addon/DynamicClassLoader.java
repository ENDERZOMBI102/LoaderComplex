package com.enderzombi102.loadercomplex.impl.addon;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * A {@link URLClassLoader} which can add URLs dynamically.
 */
public class DynamicClassLoader extends URLClassLoader {
	public DynamicClassLoader() {
		super( new URL[0], DynamicClassLoader.class.getClassLoader() );
	}

	@Override
	public void addURL( URL url ) {
		super.addURL( url );
	}
}
