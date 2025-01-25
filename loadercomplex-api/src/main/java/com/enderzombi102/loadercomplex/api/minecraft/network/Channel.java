package com.enderzombi102.loadercomplex.api.minecraft.network;

import com.enderzombi102.loadercomplex.api.annotation.Json;

import java.util.function.Consumer;

import org.jetbrains.annotations.ApiStatus.AvailableSince;

// TODO: make better API
@AvailableSince( "0.2.0" )
public interface Channel {
	String getChannelId();
	void send( @Json String data );
	void setCallback( Consumer<@Json String> callback );
}
