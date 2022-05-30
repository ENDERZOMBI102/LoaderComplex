package com.enderzombi102.loadercomplex.api.adv.keybind;

import org.jetbrains.annotations.ApiStatus.AvailableSince;

@AvailableSince( "0.2.0" )
public interface KeybindListener {
	/**
	 * Callback for when a key is pressed
	 * @param ctrl whether ctrl is pressed
	 * @param shift whether shift is pressed
	 * @param alt whether alt is pressed
	 */
	void onKeyPressed( boolean ctrl, boolean shift, boolean alt );
}
