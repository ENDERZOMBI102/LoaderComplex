package com.enderzombi102.loadercomplex.api.block;

import com.enderzombi102.loadercomplex.api.utils.ResourceIdentifier;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.AvailableSince("0.1.3")
public interface Blockstate {
	ResourceIdentifier getBlockType();
}
