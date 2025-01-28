package com.enderzombi102.loadercomplex.api.minecraft.event.client;

import com.enderzombi102.loadercomplex.api.event.CancellableEvent;
import com.enderzombi102.loadercomplex.api.event.EventDispatcher;
import com.enderzombi102.loadercomplex.api.minecraft.entity.PlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChatEvent extends CancellableEvent {
	public static final EventDispatcher<ChatEvent> SEND_MESSAGE = EventDispatcher.createCancellable( ChatEvent.class );
	public static final EventDispatcher<ChatEvent> RECV_MESSAGE = EventDispatcher.createCancellable( ChatEvent.class );

	public @NotNull String message;
	public @Nullable PlayerEntity player;

	public ChatEvent( @NotNull String message, @Nullable PlayerEntity player ) {
		this.message = message;
		this.player = player;
	}
}
