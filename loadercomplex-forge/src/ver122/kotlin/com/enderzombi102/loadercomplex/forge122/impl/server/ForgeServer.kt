package com.enderzombi102.loadercomplex.forge122.impl.server

import com.enderzombi102.loadercomplex.api.minecraft.server.Server
import net.minecraft.server.MinecraftServer

class ForgeServer( private val backingServer: MinecraftServer ) : Server {
	override fun getObject(): Any =
		this.backingServer
}
