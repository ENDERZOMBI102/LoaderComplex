package lctest.block;

import com.enderzombi102.loadercomplex.api.AddonContext;
import com.enderzombi102.loadercomplex.api.math.Direction;
import com.enderzombi102.loadercomplex.api.math.Vec3i;
import com.enderzombi102.loadercomplex.api.minecraft.block.Block;
import com.enderzombi102.loadercomplex.api.minecraft.block.BlockState;
import com.enderzombi102.loadercomplex.api.minecraft.entity.PlayerEntity;
import com.enderzombi102.loadercomplex.api.minecraft.util.*;
import com.enderzombi102.loadercomplex.api.minecraft.world.World;
import com.enderzombi102.loadercomplex.api.platform.FactoryWorld;
import org.jetbrains.annotations.NotNull;

import static com.enderzombi102.loadercomplex.api.minecraft.util.ResourceIdentifier.ri;
import static lctest.TestAddon.ID;

public class FoodBank extends Block {
	private final @NotNull FactoryWorld factoryWorld;

	public FoodBank( AddonContext ctx ) {
		this.factoryWorld = ctx.getFactoryWorld();
		this.opaque = false;
		this.hardness = 3.0f;
	}

	@Override
	public ActionResult onBlockInteracted( World world, Vec3i pos, BlockState state, PlayerEntity player, Hand hand, Direction direction, double hitX, double hitY, double hitZ ) {
		if ( !world.isClient() ) {
			if ( player.getGamemode() == Gamemode.SURVIVAL ) {
				player.sendMessage( "You feel well fed" );
				player.setFoodLevel( 20 );
				player.setSaturationLevel( 20 );
			} else {
				player.sendMessage( "You feel as nothing had changed" );
			}
		}
		return ActionResult.SUCCESS;
	}

	@Override
	public void onBreak( World world, Vec3i pos, BlockState state, PlayerEntity player ) {
		this.factoryWorld.createItemEntity( world, this.factoryWorld.createStack( ri( ID, "food_bank" ) ), pos.toVec3d() );
	}
}