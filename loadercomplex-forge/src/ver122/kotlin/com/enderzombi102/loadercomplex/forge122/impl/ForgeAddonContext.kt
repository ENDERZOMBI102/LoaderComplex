package com.enderzombi102.loadercomplex.forge122.impl

import com.enderzombi102.loadercomplex.api.AddonContext
import com.enderzombi102.loadercomplex.api.platform.*
import com.enderzombi102.loadercomplex.forge122.impl.platform.ForgeFactoryWorld
import com.enderzombi102.loadercomplex.forge122.impl.platform.ForgeI18nSystem
import com.enderzombi102.loadercomplex.forge122.impl.platform.ForgeRegistry
import net.minecraft.launchwrapper.Launch
import net.minecraftforge.common.ForgeVersion
import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.common.Loader
import java.nio.file.Path

class ForgeAddonContext : AddonContext {
	private val registry =
		ForgeRegistry()
	private val i18nSystem: I18nSystem = ForgeI18nSystem()

	override fun getPlatformInfo(): PlatformInfo {
		return object : PlatformInfo {
			override fun name(): String {
				return "Forge"
			}

			override fun version(): String {
				return ForgeVersion.getVersion()
			}

			override fun id(): String {
				return "forge-legacy"
			}

			override fun minecraftVersion(): String {
				return "1.12.2"
			}

			override fun getApiVersion(): String {
				return "0.1.3"
			}

			override fun isDeveloperEnvironment(): Boolean {
				return Launch.blackboard.getOrDefault("fml.deobfuscatedEnvironment", false) as Boolean
			}
		}
	}

	override fun getRegistry(): Registry {
		return this.registry
	}

	override fun getFactoryWorld(): FactoryWorld {
		return ForgeFactoryWorld()
	}

	override fun isAtLeastMinecraft(version: String): Boolean {
		throw RuntimeException("Not implemented")
	}

	override fun isDedicatedServer(): Boolean {
		return FMLCommonHandler.instance().side.isServer
	}

	override fun isModLoaded(id: String): Boolean {
		return Loader.isModLoaded(id)
	}

	override fun getI18nSystem(): I18nSystem {
		return this.i18nSystem
	}

	override fun getResourceLoader(): ResourceLoader {
		throw RuntimeException("Not implemented")
	}

	override fun getConfigDir(): Path {
		return Loader.instance().configDir.toPath()
	}
}
