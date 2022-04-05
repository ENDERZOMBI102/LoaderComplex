package com.enderzombi102.loadercomplex.fabric12.mixin;

import com.enderzombi102.loadercomplex.fabric12.LoaderComplexFabric;
import com.enderzombi102.loadercomplex.fabric12.impl.FabricRegistry;
import com.enderzombi102.loadercomplex.Utils;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.BlockModel;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
	@Shadow @Final private static Logger LOGGER;

	@Shadow @Final private Map<Item, List<String>> field_11316;

	@Shadow @Final private Map<String, Identifier> field_11314;

	@Shadow @Final private Map<Identifier, BlockModel> field_11303;

	@Shadow protected abstract BlockModel getModel(Identifier id) throws IOException;

	@Inject( method = "method_12514", at = @At("TAIL") )
	public void onSetupItems(CallbackInfo ci) {
		List<Item> items = ( (FabricRegistry) LoaderComplexFabric.INSTANCE.getLoader().getRegistry() ).getRegisteredItems();
		for ( Item item : items ) {
			Identifier id = Item.REGISTRY.getIdentifier( item );
			if ( id == null )
				LOGGER.error( Utils.format( "Item {} has not been registered! H O W", item ) );
			else {
				this.field_11314.put( id.toString(), id );
				if (this.field_11303.get(id) == null) {
					Identifier identifier = new Identifier( id.getNamespace(), "item/" + id.getPath() );
					try {
						BlockModel blockModel = this.getModel(identifier);
						this.field_11303.put(identifier, blockModel);
					} catch (Exception var5) {
						LOGGER.warn( "Unable to load item model: '{}' for item: '{}'", identifier, id, var5 );
					}

				}
			}
		}
	}
}
