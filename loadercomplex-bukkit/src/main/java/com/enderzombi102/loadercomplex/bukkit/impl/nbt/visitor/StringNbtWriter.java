package com.enderzombi102.loadercomplex.bukkit.impl.nbt.visitor;

import com.enderzombi102.loadercomplex.bukkit.impl.nbt.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * A simple converter to turn NBT into single-line SNBT. The output may be parsed back into binary NBT.
 */
public class StringNbtWriter implements NbtElementVisitor {
	private static final Pattern SIMPLE_NAME = Pattern.compile("[A-Za-z0-9._+-]+");
	private final StringBuilder result = new StringBuilder();

	public String apply(NbtElement element) {
		element.accept(this);
		return this.result.toString();
	}

	public void visitString(NbtString element) {
		this.result.append(NbtString.escape(element.asString()));
	}

	public void visitByte(NbtByte element) {
		this.result.append(element.numberValue()).append('b');
	}

	public void visitShort(NbtShort element) {
		this.result.append(element.numberValue()).append('s');
	}

	public void visitInt(NbtInt element) {
		this.result.append(element.numberValue());
	}

	public void visitLong(NbtLong element) {
		this.result.append(element.numberValue()).append('L');
	}

	public void visitFloat(NbtFloat element) {
		this.result.append(element.floatValue()).append('f');
	}

	public void visitDouble(NbtDouble element) {
		this.result.append(element.doubleValue()).append('d');
	}

	public void visitByteArray(NbtByteArray element) {
		this.result.append("[B;");
		byte[] bs = element.getByteArray();

		for(int i = 0; i < bs.length; ++i) {
			if (i != 0) {
				this.result.append(',');
			}

			this.result.append(bs[i]).append('B');
		}

		this.result.append(']');
	}

	public void visitIntArray(NbtIntArray element) {
		this.result.append("[I;");
		int[] is = element.getIntArray();

		for(int i = 0; i < is.length; ++i) {
			if (i != 0) {
				this.result.append(',');
			}

			this.result.append(is[i]);
		}

		this.result.append(']');
	}

	public void visitLongArray(NbtLongArray element) {
		this.result.append("[L;");
		long[] ls = element.getLongArray();

		for(int i = 0; i < ls.length; ++i) {
			if (i != 0) {
				this.result.append(',');
			}

			this.result.append(ls[i]).append('L');
		}

		this.result.append(']');
	}

	public void visitList(NbtList element) {
		this.result.append('[');

		for(int i = 0; i < element.size(); ++i) {
			if (i != 0) {
				this.result.append(',');
			}

			this.result.append((new StringNbtWriter()).apply(element.get(i)));
		}

		this.result.append(']');
	}

	public void visitCompound(NbtCompound compound) {
		this.result.append('{');
		List<String> list = new ArrayList<>( compound.getKeys() );
		Collections.sort(list);

		String string;
		for(
				Iterator<String> var3 = list.iterator();
				var3.hasNext();
			//noinspection ConstantConditions
				this.result
						.append( escapeName(string) )
						.append(':')
						.append( new StringNbtWriter().apply( compound.get(string) ) )
		) {
			string = var3.next();
			if (this.result.length() != 1) {
				this.result.append(',');
			}
		}

		this.result.append('}');
	}

	protected static String escapeName(String name) {
		return SIMPLE_NAME.matcher(name).matches() ? name : NbtString.escape(name);
	}

	public void visitNull(NbtNull element) {
		this.result.append("END");
	}
}
