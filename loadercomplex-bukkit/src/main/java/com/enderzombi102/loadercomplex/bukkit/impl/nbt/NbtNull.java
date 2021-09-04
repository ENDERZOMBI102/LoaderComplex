package com.enderzombi102.loadercomplex.bukkit.impl.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import com.enderzombi102.loadercomplex.bukkit.impl.nbt.visitor.NbtElementVisitor;

/**
 * Represents the NBT null value.
 * Defines the end of an NBT compound object,
 * represents nonexistent values in an NBT compound object,
 * and is the type of empty NBT lists.
 */
public class NbtNull implements NbtElement {
	public static final NbtType<NbtNull> TYPE = new NbtType<NbtNull>() {
		public NbtNull read(DataInput dataInput, int i, NbtTagSizeTracker nbtTagSizeTracker) {
			nbtTagSizeTracker.add(64L);
			return NbtNull.INSTANCE;
		}

		public String getCrashReportName() {
			return "END";
		}

		public String getCommandFeedbackName() {
			return "TAG_End";
		}

		public boolean isImmutable() {
			return true;
		}
	};
	public static final NbtNull INSTANCE = new NbtNull();

	private NbtNull() {
	}

	public void write(DataOutput output) throws IOException {
	}

	public byte getType() {
		return 0;
	}

	public NbtType<NbtNull> getNbtType() {
		return TYPE;
	}

	public String toString() {
		return this.asString();
	}

	public NbtNull copy() {
		return this;
	}

	public void accept(NbtElementVisitor visitor) {
		visitor.visitNull(this);
	}
}
