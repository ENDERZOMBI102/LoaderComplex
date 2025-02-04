package com.enderzombi102.loadercomplex.fabric171.mixin;

import com.enderzombi102.loadercomplex.fabric171.imixin.IMessageMixin;
import net.minecraft.server.filter.TextStream;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;


@Mixin( TextStream.Message.class )
public class MessageMixin implements IMessageMixin {
	@Shadow
	@Final
	@Mutable
	private String filtered;

	@Override
	public void lc$setFilteredMessage( String msg ) {
		this.filtered = msg;
	}
}
