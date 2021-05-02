package com.enderzombi102.loadercomplex.bukkit.impl.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class EndTag extends Tag {
	EndTag() {
	}

	void method_32150(DataInput dataInput, int i, PositionTracker positionTracker) throws IOException {
		positionTracker.add(64L);
	}

	void write(DataOutput output) throws IOException {
	}

	public byte getType() {
		return 0;
	}

	public String toString() {
		return "END";
	}

	public EndTag copy() {
		return new EndTag();
	}
}
