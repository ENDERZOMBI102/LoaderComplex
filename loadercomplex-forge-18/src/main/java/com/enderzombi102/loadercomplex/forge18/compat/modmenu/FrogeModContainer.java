package com.enderzombi102.loadercomplex.forge18.compat.modmenu;

import com.enderzombi102.loadercomplex.addonloader.AddonContainerImpl;
import net.minecraftforge.fml.ModContainer;

import java.util.Objects;

public class FrogeModContainer extends ModContainer {
	private final AddonContainerImpl container;

	public FrogeModContainer( AddonContainerImpl container) {
		super( new FrogeModInfo( container, null ) );
		this.container = container;
	}

	@Override
	public boolean matches(Object mod) {
		return Objects.equals( mod, this.getMod() );
	}

	@Override
	public Object getMod() {
		return this.container.getImplementation();
	}
}
