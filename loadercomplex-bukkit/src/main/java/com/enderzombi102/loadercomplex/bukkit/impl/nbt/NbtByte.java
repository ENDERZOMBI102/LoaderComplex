package com.enderzombi102.loadercomplex.bukkit.impl.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import com.enderzombi102.loadercomplex.bukkit.impl.nbt.visitor.NbtElementVisitor;

/**
 * Represents an NBT byte.
 */
public class NbtByte extends AbstractNbtNumber {
	public static final NbtType<NbtByte> TYPE = new NbtType<NbtByte>() {
		public NbtByte read(DataInput dataInput, int i, NbtTagSizeTracker nbtTagSizeTracker) throws IOException {
			nbtTagSizeTracker.add(72L);
			return NbtByte.of(dataInput.readByte());
		}

		public String getCrashReportName() {
			return "BYTE";
		}

		public String getCommandFeedbackName() {
			return "TAG_Byte";
		}

		public boolean isImmutable() {
			return true;
		}
	};
	public static final NbtByte ZERO = of((byte)0);
	public static final NbtByte ONE = of((byte)1);
	private final byte value;

	NbtByte(byte value) {
		this.value = value;
	}

	public static NbtByte of(byte value) {
		return NbtByte.Cache.VALUES[128 + value];
	}

	public static NbtByte of(boolean value) {
		return value ? ONE : ZERO;
	}

	public void write(DataOutput output) throws IOException {
		output.writeByte(this.value);
	}

	public byte getType() {
		return 1;
	}

	public NbtType<NbtByte> getNbtType() {
		return TYPE;
	}

	public NbtByte copy() {
		return this;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else {
			return o instanceof NbtByte && this.value == ((NbtByte)o).value;
		}
	}

	public int hashCode() {
		return this.value;
	}

	public void accept(NbtElementVisitor visitor) {
		visitor.visitByte(this);
	}

	public long longValue() {
		return this.value;
	}

	public int intValue() {
		return this.value;
	}

	public short shortValue() {
		return this.value;
	}

	public byte byteValue() {
		return this.value;
	}

	public double doubleValue() {
		return this.value;
	}

	public float floatValue() {
		return this.value;
	}

	public Number numberValue() {
		return this.value;
	}

	private static class Cache {
		static final NbtByte[] VALUES = new NbtByte[256];

		static {
			for(int i = 0; i < VALUES.length; ++i) {
				VALUES[i] = new NbtByte((byte)(i - 128));
			}

		}
	}
}
