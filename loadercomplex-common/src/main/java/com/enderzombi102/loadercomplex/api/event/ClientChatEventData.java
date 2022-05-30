package com.enderzombi102.loadercomplex.api.event;

import com.enderzombi102.eventsystem.CancellableEventData;
import com.enderzombi102.loadercomplex.api.entity.Player;
import org.jetbrains.annotations.ApiStatus;

/**
 * Event id are 'lc.client.chat.send'
 *
 * Fired before a message is sent to the server.
 */
@ApiStatus.AvailableSince("0.2.0")
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

	/**
	 * Returns whether the message starts with `/`
	 */
	public boolean isCommand() {
		return this.message.startsWith("/");
	}
}
