package lctest.item;

import com.enderzombi102.loadercomplex.api.math.Vec3d;
import com.enderzombi102.loadercomplex.api.math.Vec3i;
import com.enderzombi102.loadercomplex.api.minecraft.entity.PlayerEntity;
import com.enderzombi102.loadercomplex.api.minecraft.item.Item;
import com.enderzombi102.loadercomplex.api.minecraft.item.ItemStack;
import com.enderzombi102.loadercomplex.api.minecraft.util.ActionResult;
import com.enderzombi102.loadercomplex.api.minecraft.util.ActionResultHolder;
import com.enderzombi102.loadercomplex.api.minecraft.util.UseAction;
import com.enderzombi102.loadercomplex.api.minecraft.world.World;

public class TeleportScroll extends Item {
	public TeleportScroll() {
		this.maxCount = 1;
		this.maxDamage = 20;
		this.useAction = UseAction.NONE;
	}

	@Override
	public ActionResultHolder<ItemStack> startUsing( World world, PlayerEntity player, ItemStack stack ) {
		Vec3d og = player.getPosition();
		float pitch = player.getPitch();
		float yaw = player.getYaw();

		Vec3d dir = Vec3d.fromPolar( pitch, yaw );
		Vec3d dest = og.add( dir.multiply( 10 ) );
		Vec3i destBlock = dest.toVec3i();

		if ( world.getBlockState( destBlock ).isAir() && world.getBlockState( destBlock.up() ).isAir() ) {
			if (! world.isClient() ) {
				player.setPosition( dest );
			}
//			player.setActiveHand( hand );
			stack.setDamage( stack.getDamage() - 1 );
			return ActionResultHolder.of( ActionResult.SUCCESS, stack );
		}

		return ActionResultHolder.of( ActionResult.PASS, stack );
	}
}
