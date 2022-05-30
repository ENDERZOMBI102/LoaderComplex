package com.enderzombi102.loadercomplex.api.adv.keybind;

import com.enderzombi102.loadercomplex.api.utils.ResourceIdentifier;

import org.jetbrains.annotations.ApiStatus.AvailableSince;

@AvailableSince( "0.2.0" )
public interface Keybind {
	KeybindListener getListener();
	void setListener( KeybindListener listener );

	int getCode();
	void setCode( int code );
	boolean isBound();

	ResourceIdentifier getTranslationKey();
	void setTranslationKey( ResourceIdentifier translationKey );

	String getCategory();
}
