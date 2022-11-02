package com.enderzombi102.loadercomplex.api.annotation;

import com.enderzombi102.loadercomplex.api.addon.Addon;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that if placed on a static field on an {@link Addon} implementation,
 * will mark that field as implementation provider, making LoaderComplex use that instance globally.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Instance { }

