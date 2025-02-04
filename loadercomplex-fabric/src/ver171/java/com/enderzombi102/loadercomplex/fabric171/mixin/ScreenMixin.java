package com.enderzombi102.loadercomplex.fabric171.mixin;

import com.enderzombi102.loadercomplex.api.minecraft.event.client.ChatEvent;
import com.enderzombi102.loadercomplex.fabric171.impl.entity.FabricPlayerEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


// This is relying on the mixin resolution order, which is very hacky.
@Mixin( Screen.class )
public class ScreenMixin {
	@Shadow
	protected MinecraftClient client;
	@Unique
	private static final String CANCEL_TOKEN = "^|<CANCEL>|^";

	@ModifyVariable(
		method = "sendMessage(Ljava/lang/String;Z)V",
		at = @At( "HEAD" ),
		argsOnly = true,
		index = 1
	)
	public String onSendMessage( String message ) {
		assert this.client.player != null;
		ChatEvent evt = new ChatEvent( message, new FabricPlayerEntity( this.client.player ) );
		ChatEvent.SEND_MESSAGE.invoke( evt );
		return evt.isCancelled() ? CANCEL_TOKEN : evt.message;
	}

	@Inject(
		method = "sendMessage(Ljava/lang/String;Z)V",
		at = @At( "HEAD" ),
		cancellable = true
	)
	public void onMessageSent( String message, boolean toHud, CallbackInfo ci ) {
		if ( CANCEL_TOKEN.equals( message ) ) {
			ci.cancel();
		}
	}
}
