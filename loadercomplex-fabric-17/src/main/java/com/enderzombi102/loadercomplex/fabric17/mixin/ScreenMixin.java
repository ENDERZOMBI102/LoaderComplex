package com.enderzombi102.loadercomplex.fabric17.mixin;

import com.enderzombi102.loadercomplex.api.event.ClientChatEventData;
import com.enderzombi102.loadercomplex.fabric17.LoaderComplexFabric;
import com.enderzombi102.loadercomplex.fabric17.impl.entity.FabricPlayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// This is relying on the mixin resolution order, which is very hacky.
@Mixin(Screen.class)
public class ScreenMixin {
	@Shadow protected MinecraftClient client;

	@ModifyVariable(
		method = "sendMessage(Ljava/lang/String;Z)V",
		at = @At( "HEAD" ),
		argsOnly = true,
		index = 1
	)
	public String onSendMessage( String message ) {
		var data = LoaderComplexFabric.INSTANCE.getLoader()
			.getEventSystem()
			.dispatch(
				"lc.client.chat.send",
				new ClientChatEventData( message, new FabricPlayer( this.client.player ) )
			);
		return data.isCancelled() ? "^<CANCEL>^" : data.getMessage();
	}

	@Inject(
		method = "sendMessage(Ljava/lang/String;Z)V",
		at = @At( "HEAD" ),
		cancellable = true
	)
	public void onMessageSent( String message, boolean toHud, CallbackInfo ci ) {
		if ( message.equals( "^<CANCEL>^" ) )
			ci.cancel();
	}
}
