package com.enderzombi102.loadercomplex.impl;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility methods for calling methods without having to deal with exceptions.
 * Intended use case is calling methods with checked exceptions when it's known that for some reason it won't throw.
 * @author <a href="https://github.com/ENDERZOMBI102/enderlib">ENDERZOMBI102</a>
 * @author <a href="https://github.com/MattiDragon">MattiDragon</a>
 */
public final class SafeUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger( "EnderLib | SafeUtils" );
	private SafeUtils() { }

	/**
	 * Call the runnable and ignores any exceptions it throws. Exceptions will be printed.
	 * @param runnable The runnable to run.
	 */
	public static void doSafely( @NotNull ThrowingRunnable<?> runnable ) {
		try {
			runnable.run();
		} catch ( Throwable e ) {
			LOGGER.error( "Could not execute safely", e );
		}
	}

	/**
	 * Uses the supplier to get a value and returns it. If it throws an exception {@code null} will be returned. Exceptions will be printed.
	 * @param supplier The supplier to use.
	 */
	public static <T> T doSafely( @NotNull ThrowingSupplier<T, ?> supplier ) {
		try {
			return supplier.get();
		} catch ( Throwable e ) {
			LOGGER.error( "Could not execute safely", e );
			return null;
		}
	}

	/**
	 * Tries to get the given supplier, throwing a {@link RuntimeException} if an exception is thrown inside it.
	 * @param supplier supplier to get.
	 * @param <T> return type of the supplier.
	 * @return the supplier's returned value.
	 */
	public static <T> @NotNull T tryThrow( @NotNull ThrowingSupplier<T, ?> supplier ) {
		try {
			return supplier.get();
		} catch ( Throwable e ) {
			throw new RuntimeException( e );
		}
	}

	/**
	 * Tries to run the given runnable, throwing a {@link RuntimeException} if an exception is thrown inside it.
	 */
	public static void tryThrow( @NotNull ThrowingRunnable<?> runnable ) {
		try {
			runnable.run();
		} catch ( Throwable e ) {
			throw new RuntimeException( e );
		}
	}

	public interface ThrowingRunnable<Ex extends Throwable> {
		void run() throws Ex;
	}

	public interface ThrowingSupplier<T, Ex extends Throwable> {
		T get() throws Ex;
	}
}
