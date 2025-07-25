package com.enderzombi102.loadercomplex.forge122.impl

import com.enderzombi102.loadercomplex.api.AddonContext
import com.enderzombi102.loadercomplex.api.minecraft.command.CommandManager
import com.enderzombi102.loadercomplex.api.minecraft.keybind.KeybindManager
import com.enderzombi102.loadercomplex.api.minecraft.network.NetworkManager
import com.enderzombi102.loadercomplex.api.platform.*
import com.enderzombi102.loadercomplex.forge122.impl.platform.ForgeFactoryWorld
import com.enderzombi102.loadercomplex.forge122.impl.platform.ForgeI18nSystem
import com.enderzombi102.loadercomplex.forge122.impl.platform.ForgeRegistry
import com.unascribed.flexver.FlexVerComparator
import net.minecraft.launchwrapper.Launch
import net.minecraftforge.common.ForgeVersion
import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.common.Loader
import java.nio.file.Path

class ForgeAddonContext : AddonContext {
	private val registry = ForgeRegistry()
	private val i18nSystem = ForgeI18nSystem()

	override fun getPlatformInfo(): PlatformInfo {
		return object : PlatformInfo {
			override fun name(): String =
				"Forge"

			override fun version(): String =
				ForgeVersion.getVersion()

			override fun id(): String =
				"forge-legacy"

			override fun minecraftVersion(): String =
				"1.12.2"

			override fun getApiVersion(): String =
				"0.1.3"

			override fun isDeveloperEnvironment(): Boolean =
				Launch.blackboard.getOrDefault("fml.deobfuscatedEnvironment", false) as Boolean
		}
	}

	override fun getRegistry(): Registry =
		this.registry

	override fun getFactoryWorld(): FactoryWorld =
		ForgeFactoryWorld()

	override fun isAtLeastMinecraft(version: String): Boolean =
		FlexVerComparator.compare( ForgeVersion.mcVersion, version ) <= 0

	override fun isDedicatedServer(): Boolean =
		FMLCommonHandler.instance().side.isServer

	override fun isModLoaded(id: String): Boolean =
		Loader.isModLoaded(id)

	override fun getI18nSystem(): I18nSystem =
		this.i18nSystem

	override fun getResourceLoader(): ResourceLoader =
		throw NotImplementedError()

	override fun getConfigDir(): Path =
		Loader.instance().configDir.toPath()

	override fun getKeybindManager(): KeybindManager =
		throw NotImplementedError()

	override fun getNetworkManager(): NetworkManager =
		throw NotImplementedError()

	override fun getCommandManager(): CommandManager =
		throw NotImplementedError()
}
