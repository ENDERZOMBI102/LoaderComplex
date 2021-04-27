package com.enderzombi102.loadercomplex.testmod;


import com.enderzombi102.loadercomplex.abstraction.ContentMod;
import com.enderzombi102.loadercomplex.abstraction.Loader;
import com.enderzombi102.loadercomplex.abstraction.block.Block;
import com.enderzombi102.loadercomplex.abstraction.utils.ResourceIdentifier;

public class TestMod implements ContentMod {
	public static final String MOD_ID = "testmod";

	@Override
	public void init(Loader loader) {
		loader.getRegistry().register(
				new TestBlock(),
				new ResourceIdentifier( MOD_ID, "testblock" )
		);
	}


	static class TestBlock extends Block {

		@Override
		public boolean OnBlockInteracted(Object player) {
			System.out.println("Interacted with testblock!");
			return true;
		}
	}
}
