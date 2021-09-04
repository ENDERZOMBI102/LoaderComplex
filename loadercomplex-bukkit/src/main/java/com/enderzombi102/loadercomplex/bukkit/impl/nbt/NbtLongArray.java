package com.enderzombi102.loadercomplex.bukkit.impl.nbt;

import com.enderzombi102.loadercomplex.bukkit.impl.nbt.visitor.NbtElementVisitor;
import org.apache.commons.lang.ArrayUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Represents an NBT 64-bit integer array.
 */
public class NbtLongArray extends AbstractNbtList<NbtLong> {
	public static final NbtType<NbtLongArray> TYPE = new NbtType<NbtLongArray>() {
		public NbtLongArray read(DataInput dataInput, int i, NbtTagSizeTracker nbtTagSizeTracker) throws IOException {
			nbtTagSizeTracker.add(192L);
			int j = dataInput.readInt();
			nbtTagSizeTracker.add(64L * (long)j);
			long[] ls = new long[j];

			for(int k = 0; k < j; ++k) {
				ls[k] = dataInput.readLong();
			}

			return new NbtLongArray(ls);
		}

		public String getCrashReportName() {
			return "LONG[]";
		}

		public String getCommandFeedbackName() {
			return "TAG_Long_Array";
		}
	};
	private long[] value;

	public NbtLongArray(long[] value) {
		this.value = value;
	}

	public NbtLongArray(List<Long> value) {
		this(toArray(value));
	}

	private static long[] toArray(List<Long> list) {
		long[] ls = new long[list.size()];

		for(int i = 0; i < list.size(); ++i) {
			Long long_ = list.get(i);
			ls[i] = long_ == null ? 0L : long_;
		}

		return ls;
	}

	public void write(DataOutput output) throws IOException {
		output.writeInt(this.value.length);
		long[] var2 = this.value;
		int var3 = var2.length;

		for(int var4 = 0; var4 < var3; ++var4) {
			long l = var2[var4];
			output.writeLong(l);
		}

	}

	public byte getType() {
		return 12;
	}

	public NbtType<NbtLongArray> getNbtType() {
		return TYPE;
	}

	public String toString() {
		return this.asString();
	}

	public NbtLongArray copy() {
		long[] ls = new long[this.value.length];
		System.arraycopy(this.value, 0, ls, 0, this.value.length);
		return new NbtLongArray(ls);
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else {
			return o instanceof NbtLongArray && Arrays.equals(this.value, ((NbtLongArray)o).value);
		}
	}

	public int hashCode() {
		return Arrays.hashCode(this.value);
	}

	public void accept(NbtElementVisitor visitor) {
		visitor.visitLongArray(this);
	}

	public long[] getLongArray() {
		return this.value;
	}

	public int size() {
		return this.value.length;
	}

	public NbtLong get(int i) {
		return NbtLong.of(this.value[i]);
	}

	public NbtLong set(int i, NbtLong nbtLong) {
		long l = this.value[i];
		this.value[i] = nbtLong.longValue();
		return NbtLong.of(l);
	}

	public void add(int i, NbtLong nbtLong) {
		this.value = ArrayUtils.add(this.value, i, nbtLong.longValue());
	}

	public boolean setElement(int index, NbtElement element) {
		if (element instanceof AbstractNbtNumber) {
			this.value[index] = ((AbstractNbtNumber)element).longValue();
			return true;
		} else {
			return false;
		}
	}

	public boolean addElement(int index, NbtElement element) {
		if (element instanceof AbstractNbtNumber) {
			this.value = ArrayUtils.add(this.value, index, ((AbstractNbtNumber)element).longValue());
			return true;
		} else {
			return false;
		}
	}

	public NbtLong remove(int i) {
		long l = this.value[i];
		this.value = ArrayUtils.remove(this.value, i);
		return NbtLong.of(l);
	}

	public byte getHeldType() {
		return 4;
	}

	public void clear() {
		this.value = new long[0];
	}
}
