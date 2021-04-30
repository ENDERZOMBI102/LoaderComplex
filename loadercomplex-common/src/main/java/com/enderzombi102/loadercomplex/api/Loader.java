package com.enderzombi102.loadercomplex.api;

import com.enderzombi102.loadercomplex.api.utils.LoaderType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface Loader {

	LoaderType getLoaderType();
	Registry getRegistry();
	String getMinecraftVersion();
	String getLoaderVersion();
	boolean isModLoaded(String id);
	default Logger getLogger(String modid) {
		return LogManager.getLogger(modid);
	}

}
