package com.enderzombi102.loadercomplex.abstraction;

import com.enderzombi102.loadercomplex.abstraction.utils.LoaderType;

public interface Loader {

	LoaderType getLoaderType();
	Registry getRegistry();
	String getMinecraftVersion();
	String getLoaderVersion();
	boolean isModLoaded(String id);

}
