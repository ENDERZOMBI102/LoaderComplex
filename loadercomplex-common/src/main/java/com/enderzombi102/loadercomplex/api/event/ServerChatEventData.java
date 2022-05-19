package com.enderzombi102.loadercomplex.api.event;

import com.enderzombi102.eventsystem.CancellableEventData;
import com.enderzombi102.loadercomplex.api.annotation.Json;
import com.enderzombi102.loadercomplex.api.entity.Player;

public class ServerChatEventData extends CancellableEventData {
	private final Player sender;
	@Json
	private String message;

	public ServerChatEventData( String message, Player sender ) {
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
