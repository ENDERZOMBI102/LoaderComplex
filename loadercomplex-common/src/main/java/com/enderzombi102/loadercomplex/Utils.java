package com.enderzombi102.loadercomplex;

import com.enderzombi102.loadercomplex.api.utils.Version;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalField;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public final class Utils {
	private Utils() { }

	public static String format(String fmt, Object... objs) {
		for ( Object obj : objs ) {
			fmt = fmt.replaceFirst("\\{}", obj.toString() );
		}
		return fmt;
	}

	public static Version getApiVersion( boolean isDevEnv ) {
		if ( isDevEnv )
			return new Version(
				"1.0.0",
				LocalDateTime.now().format( DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss") )
			);
		// TODO: only works on prod?
		try {
			final Attributes fest = new Manifest(
					Utils.class.getResourceAsStream("META-INF/MANIFEST.MF")
			).getMainAttributes();
			return new Version(
					fest.getValue("ApiVersion"),
					fest.getValue("Implementation-Timestamp")
			);
		} catch (IOException ignored) {
			throw new IllegalStateException("Failed to read manifest from LoaderComplex's jar");
		}
	}
}
