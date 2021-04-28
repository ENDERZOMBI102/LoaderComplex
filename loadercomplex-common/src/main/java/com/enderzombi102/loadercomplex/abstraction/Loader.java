package com.enderzombi102.loadercomplex.abstraction;

import com.enderzombi102.loadercomplex.abstraction.utils.LoaderType;
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
