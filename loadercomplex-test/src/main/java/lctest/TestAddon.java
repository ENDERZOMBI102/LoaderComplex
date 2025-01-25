package lctest;

import com.enderzombi102.loadercomplex.api.AddonContext;
import com.enderzombi102.loadercomplex.api.addon.Addon;
import com.enderzombi102.loadercomplex.api.annotation.Instance;
import com.enderzombi102.loadercomplex.api.minecraft.block.Block;
import com.enderzombi102.loadercomplex.api.minecraft.block.Blockstate;
import com.enderzombi102.loadercomplex.api.minecraft.entity.PlayerEntity;
import com.enderzombi102.loadercomplex.api.minecraft.util.Direction;
import com.enderzombi102.loadercomplex.api.minecraft.util.Gamemode;
import com.enderzombi102.loadercomplex.api.minecraft.util.Hand;
import com.enderzombi102.loadercomplex.api.minecraft.util.Position;
import com.enderzombi102.loadercomplex.api.minecraft.world.World;
import com.enderzombi102.loadercomplex.api.platform.FactoryWorld;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import static com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier.ri;


public class TestAddon implements Addon {
	@Instance
	private static final TestAddon INSTANCE = new TestAddon();
	private static final String ID = "testaddon";

	@Override
	public void init( AddonContext ctx ) {
		ctx.getLogger( ID ).info( "[LC|TA] TestAddon initialized! lets hope everything works as intended!" );
		final Block foodBank = new Block() {
			{
				this.opaque = false;
				this.hardness = 3.0f;
			}

			@Override
			public boolean onBlockInteracted( World world, Position pos, Blockstate state, PlayerEntity player, Hand hand, Direction direction, double hitX, double hitY, double hitZ ) {
				if (! world.isClient() ) {
					if ( player.getGamemode() == Gamemode.SURVIVAL ) {
						player.sendMessage( "You feel well fed" );
						player.setFoodLevel( 20 );
						player.setSaturationLevel( 20 );
					} else {
						player.sendMessage( "You feel as nothing had changed" );
					}
				}
				return true;
			}

			@Override
			public void onBreak( World world, Position pos, Blockstate state, PlayerEntity player ) {
				FactoryWorld factory = ctx.getFactoryWorld();
				factory.createItemEntity( world, factory.createStack( ri( ID, "food_bank" ) ), pos );
			}
		};
		ctx.getRegistry().register( foodBank, ri( ID, "food_bank" ), ri( "misc" ) );
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
}
