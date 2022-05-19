package com.enderzombi102.loadercomplex.api.event;

import com.enderzombi102.eventsystem.CancellableEventData;
import com.enderzombi102.loadercomplex.api.entity.Player;

public class ClientChatEventData extends CancellableEventData {
	private final Player sender;
	private String message;

	public ClientChatEventData( String message, Player sender ) {
		this.message = message;
		this.sender = sender;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage( String message ) {
		this.message = message;
	}

	public Player getSender() {
		return sender;
	}

	public boolean isCommand() {
		return this.message.startsWith("/");
	}
}
