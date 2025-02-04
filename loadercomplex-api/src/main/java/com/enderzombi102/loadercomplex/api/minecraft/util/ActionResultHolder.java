package com.enderzombi102.loadercomplex.api.minecraft.util;

/**
 * Represents the result of any in-game action which modifies an object.
 */
public class ActionResultHolder<Result> {
	private final ActionResult result;
	private final Result object;

	public ActionResultHolder( ActionResult result, Result object ) {
		this.result = result;
		this.object = object;
	}

	public ActionResult getResult() {
		return this.result;
	}

	public Result getObject() {
		return this.object;
	}

	/**
	 * Same thing as `new`.
	 * @param result the action's result.
	 * @param object the final object to turn back.
	 */
	public static <Result> ActionResultHolder<Result> of( ActionResult result, Result object ) {
		return new ActionResultHolder<>( result, object );
	}

	/**
	 * Returns an {@link ActionResultHolder} of {@link ActionResult#PASS}.
	 * @param object object to pass.
	 */
	public static <Result> ActionResultHolder<Result> pass( Result object ) {
		return new ActionResultHolder<>( ActionResult.PASS, object );
	}
}
