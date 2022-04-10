package com.enderzombi102.loadercomplex.fabric12.mixin;

import com.enderzombi102.loadercomplex.fabric12.LoaderComplexFabric;
import com.enderzombi102.loadercomplex.fabric12.impl.FabricRegistry;
import com.enderzombi102.loadercomplex.Utils;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.BlockModel;
import net.minecraft.client.render.model.json.ItemModelGenerator;
import net.minecraft.client.render.model.json.ModelVariantMap;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
	@Shadow @Final private static Logger LOGGER;

	@Shadow @Final private Map<Item, List<String>> field_11316;

	@Shadow @Final private Map<String, Identifier> field_11314;

	@Shadow @Final private Map<Identifier, BlockModel> field_11303;

	@Shadow protected abstract BlockModel getModel(Identifier id) throws IOException;

	@Shadow @Final private ItemModelGenerator field_11308;

	@Shadow @Final private Map<Identifier, ModelVariantMap> field_11315;

	@Shadow @Final private ResourceManager resourceManager;

	@Shadow protected abstract ModelVariantMap method_12508(Identifier identifier);

	@Shadow protected abstract ModelVariantMap method_12510(Identifier identifier, Identifier identifier2);

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
				// FIXME: This crashes somehow
//				if ( item instanceof BlockItem)
//					this.field_11315.put( id, this.method_12508( id ) );
//				else {
//					id = new Identifier( id.getNamespace(), "item/" + id.getPath() + ".json" );
//					this.field_11315.put(id, this.method_12510(id, id));
//				}
				this.field_11316.computeIfAbsent( item, key -> new ArrayList<>() );
				this.field_11316.get( item ).add( id.toString() );
			}
		}
	}
}
