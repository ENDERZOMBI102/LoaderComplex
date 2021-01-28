package com.enderzombi102.loadercomplex.common.abstraction;

import com.enderzombi102.loadercomplex.common.abstraction.utils.LoaderType;

public interface Loader {

	LoaderType getLoaderType();
	Registry getRegistry();
	String getMinecraftVersion();
	String getLoaderVersion();
	boolean isModLoaded(String id);

}
