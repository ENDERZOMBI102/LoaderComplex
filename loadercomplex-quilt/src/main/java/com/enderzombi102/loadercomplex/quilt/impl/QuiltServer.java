package com.enderzombi102.loadercomplex.quilt.impl;

import com.enderzombi102.loadercomplex.api.utils.Server;
import net.minecraft.server.MinecraftServer;

public class QuiltServer implements Server {
	private final MinecraftServer backingServer;

	public QuiltServer(MinecraftServer server) {
		this.backingServer = server;
	}

	@Override
	public Object getObject() {
		return this.backingServer;
	}
}
