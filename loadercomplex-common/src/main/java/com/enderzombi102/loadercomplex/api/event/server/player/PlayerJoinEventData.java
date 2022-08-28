package com.enderzombi102.loadercomplex.api.event.server.player;

import com.enderzombi102.eventsystem.EventData;
import com.enderzombi102.loadercomplex.api.entity.Player;
import org.jetbrains.annotations.ApiStatus;

/**
 * Event id is `lc.server.player.join`
 *
 * Fired when a player joins this server
 */
@ApiStatus.AvailableSince("0.2.0")
public class PlayerJoinEventData extends EventData {
	public PlayerJoinEventData( Player player ) {

	}
}
