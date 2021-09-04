package com.enderzombi102.loadercomplex.bukkit.impl.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import com.enderzombi102.loadercomplex.bukkit.impl.nbt.visitor.NbtElementVisitor;

/**
 * Represents an NBT 64-bit integer.
 */
public class NbtLong extends AbstractNbtNumber {
	public static final NbtType<NbtLong> TYPE = new NbtType<NbtLong>() {
		public NbtLong read(DataInput dataInput, int i, NbtTagSizeTracker nbtTagSizeTracker) throws IOException {
			nbtTagSizeTracker.add(128L);
			return NbtLong.of(dataInput.readLong());
		}

		public String getCrashReportName() {
			return "LONG";
		}

		public String getCommandFeedbackName() {
			return "TAG_Long";
		}

		public boolean isImmutable() {
			return true;
		}
	};
	private final long value;

	NbtLong(long l) {
		this.value = l;
	}

	public static NbtLong of(long value) {
		return value >= -128L && value <= 1024L ? NbtLong.Cache.VALUES[(int)value - -128] : new NbtLong(value);
	}

	public void write(DataOutput output) throws IOException {
		output.writeLong(this.value);
	}

	public byte getType() {
		return 4;
	}

	public NbtType<NbtLong> getNbtType() {
		return TYPE;
	}

	public NbtLong copy() {
		return this;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else {
			return o instanceof NbtLong && this.value == ((NbtLong)o).value;
		}
	}

	public int hashCode() {
		return (int)(this.value ^ this.value >>> 32);
	}

	public void accept(NbtElementVisitor visitor) {
		visitor.visitLong(this);
	}

	public long longValue() {
		return this.value;
	}

	public int intValue() {
		return (int)(this.value & -1L);
	}

	public short shortValue() {
		return (short)((int)(this.value & 65535L));
	}

	public byte byteValue() {
		return (byte)((int)(this.value & 255L));
	}

	public double doubleValue() {
		return (double)this.value;
	}

	public float floatValue() {
		return (float)this.value;
	}

	public Number numberValue() {
		return this.value;
	}

	static class Cache {
		static final NbtLong[] VALUES = new NbtLong[1153];

		private Cache() {
		}

		static {
			for(int i = 0; i < VALUES.length; ++i) {
				VALUES[i] = new NbtLong(-128 + i);
			}

		}
	}
}
