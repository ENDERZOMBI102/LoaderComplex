package com.enderzombi102.loadercomplex.api.utils;

import blue.endless.jankson.JsonArray;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.api.SyntaxError;
import com.enderzombi102.loadercomplex.api.annotation.Json;
import org.jetbrains.annotations.NotNull;

import static com.enderzombi102.loadercomplex.impl.Utils.JANKSON;

/**
 * An object that can be converted to a json object/array/element and to a string
 */
public class JsonConvertible {
	private final String data;

	public JsonConvertible( @Json String data ) {
		this.data = data;
	}

	public @NotNull JsonElement element() throws SyntaxError {
		return JANKSON.loadElement( this.data );
	}

	public @NotNull JsonObject object() throws SyntaxError {
		return JANKSON.load( this.data );
	}

	public @NotNull JsonArray array() throws SyntaxError {
		return JANKSON.fromJson( this.data, JsonArray.class );
	}

	public @Json String string() {
		return this.data;
	}

	@Override
	public String toString() {
		return this.data;
	}
}
