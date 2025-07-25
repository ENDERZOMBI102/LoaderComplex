package com.enderzombi102.loadercomplex.fabric122.impl

import com.enderzombi102.loadercomplex.api.AddonContext
import com.enderzombi102.loadercomplex.api.platform.*
import com.enderzombi102.loadercomplex.fabric122.impl.platform.FabricFactoryWorld
import com.enderzombi102.loadercomplex.fabric122.impl.platform.FabricRegistry
import com.unascribed.flexver.FlexVerComparator
import net.fabricmc.api.EnvType
import net.fabricmc.loader.api.FabricLoader
import java.nio.file.Path


class FabricAddonContext : AddonContext {
	private val registry: Registry = FabricRegistry()

	override fun getPlatformInfo(): PlatformInfo {
		return object : PlatformInfo {
			override fun name(): String =
				""

			override fun version(): String =
				FabricLoader.getInstance()
					.getModContainer( "fabricloader" )
					.orElseThrow( ::IllegalStateException )
					.metadata
					.version
					.friendlyString

			override fun id(): String =
				""

			override fun minecraftVersion(): String =
				MINECRAFT_VERSION

			override fun getApiVersion(): String =
				"1.3"

			override fun isDeveloperEnvironment(): Boolean =
				FabricLoader.getInstance().isDevelopmentEnvironment
		}
	}

	override fun getRegistry(): Registry =
		this.registry

	override fun getFactoryWorld(): FactoryWorld =
		FabricFactoryWorld()

	override fun isAtLeastMinecraft( version: String ): Boolean =
		FlexVerComparator.compare( version, MINECRAFT_VERSION ) >= 0

	override fun isDedicatedServer(): Boolean =
		FabricLoader.getInstance().environmentType == EnvType.SERVER

	override fun getI18nSystem(): I18nSystem =
		throw NotImplementedError()

	override fun getResourceLoader(): ResourceLoader =
		throw NotImplementedError()

	override fun getConfigDir(): Path =
		FabricLoader.getInstance().configDir

	override fun isModLoaded( id: String ): Boolean =
		FabricLoader.getInstance().isModLoaded( id )

	//	override getApiVersion(): Version =
	//		Version( "0.1.3", LocalDateTime.now().format( DateTimeFormatter.ofPattern( "dd-MM-yyyy'T'HH:mm:ss" ) ) )

	companion object {
		const val MINECRAFT_VERSION: String = "1.12.2"
	}
}
