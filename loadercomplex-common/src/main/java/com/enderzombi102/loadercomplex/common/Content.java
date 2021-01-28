package com.enderzombi102.loadercomplex.common;

import com.enderzombi102.loadercomplex.common.abstraction.Loader;
import com.enderzombi102.loadercomplex.common.abstraction.block.Block;
import com.enderzombi102.loadercomplex.common.abstraction.utils.Identifer;

public class Content {

	public static Content INSTANCE;
	public final Loader loader;
	public static final String NAMESPACE = "loadercomplex";

	public Content(Loader loader) {
		INSTANCE = this;
		this.loader = loader;
		this.initStuff();
	}

	public void initStuff() {
		Block block = this.loader.getRegistry().createBlock();
		block.setIdentifier( new Identifer( NAMESPACE, "test") );
		this.loader.getRegistry().register( block );
	}

}