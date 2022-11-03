package com.enderzombi102.loadercomplex.minecraft.util;

/**
 * Represents the result of any in-game action.
 */
public enum ActionResult {
	/** Symbolize that the action was performed, chain stops here. */
	SUCCESS,
	/** Nothing happens, but the chain ends here. */
	CONSUME,
	/** Nothing happens, passing on the even in the chain. */
	PASS,
	/** Something didn't work well, the chain ends here and error is reported ( usually ). */
	FAIL
}
