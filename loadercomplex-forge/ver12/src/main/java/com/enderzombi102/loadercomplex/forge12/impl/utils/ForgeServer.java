package com.enderzombi102.loadercomplex.forge12.impl.utils;

import com.enderzombi102.loadercomplex.api.minecraft.util.Server;
import net.minecraft.server.MinecraftServer;

public class ForgeServer implements Server {
	private final MinecraftServer backingServer;

	public ForgeServer(MinecraftServer minecraftServer) {
		backingServer = minecraftServer;
	}

	@Override
	public Object getObject() {
		return backingServer;
	}
}
