package com.enderzombi102.loadercomplex.fabric17.mixin;

import com.enderzombi102.loadercomplex.api.event.ServerChatEventData;
import com.enderzombi102.loadercomplex.fabric17.LoaderComplexFabric;
import com.enderzombi102.loadercomplex.fabric17.imixin.IMessageMixin;
import com.enderzombi102.loadercomplex.fabric17.impl.entity.FabricPlayer;
import net.minecraft.server.filter.TextStream;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ServerPlayNetworkHandler.class)
@SuppressWarnings("InvalidInjectorMethodSignature")
public class ServerPlayNetworkHandlerMixin {
	@Shadow public ServerPlayerEntity player;

	@ModifyVariable(
		method = "handleMessage",
		at = @At(
			value = "INVOKE_ASSIGN",
			target = "Lnet/minecraft/server/filter/TextStream$Message;getRaw()Ljava/lang/String;"
		),
		index = 2
	)
	public String modifyRawMessage( String rawMessage ) {
		var data = LoaderComplexFabric.INSTANCE.getLoader()
			.getEventSystem()
			.dispatch(
				"lc.server.chat.receive",
				new ServerChatEventData( rawMessage, new FabricPlayer( this.player ) )
			);
		return data.isCancelled() ? "^<CANCEL>^" : data.getJsonMessage();
	}

	@Inject(
		method = "handleMessage",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/filter/TextStream$Message;getFiltered()Ljava/lang/String;",
			shift = At.Shift.BEFORE
		),
		cancellable = true,
		locals = LocalCapture.CAPTURE_FAILEXCEPTION
	)
	public void onHandleMessage( TextStream.Message message, CallbackInfo info, String editedRawMessage ) {
		if ( editedRawMessage.equals("^<CANCEL>^") )
			info.cancel();

		// maintain the chat filtering vanilla feature
		if (! message.getFiltered().isEmpty() )
			( (IMessageMixin) message ).lc$setFilteredMessage( editedRawMessage );
	}
}
