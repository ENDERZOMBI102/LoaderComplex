package com.enderzombi102.loadercomplex.bukkit.impl.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import com.enderzombi102.loadercomplex.bukkit.impl.nbt.visitor.NbtElementVisitor;
import org.apache.commons.lang.ArrayUtils;

/**
 * Represents an NBT byte array.
 */
public class NbtByteArray extends AbstractNbtList<NbtByte> {
	public static final NbtType<NbtByteArray> TYPE = new NbtType<NbtByteArray>() {
		public NbtByteArray read(DataInput dataInput, int i, NbtTagSizeTracker nbtTagSizeTracker) throws IOException {
			nbtTagSizeTracker.add(192L);
			int j = dataInput.readInt();
			nbtTagSizeTracker.add(8L * (long)j);
			byte[] bs = new byte[j];
			dataInput.readFully(bs);
			return new NbtByteArray(bs);
		}

		public String getCrashReportName() {
			return "BYTE[]";
		}

		public String getCommandFeedbackName() {
			return "TAG_Byte_Array";
		}
	};
	private byte[] value;

	public NbtByteArray(byte[] value) {
		this.value = value;
	}

	public NbtByteArray(List<Byte> value) {
		this(toArray(value));
	}

	private static byte[] toArray(List<Byte> list) {
		byte[] bs = new byte[list.size()];

		for(int i = 0; i < list.size(); ++i) {
			Byte byte_ = list.get(i);
			bs[i] = byte_ == null ? 0 : byte_;
		}

		return bs;
	}

	public void write(DataOutput output) throws IOException {
		output.writeInt(this.value.length);
		output.write(this.value);
	}

	public byte getType() {
		return 7;
	}

	public NbtType<NbtByteArray> getNbtType() {
		return TYPE;
	}

	public String toString() {
		return this.asString();
	}

	public NbtElement copy() {
		byte[] bs = new byte[this.value.length];
		System.arraycopy(this.value, 0, bs, 0, this.value.length);
		return new NbtByteArray(bs);
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else {
			return o instanceof NbtByteArray && Arrays.equals(this.value, ((NbtByteArray)o).value);
		}
	}

	public int hashCode() {
		return Arrays.hashCode(this.value);
	}

	public void accept(NbtElementVisitor visitor) {
		visitor.visitByteArray(this);
	}

	public byte[] getByteArray() {
		return this.value;
	}

	public int size() {
		return this.value.length;
	}

	public NbtByte get(int i) {
		return NbtByte.of(this.value[i]);
	}

	public NbtByte set(int i, NbtByte nbtByte) {
		byte b = this.value[i];
		this.value[i] = nbtByte.byteValue();
		return NbtByte.of(b);
	}

	public void add(int i, NbtByte nbtByte) {
		this.value = ArrayUtils.add( this.value, i, nbtByte.byteValue() );
	}

	public boolean setElement(int index, NbtElement element) {
		if (element instanceof AbstractNbtNumber) {
			this.value[index] = ((AbstractNbtNumber)element).byteValue();
			return true;
		} else {
			return false;
		}
	}

	public boolean addElement(int index, NbtElement element) {
		if (element instanceof AbstractNbtNumber) {
			this.value = ArrayUtils.add(this.value, index, ((AbstractNbtNumber)element).byteValue());
			return true;
		} else {
			return false;
		}
	}

	public NbtByte remove(int i) {
		byte b = this.value[i];
		this.value = ArrayUtils.remove(this.value, i);
		return NbtByte.of(b);
	}

	public byte getHeldType() {
		return 1;
	}

	public void clear() {
		this.value = new byte[0];
	}
}
