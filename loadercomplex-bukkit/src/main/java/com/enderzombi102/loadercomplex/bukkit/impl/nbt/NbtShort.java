package com.enderzombi102.loadercomplex.bukkit.impl.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import com.enderzombi102.loadercomplex.bukkit.impl.nbt.visitor.NbtElementVisitor;

/**
 * Represents an NBT 16-bit integer.
 */
public class NbtShort extends AbstractNbtNumber {
	public static final NbtType<NbtShort> TYPE = new NbtType<NbtShort>() {
		public NbtShort read(DataInput dataInput, int i, NbtTagSizeTracker nbtTagSizeTracker) throws IOException {
			nbtTagSizeTracker.add(80L);
			return NbtShort.of(dataInput.readShort());
		}

		public String getCrashReportName() {
			return "SHORT";
		}

		public String getCommandFeedbackName() {
			return "TAG_Short";
		}

		public boolean isImmutable() {
			return true;
		}
	};
	private final short value;

	NbtShort(short s) {
		this.value = s;
	}

	public static NbtShort of(short value) {
		return value >= -128 && value <= 1024 ? NbtShort.Cache.VALUES[value - -128] : new NbtShort(value);
	}

	public void write(DataOutput output) throws IOException {
		output.writeShort(this.value);
	}

	public byte getType() {
		return 2;
	}

	public NbtType<NbtShort> getNbtType() {
		return TYPE;
	}

	public NbtShort copy() {
		return this;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else {
			return o instanceof NbtShort && this.value == ((NbtShort)o).value;
		}
	}

	public int hashCode() {
		return this.value;
	}

	public void accept(NbtElementVisitor visitor) {
		visitor.visitShort(this);
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
		return (byte) ( this.value & 255 );
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

	static class Cache {
		static final NbtShort[] VALUES = new NbtShort[1153];

		private Cache() {
		}

		static {
			for(int i = 0; i < VALUES.length; ++i) {
				VALUES[i] = new NbtShort( (short) (-128 + i) );
			}

		}
	}
}
