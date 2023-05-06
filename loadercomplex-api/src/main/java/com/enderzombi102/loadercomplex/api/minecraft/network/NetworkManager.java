package com.enderzombi102.loadercomplex.api.minecraft.network;

import com.enderzombi102.loadercomplex.api.minecraft.entity.Player;
import org.jetbrains.annotations.ApiStatus.AvailableSince;

@AvailableSince( "0.2.0" )
public interface NetworkManager {
	Channel getOrOpenChannel( String addonId, Player player );
}