package com.enderzombi102.loadercomplex;

public final class Utils {
	private Utils() { }

	public static String format(String fmt, Object... objs) {
		for ( Object obj : objs ) {
			fmt = fmt.replaceFirst("\\{}", obj.toString() );
		}
		return fmt;
	}
}
