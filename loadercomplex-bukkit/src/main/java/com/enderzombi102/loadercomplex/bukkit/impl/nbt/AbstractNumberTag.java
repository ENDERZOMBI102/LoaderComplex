package com.enderzombi102.loadercomplex.bukkit.impl.nbt;

abstract class AbstractNumberTag extends Tag {
	protected AbstractNumberTag() {
	}

	public abstract long getLong();

	public abstract int getInt();

	public abstract short getShort();

	public abstract byte getByte();

	public abstract double getDouble();

	public abstract float getFloat();
}
