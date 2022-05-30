package com.enderzombi102.loadercomplex.api.adv.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import org.jetbrains.annotations.ApiStatus.AvailableSince;

@AvailableSince( "0.2.0" )
public interface CommandManager {
	/**
	 * Registers a command
	 * @param command
	 */
	void addCommand( LiteralArgumentBuilder<CommandContext> command );
}
