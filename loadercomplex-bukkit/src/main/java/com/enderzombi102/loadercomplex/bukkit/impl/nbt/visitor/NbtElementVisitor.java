package com.enderzombi102.loadercomplex.bukkit.impl.nbt.visitor;

import com.enderzombi102.loadercomplex.bukkit.impl.nbt.NbtByte;
import com.enderzombi102.loadercomplex.bukkit.impl.nbt.NbtByteArray;
import com.enderzombi102.loadercomplex.bukkit.impl.nbt.NbtCompound;
import com.enderzombi102.loadercomplex.bukkit.impl.nbt.NbtDouble;
import com.enderzombi102.loadercomplex.bukkit.impl.nbt.NbtFloat;
import com.enderzombi102.loadercomplex.bukkit.impl.nbt.NbtInt;
import com.enderzombi102.loadercomplex.bukkit.impl.nbt.NbtIntArray;
import com.enderzombi102.loadercomplex.bukkit.impl.nbt.NbtList;
import com.enderzombi102.loadercomplex.bukkit.impl.nbt.NbtLong;
import com.enderzombi102.loadercomplex.bukkit.impl.nbt.NbtLongArray;
import com.enderzombi102.loadercomplex.bukkit.impl.nbt.NbtNull;
import com.enderzombi102.loadercomplex.bukkit.impl.nbt.NbtShort;
import com.enderzombi102.loadercomplex.bukkit.impl.nbt.NbtString;

/**
 * A visitor interface for NBT elements.
 */
public interface NbtElementVisitor {
	void visitString(NbtString element);

	void visitByte(NbtByte element);

	void visitShort(NbtShort element);

	void visitInt(NbtInt element);

	void visitLong(NbtLong element);

	void visitFloat(NbtFloat element);

	void visitDouble(NbtDouble element);

	void visitByteArray(NbtByteArray element);

	void visitIntArray(NbtIntArray element);

	void visitLongArray(NbtLongArray element);

	void visitList(NbtList element);

	void visitCompound(NbtCompound compound);

	void visitNull(NbtNull element);
}
