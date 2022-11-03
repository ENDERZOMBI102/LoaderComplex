package com.enderzombi102.loadercomplex.api.annotation;

/**
 * A simple interface that permits to inject registered stuff.
 */
public @interface Inject {
	/**
	 * Injection parameter.
	 */
	String value() default "";
}
