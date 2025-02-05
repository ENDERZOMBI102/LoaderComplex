package com.enderzombi102.loadercomplex.forge182.impl

import com.enderzombi102.loadercomplex.api.AddonContext
import com.enderzombi102.loadercomplex.api.minecraft.command.CommandManager
import com.enderzombi102.loadercomplex.api.minecraft.keybind.KeybindManager
import com.enderzombi102.loadercomplex.api.minecraft.network.NetworkManager
import com.enderzombi102.loadercomplex.api.platform.*
import com.enderzombi102.loadercomplex.forge182.impl.platform.ForgeFactoryWorld
import com.enderzombi102.loadercomplex.forge182.impl.platform.ForgeRegistry
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.fml.ModList
import net.minecraftforge.fml.loading.FMLEnvironment
import net.minecraftforge.fml.loading.FMLLoader
import net.minecraftforge.versions.forge.ForgeVersion
import java.nio.file.Path


class ForgeAddonContext : AddonContext {
	private val registry: Registry = ForgeRegistry()

	override fun getPlatformInfo(): PlatformInfo {
		return object : PlatformInfo {
			override fun name(): String =
				"Forge"

			override fun version(): String =
				ForgeVersion.getVersion()

			override fun id(): String =
				"forge"

			override fun minecraftVersion(): String =
				"1.18.2"

			override fun getApiVersion(): String =
				"0.1.3"

			override fun isDeveloperEnvironment(): Boolean =
				FMLEnvironment.production
		}
	}

	override fun isModLoaded(id: String): Boolean {
		return ModList.get().isLoaded(id)
	}

	override fun getFactoryWorld(): FactoryWorld =
		ForgeFactoryWorld()

	override fun isAtLeastMinecraft(version: String): Boolean {
		throw NotImplementedError()
	}

	override fun isDedicatedServer(): Boolean =
		FMLLoader.getDist() == Dist.DEDICATED_SERVER

	override fun getRegistry(): Registry =
		this.registry

	override fun getI18nSystem(): I18nSystem =
		throw NotImplementedError()

	override fun getResourceLoader(): ResourceLoader =
		throw NotImplementedError()

	override fun getConfigDir(): Path =
		FMLLoader.getGamePath().resolve( "config" )

	override fun getKeybindManager(): KeybindManager =
		throw NotImplementedError()

	override fun getNetworkManager(): NetworkManager =
		throw NotImplementedError()

	override fun getCommandManager(): CommandManager =
		throw NotImplementedError()
}
