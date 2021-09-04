package com.enderzombi102.loadercomplex.bukkit.impl.nbt;

import com.enderzombi102.loadercomplex.bukkit.impl.nbt.visitor.NbtElementVisitor;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

/**
 * Represents an NBT string.
 */
public class NbtString implements NbtElement {
	public static final NbtType<NbtString> TYPE = new NbtType<NbtString>() {
		public NbtString read(DataInput dataInput, int i, NbtTagSizeTracker nbtTagSizeTracker) throws IOException {
			nbtTagSizeTracker.add(288L);
			String string = dataInput.readUTF();
			nbtTagSizeTracker.add( 16L * string.length() );
			return NbtString.of(string);
		}

		public String getCrashReportName() {
			return "STRING";
		}

		public String getCommandFeedbackName() {
			return "TAG_String";
		}

		public boolean isImmutable() {
			return true;
		}
	};
	private static final NbtString EMPTY = new NbtString("");
	private final String value;

	private NbtString(String value) {
		Objects.requireNonNull(value, "Null string not allowed");
		this.value = value;
	}

	public static NbtString of(String value) {
		return value.isEmpty() ? EMPTY : new NbtString(value);
	}

	public void write(DataOutput output) throws IOException {
		output.writeUTF(this.value);
	}

	public byte getType() {
		return 8;
	}

	public NbtType<NbtString> getNbtType() {
		return TYPE;
	}

	public String toString() {
		return NbtElement.super.asString();
	}

	public NbtString copy() {
		return this;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else {
			return o instanceof NbtString && Objects.equals(this.value, ((NbtString)o).value);
		}
	}

	public int hashCode() {
		return this.value.hashCode();
	}

	public String asString() {
		return this.value;
	}

	public void accept(NbtElementVisitor visitor) {
		visitor.visitString(this);
	}

	public static String escape(String value) {
		StringBuilder stringBuilder = new StringBuilder(" ");
		char c = 0;

		for(int i = 0; i < value.length(); ++i) {
			char charAt = value.charAt(i);
			if (charAt == '\\') {
				stringBuilder.append('\\');
			} else if (charAt == '"' || charAt == '\'') {
				if (c == 0) {
					c = (char) ( charAt == '"' ? 39 : 34 );
				}

				if (c == charAt) {
					stringBuilder.append('\\');
				}
			}

			stringBuilder.append(charAt);
		}

		if (c == 0) {
			c = 34;
		}

		stringBuilder.setCharAt(0, c);
		stringBuilder.append(c);
		return stringBuilder.toString();
	}
}
