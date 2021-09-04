package com.enderzombi102.loadercomplex.bukkit.impl.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import com.enderzombi102.loadercomplex.bukkit.impl.nbt.visitor.NbtElementVisitor;

/**
 * Represents an NBT 64-bit floating-point number.
 */
public class NbtDouble extends AbstractNbtNumber {
	public static final NbtDouble ZERO = new NbtDouble(0.0D);
	public static final NbtType<NbtDouble> TYPE = new NbtType<NbtDouble>() {
		public NbtDouble read(DataInput dataInput, int i, NbtTagSizeTracker nbtTagSizeTracker) throws IOException {
			nbtTagSizeTracker.add(128L);
			return NbtDouble.of(dataInput.readDouble());
		}

		public String getCrashReportName() {
			return "DOUBLE";
		}

		public String getCommandFeedbackName() {
			return "TAG_Double";
		}

		public boolean isImmutable() {
			return true;
		}
	};
	private final double value;

	private NbtDouble(double value) {
		this.value = value;
	}

	public static NbtDouble of(double value) {
		return value == 0.0D ? ZERO : new NbtDouble(value);
	}

	public void write(DataOutput output) throws IOException {
		output.writeDouble(this.value);
	}

	public byte getType() {
		return 6;
	}

	public NbtType<NbtDouble> getNbtType() {
		return TYPE;
	}

	public NbtDouble copy() {
		return this;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else {
			return o instanceof NbtDouble && this.value == ((NbtDouble)o).value;
		}
	}

	public int hashCode() {
		long l = Double.doubleToLongBits(this.value);
		return (int)(l ^ l >>> 32);
	}

	public void accept(NbtElementVisitor visitor) {
		visitor.visitDouble(this);
	}

	public long longValue() {
		return (long)Math.floor(this.value);
	}

	public int intValue() {
		return MathHelper.floor(this.value);
	}

	public short shortValue() {
		return (short)(MathHelper.floor(this.value) & '\uffff');
	}

	public byte byteValue() {
		return (byte)(MathHelper.floor(this.value) & 255);
	}

	public double doubleValue() {
		return this.value;
	}

	public float floatValue() {
		return (float)this.value;
	}

	public Number numberValue() {
		return this.value;
	}
}
