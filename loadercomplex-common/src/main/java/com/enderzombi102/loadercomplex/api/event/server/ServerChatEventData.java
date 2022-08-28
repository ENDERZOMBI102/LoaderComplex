package com.enderzombi102.loadercomplex.api.event.server;

import com.enderzombi102.eventsystem.CancellableEventData;
import com.enderzombi102.loadercomplex.api.annotation.Json;
import com.enderzombi102.loadercomplex.api.entity.Player;
import org.jetbrains.annotations.ApiStatus;

/**
 * Event id is 'lc.server.chat.receive'
 *
 * Fired when a message is received from a client.
 * The message may or may not be json, check for `{}`!
 */
@ApiStatus.AvailableSince("0.2.0")
public class ServerChatEventData extends CancellableEventData {
	private final Player sender;
	private @Json String message;

	public ServerChatEventData( @Json String message, Player sender ) {
		this.message = message;
		this.sender = sender;
	}

	public Player getSender() {
		return sender;
	}

	@Json
	public String getJsonMessage() {
		return message;
	}

	public void setJsonMessage( @Json String message ) {
		this.message = message;
	}
}
