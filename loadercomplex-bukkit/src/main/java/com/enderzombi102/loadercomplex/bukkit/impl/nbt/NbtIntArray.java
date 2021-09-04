package com.enderzombi102.loadercomplex.bukkit.impl.nbt;

import com.enderzombi102.loadercomplex.bukkit.impl.nbt.visitor.NbtElementVisitor;
import org.apache.commons.lang.ArrayUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Represents an NBT 32-bit integer array.
 */
public class NbtIntArray extends AbstractNbtList<NbtInt> {
	public static final NbtType<NbtIntArray> TYPE = new NbtType<NbtIntArray>() {
		public NbtIntArray read(DataInput dataInput, int i, NbtTagSizeTracker nbtTagSizeTracker) throws IOException {
			nbtTagSizeTracker.add(192L);
			int j = dataInput.readInt();
			nbtTagSizeTracker.add(32L * (long)j);
			int[] is = new int[j];

			for(int k = 0; k < j; ++k) {
				is[k] = dataInput.readInt();
			}

			return new NbtIntArray(is);
		}

		public String getCrashReportName() {
			return "INT[]";
		}

		public String getCommandFeedbackName() {
			return "TAG_Int_Array";
		}
	};
	private int[] value;

	public NbtIntArray(int[] value) {
		this.value = value;
	}

	public NbtIntArray(List<Integer> value) {
		this(toArray(value));
	}

	private static int[] toArray(List<Integer> list) {
		int[] is = new int[list.size()];

		for(int i = 0; i < list.size(); ++i) {
			Integer integer = (Integer)list.get(i);
			is[i] = integer == null ? 0 : integer;
		}

		return is;
	}

	public void write(DataOutput output) throws IOException {
		output.writeInt(this.value.length);
		int[] var2 = this.value;
		int var3 = var2.length;

		for(int var4 = 0; var4 < var3; ++var4) {
			int i = var2[var4];
			output.writeInt(i);
		}

	}

	public byte getType() {
		return 11;
	}

	public NbtType<NbtIntArray> getNbtType() {
		return TYPE;
	}

	public String toString() {
		return this.asString();
	}

	public NbtIntArray copy() {
		int[] is = new int[this.value.length];
		System.arraycopy(this.value, 0, is, 0, this.value.length);
		return new NbtIntArray(is);
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else {
			return o instanceof NbtIntArray && Arrays.equals(this.value, ((NbtIntArray)o).value);
		}
	}

	public int hashCode() {
		return Arrays.hashCode(this.value);
	}

	public int[] getIntArray() {
		return this.value;
	}

	public void accept(NbtElementVisitor visitor) {
		visitor.visitIntArray(this);
	}

	public int size() {
		return this.value.length;
	}

	public NbtInt get(int i) {
		return NbtInt.of(this.value[i]);
	}

	public NbtInt set(int i, NbtInt nbtInt) {
		int j = this.value[i];
		this.value[i] = nbtInt.intValue();
		return NbtInt.of(j);
	}

	public void add(int i, NbtInt nbtInt) {
		this.value = ArrayUtils.add(this.value, i, nbtInt.intValue());
	}

	public boolean setElement(int index, NbtElement element) {
		if (element instanceof AbstractNbtNumber) {
			this.value[index] = ((AbstractNbtNumber)element).intValue();
			return true;
		} else {
			return false;
		}
	}

	public boolean addElement(int index, NbtElement element) {
		if (element instanceof AbstractNbtNumber) {
			this.value = ArrayUtils.add(this.value, index, ((AbstractNbtNumber)element).intValue());
			return true;
		} else {
			return false;
		}
	}

	public NbtInt remove(int i) {
		int j = this.value[i];
		this.value = ArrayUtils.remove(this.value, i);
		return NbtInt.of(j);
	}

	public byte getHeldType() {
		return 3;
	}

	public void clear() {
		this.value = new int[0];
	}
}
