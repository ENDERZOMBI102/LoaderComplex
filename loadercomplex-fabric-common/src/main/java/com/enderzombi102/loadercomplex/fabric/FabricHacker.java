package com.enderzombi102.loadercomplex.fabric;

import net.fabricmc.loader.api.FabricLoader;

public class FabricHacker implements net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint {
	@Override
	public void onPreLaunch() {
		FabricLoader.getInstance()
			.getModContainer( "loadercomplex" )
			.orElseThrow(IllegalAccessError::new)
			.getMetadata()
			.getAuthors();
	}
}
