package lctest.item;

import com.enderzombi102.loadercomplex.api.math.Vec3d;
import com.enderzombi102.loadercomplex.api.math.Vec3i;
import com.enderzombi102.loadercomplex.api.minecraft.entity.LivingEntity;
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
		this.maxUseTime = 20;  // 20 ticks to use
		this.useAction = UseAction.BOW;
	}

	@Override
	public ActionResultHolder<ItemStack> startUsing( World world, PlayerEntity player, ItemStack stack ) {
//		player.setActiveHand( hand );
		return ActionResultHolder.of( ActionResult.SUCCESS, stack );
	}

	@Override
	public ItemStack finishUsing( ItemStack stack, World world, LivingEntity user ) {
		Vec3d og = user.getPosition();
		float pitch = user.getPitch();
		float yaw = user.getYaw();

		// FIXME: this is wrong
		Vec3d dir = Vec3d.fromPolar( pitch, yaw );
		Vec3d dest = og.add( dir.multiply( 10, 10, 10 ) );
		Vec3i destBlock = dest.toVec3i();

		if ( world.getBlockState( destBlock ).isAir() && world.getBlockState( destBlock.up() ).isAir() ) {
			if (! world.isClient() ) {
				user.setPosition( dest );
			}
		}

		return stack;
	}
}
