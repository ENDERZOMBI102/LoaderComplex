package com.enderzombi102.loadercomplex.bukkit.impl.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class IntArrayTag extends Tag {
	private int[] value;

	IntArrayTag() {
	}

	public IntArrayTag(int[] value) {
		this.value = value;
	}

	public IntArrayTag(List<Integer> value) {
		this(toArray(value));
	}

	private static int[] toArray(List<Integer> list) {
		int[] is = new int[list.size()];

		for(int i = 0; i < list.size(); ++i) {
			Integer integer = list.get(i);
			is[i] = integer == null ? 0 : integer;
		}

		return is;
	}

	void write(DataOutput output) throws IOException {
		output.writeInt(this.value.length);
		int[] var2 = this.value;
		int var3 = var2.length;

		for(int var4 = 0; var4 < var3; ++var4) {
			int i = var2[var4];
			output.writeInt(i);
		}

	}

	void method_32150(DataInput dataInput, int i, PositionTracker positionTracker) throws IOException {
		positionTracker.add(192L);
		int j = dataInput.readInt();
		positionTracker.add(32 * j);
		this.value = new int[j];

		for(int k = 0; k < j; ++k) {
			this.value[k] = dataInput.readInt();
		}

	}

	public byte getType() {
		return 11;
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("[I;");

		for(int i = 0; i < this.value.length; ++i) {
			if (i != 0) {
				stringBuilder.append(',');
			}

			stringBuilder.append(this.value[i]);
		}

		return stringBuilder.append(']').toString();
	}

	public IntArrayTag copy() {
		int[] is = new int[this.value.length];
		System.arraycopy(this.value, 0, is, 0, this.value.length);
		return new IntArrayTag(is);
	}

	public boolean equals(Object object) {
		return super.equals(object) && Arrays.equals(this.value, ((IntArrayTag)object).value);
	}

	public int hashCode() {
		return super.hashCode() ^ Arrays.hashCode(this.value);
	}

	public int[] getIntArray() {
		return this.value;
	}
}
