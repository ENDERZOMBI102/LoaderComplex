package com.enderzombi102.loadercomplex.testmod;


import com.enderzombi102.loadercomplex.abstraction.ContentMod;
import com.enderzombi102.loadercomplex.abstraction.Loader;
import com.enderzombi102.loadercomplex.abstraction.block.Block;
import com.enderzombi102.loadercomplex.abstraction.item.Item;
import com.enderzombi102.loadercomplex.abstraction.item.ItemStack;
import com.enderzombi102.loadercomplex.abstraction.utils.ActionResult;
import com.enderzombi102.loadercomplex.abstraction.utils.Hand;
import com.enderzombi102.loadercomplex.abstraction.utils.ResourceIdentifier;

public class TestMod implements ContentMod {
	public static final String MOD_ID = "testmod";

	@Override
	public void init(Loader loader) {
		loader.getRegistry().register(
				new TestBlock(),
				new ResourceIdentifier( MOD_ID, "testblock" )
		);
		loader.getRegistry().register(
				new TestItem(),
				new ResourceIdentifier( MOD_ID, "testitem" )
		);
		loader.getLogger(MOD_ID).info(
				"LoaderComplex on: " +
				loader.getLoaderType() +
				" v" +
				loader.getLoaderVersion() +
				" on minecraft " +
				loader.getMinecraftVersion()
		);
	}


	static class TestBlock extends Block {

		@Override
		public boolean OnBlockInteracted(Object player, boolean isClient) {
			if (! isClient ) {
				System.out.println("Block interacted!");
			}
			return true;
		}

		@Override
		public void OnBreak(Object player, boolean isClient) {
			if (! isClient ) {
				System.out.println("Block broken!");
			}
		}

		@Override
		public void OnEntityCollision(Object entity, boolean isClient) {
			if (! isClient ) {
				System.out.println("Block collided with entity!");
			}
		}

		@Override
		public void OnSteppedOn(Object entity, boolean isClient) {
			if (! isClient ) {
				System.out.println("Block stepped on!");
			}
		}
	}

	static class TestItem extends Item {
		@Override
		public void onStoppedUsing(ItemStack stack, Object user, int remainingUseTicks) {
			System.out.println("Item stopped using!");
		}

		@Override
		public ActionResult useOnBlock() {
			System.out.println("Item used on block!");
			return ActionResult.SUCCESS;
		}

		@Override
		public boolean useOnEntity(ItemStack stack, Object user, Object entity, Hand hand) {
			System.out.println("Item used on entity!");
			return true;
		}

		@Override
		public ActionResult use() {
			System.out.println("Item used!");
			return ActionResult.SUCCESS;
		}

	}
}
