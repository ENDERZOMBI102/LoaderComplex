package com.enderzombi102.loadercomplex.fabric171.mixin;

import com.enderzombi102.loadercomplex.api.minecraft.event.server.ChatEvent;
import com.enderzombi102.loadercomplex.fabric171.imixin.IMessageMixin;
import com.enderzombi102.loadercomplex.fabric171.impl.entity.FabricPlayerEntity;
import net.minecraft.server.filter.TextStream;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;


@Mixin( ServerPlayNetworkHandler.class )
public class ServerPlayNetworkHandlerMixin {
	@Shadow
	public ServerPlayerEntity player;
	@Unique
	private static final String CANCEL_TOKEN = "^|<CANCEL>|^";

	@ModifyVariable(
		method = "handleMessage",
		at = @At(
			value = "INVOKE_ASSIGN",
			target = "Lnet/minecraft/server/filter/TextStream$Message;getRaw()Ljava/lang/String;"
		),
		index = 2
	)
	public String modifyRawMessage( String rawMessage ) {
		ChatEvent evt = new ChatEvent( rawMessage, new FabricPlayerEntity( this.player ) );
		ChatEvent.RECV_MESSAGE.invoke( evt );
		return evt.isCancelled() ? CANCEL_TOKEN : evt.message;
	}

	@Inject(
		method = "handleMessage",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/filter/TextStream$Message;getFiltered()Ljava/lang/String;",
			shift = At.Shift.BEFORE
		),
		cancellable = true,
		locals = LocalCapture.CAPTURE_FAILHARD
	)
	public void onHandleMessage( TextStream.Message message, CallbackInfo info, String editedRawMessage ) {
		if ( editedRawMessage.equals( CANCEL_TOKEN ) ) {
			info.cancel();
		}

		// maintain the chat filtering vanilla feature
		if (! message.getFiltered().isEmpty() ) {
			((IMessageMixin) message).lc$setFilteredMessage( editedRawMessage );
		}
	}
}
