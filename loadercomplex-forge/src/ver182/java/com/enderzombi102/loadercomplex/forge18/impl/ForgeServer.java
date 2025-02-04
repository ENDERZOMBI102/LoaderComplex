package com.enderzombi102.loadercomplex.forge18.impl;

import com.enderzombi102.loadercomplex.api.minecraft.server.Server;
import net.minecraft.server.MinecraftServer;

public class ForgeServer implements Server {
	private final MinecraftServer backingServer;

	public ForgeServer(MinecraftServer server) {
		this.backingServer = server;
	}

	@Override
	public Object getObject() {
		return this.backingServer;
	}
}
