package com.enderzombi102.loadercomplex.fabric12.impl;

import com.enderzombi102.loadercomplex.api.minecraft.util.Server;
import net.minecraft.server.MinecraftServer;

public class FabricServer implements Server {
	private final MinecraftServer backingServer;

	public FabricServer(MinecraftServer server) {
		this.backingServer = server;
	}

	@Override
	public Object getObject() {
		return this.backingServer;
	}
}
