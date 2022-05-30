package com.enderzombi102.loadercomplex.api.event.player;

import com.enderzombi102.eventsystem.EventData;
import com.enderzombi102.loadercomplex.api.entity.Player;
import org.jetbrains.annotations.ApiStatus;

/**
 * Event id is `lc.server.player.join` for the server and `lc.client.player.join` for the client
 *
 * Fired when a player joins this server
 */
@ApiStatus.AvailableSince("0.2.0")
public class PlayerJoinEventData extends EventData {
	public PlayerJoinEventData( Player player, ) {

	}
}
