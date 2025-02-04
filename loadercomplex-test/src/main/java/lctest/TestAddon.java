package lctest;

import com.enderzombi102.loadercomplex.api.AddonContext;
import com.enderzombi102.loadercomplex.api.addon.Addon;
import com.enderzombi102.loadercomplex.api.annotation.Instance;
import com.enderzombi102.loadercomplex.api.minecraft.command.CommandManager;
import com.enderzombi102.loadercomplex.api.minecraft.item.Item;
import com.enderzombi102.loadercomplex.api.minecraft.keybind.KeybindManager;
import com.enderzombi102.loadercomplex.api.minecraft.network.NetworkManager;
import com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier;
import lctest.block.FoodBank;
import lctest.item.TeleportScroll;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier.ri;


public class TestAddon implements Addon {
	@Instance
	private static final TestAddon INSTANCE = new TestAddon();
	public static final String ID = "testaddon";

	@Override
	public void init( AddonContext ctx ) {
		Logger logger = ctx.getLogger( ID );
		try {
			Config.init( ctx.getConfigDir() );
		} catch ( IOException e ) {
			logger.info( "[LC|TA] TestAddon initialized! lets hope everything works as intended!" );
			throw new RuntimeException( e );
		}

		if ( Config.get().item ) {
			Item teleportScroll = new TeleportScroll();
			teleportScroll.creativeTab = ri( "creativetab.misc" );
			ctx.getRegistry().register( teleportScroll, getId( "teleport_scroll" ) );
		}
		if ( Config.get().itemGroup ) {
			ctx.getRegistry().register( new FoodBank( ctx ), getId( "food_bank" ), ri( "creativetab.misc" ) );
		}
		if ( Config.get().block ) {
			ctx.getRegistry().register( new FoodBank( ctx ), getId( "food_bank" ), ri( "creativetab.misc" ) );
		}
		if ( Config.get().command ) {
			CommandManager manager = ctx.getCommandManager();
		}
		if ( Config.get().keybind ) {
			KeybindManager manager = ctx.getKeybindManager();
		}
		if ( Config.get().network ) {
			NetworkManager manager = ctx.getNetworkManager();
		}
		if ( Config.get().entity ) {
//			ctx.getRegistry().register(  );
		}

		logger.info( "[LC|TA] TestAddon initialized! lets hope everything works as intended!" );
	}

	@Override
	public @NotNull String getAuthors() {
		return "ENDERZOMBI102";
	}

	@Override
	public @Nullable String getName() {
		return "TestAddon";
	}

	@Override
	public @NotNull String getDescription() {
		return "My super awesome test addon!";
	}

	@Override
	public @Nullable String getIconPath() {
		return "assets/testaddon/icon.png";
	}

	@Override
	public @NotNull Map<String, String> getLinks() {
		return new HashMap<String, String>() {{
			put( "source", "https://github.com/ENDERZOMBI102/LoaderComplex" );
		}};
	}

	public static ResourceIdentifier getId( String path ) {
		return new ResourceIdentifier( ID, path );
	}
}
