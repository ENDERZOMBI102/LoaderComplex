package com.enderzombi102.loadercomplex.api.minecraft.command;

import com.enderzombi102.loadercomplex.api.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.ApiStatus.AvailableSince;
import org.jetbrains.annotations.Nullable;

@AvailableSince( "0.2.0" )
public interface CommandContext {
	@Nullable LivingEntity getSender();

	default void reply( String text ) {
		if ( this.getSender() != null && this.getSender().isPlayer() )
			this.getSender().asPlayer().sendMessage( text );
	}
}
