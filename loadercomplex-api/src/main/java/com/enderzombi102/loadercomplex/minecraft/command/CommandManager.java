package com.enderzombi102.loadercomplex.minecraft.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import org.jetbrains.annotations.ApiStatus.AvailableSince;

@AvailableSince( "0.2.0" )
public interface CommandManager {
	/**
	 * Registers a command for server-side execution.
	 * @param command to register.
	 */
	void register( LiteralArgumentBuilder<CommandContext> command );

	/**
	 * Registers a command for client-side execution.
	 * @param command to register.
	 */
	void registerClient( LiteralArgumentBuilder<ClientCommandContext> command );
}
