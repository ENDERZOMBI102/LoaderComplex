package com.enderzombi102.loadercomplex.fabric171.impl.server

import com.enderzombi102.loadercomplex.api.minecraft.server.Server
import net.minecraft.server.MinecraftServer

class FabricServer( private val backingServer: MinecraftServer ) : Server {
	override fun getObject(): Any =
		this.backingServer
}
