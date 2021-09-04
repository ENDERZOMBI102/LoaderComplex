package com.enderzombi102.loadercomplex.bukkit.impl.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import com.enderzombi102.loadercomplex.bukkit.impl.nbt.visitor.NbtElementVisitor;

/**
 * Represents an NBT 32-bit integer.
 */
public class NbtInt extends AbstractNbtNumber {
	public static final NbtType<NbtInt> TYPE = new NbtType<NbtInt>() {
		public NbtInt read(DataInput dataInput, int i, NbtTagSizeTracker nbtTagSizeTracker) throws IOException {
			nbtTagSizeTracker.add(96L);
			return NbtInt.of(dataInput.readInt());
		}

		public String getCrashReportName() {
			return "INT";
		}

		public String getCommandFeedbackName() {
			return "TAG_Int";
		}

		public boolean isImmutable() {
			return true;
		}
	};
	private final int value;

	NbtInt(int i) {
		this.value = i;
	}

	public static NbtInt of(int value) {
		return value >= -128 && value <= 1024 ? NbtInt.Cache.VALUES[value - -128] : new NbtInt(value);
	}

	public void write(DataOutput output) throws IOException {
		output.writeInt(this.value);
	}

	public byte getType() {
		return 3;
	}

	public NbtType<NbtInt> getNbtType() {
		return TYPE;
	}

	public NbtInt copy() {
		return this;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else {
			return o instanceof NbtInt && this.value == ((NbtInt)o).value;
		}
	}

	public int hashCode() {
		return this.value;
	}

	public void accept(NbtElementVisitor visitor) {
		visitor.visitInt(this);
	}

	public long longValue() {
		return (long)this.value;
	}

	public int intValue() {
		return this.value;
	}

	public short shortValue() {
		return (short)(this.value & '\uffff');
	}

	public byte byteValue() {
		return (byte)(this.value & 255);
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
		static final NbtInt[] VALUES = new NbtInt[1153];

		private Cache() {
		}

		static {
			for(int i = 0; i < VALUES.length; ++i) {
				VALUES[i] = new NbtInt(-128 + i);
			}

		}
	}
}
