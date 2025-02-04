package com.enderzombi102.loadercomplex.forge122.impl.platform

import com.enderzombi102.loadercomplex.api.platform.I18nSystem
import net.minecraft.client.resources.I18n

class ForgeI18nSystem : I18nSystem {
	override fun translate( key: String ): String =
		I18n.format( key )

	override fun translate( key: String, vararg args: Any ): String =
		I18n.format( key )

	override fun contains( key: String ): Boolean =
		I18n.hasKey( key )

	override fun getCurrentLang(): String = // TODO: Implement
		"en_us"
}
