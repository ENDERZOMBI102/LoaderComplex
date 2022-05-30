package com.enderzombi102.loadercomplex.api.adv.keybind;

import com.enderzombi102.loadercomplex.api.utils.ResourceIdentifier;
import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.ApiStatus.AvailableSince;

@AvailableSince( "0.2.0" )
public interface KeybindManager {
	Keybind registerBind( @NotNull ResourceIdentifier translationKey, int code, @NotNull String category, @NotNull KeybindListener listener );
}
