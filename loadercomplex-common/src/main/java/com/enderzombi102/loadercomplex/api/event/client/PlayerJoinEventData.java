package com.enderzombi102.loadercomplex.api.event.client;

import com.enderzombi102.eventsystem.CancellableEventData;
import com.enderzombi102.loadercomplex.api.entity.Player;

/**
 * Event id is `lc.client.player.join`
 *
 * Fired when this client successfully connects to a server
 */
public class PlayerJoinEventData extends CancellableEventData {
	private final Player player = null;

	public PlayerJoinEventData() {

	}
}
