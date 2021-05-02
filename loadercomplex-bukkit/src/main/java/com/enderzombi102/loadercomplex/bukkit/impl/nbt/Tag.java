package com.enderzombi102.loadercomplex.bukkit.impl.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class Tag {
   public static final String[] field_30792 = new String[]{"END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]", "LONG[]"};

   abstract void write(DataOutput output) throws IOException;

   abstract void method_32150(DataInput dataInput, int i, PositionTracker positionTracker) throws IOException;

   public abstract String toString();

   public abstract byte getType();

   protected Tag() {
   }

   protected static Tag method_32149(byte b) {
      switch(b) {
      case 0:
         return new EndTag();
      case 1:
         return new ByteTag();
      case 2:
         return new ShortTag();
      case 3:
         return new IntTag();
      case 4:
         return new LongTag();
      case 5:
         return new FloatTag();
      case 6:
         return new DoubleTag();
      case 7:
         return new ByteArrayTag();
      case 8:
         return new StringTag();
      case 9:
         return new ListTag();
      case 10:
         return new CompoundTag();
      case 11:
         return new IntArrayTag();
      case 12:
         return new LongArrayTag();
      default:
         return null;
      }
   }

   public static String method_32155(int i) {
      switch(i) {
      case 0:
         return "TAG_End";
      case 1:
         return "TAG_Byte";
      case 2:
         return "TAG_Short";
      case 3:
         return "TAG_Int";
      case 4:
         return "TAG_Long";
      case 5:
         return "TAG_Float";
      case 6:
         return "TAG_Double";
      case 7:
         return "TAG_Byte_Array";
      case 8:
         return "TAG_String";
      case 9:
         return "TAG_List";
      case 10:
         return "TAG_Compound";
      case 11:
         return "TAG_Int_Array";
      case 12:
         return "TAG_Long_Array";
      case 99:
         return "Any Numeric Tag";
      default:
         return "UNKNOWN";
      }
   }

   public abstract Tag copy();

   public boolean isEmpty() {
      return false;
   }

   public boolean equals(Object object) {
      return object instanceof Tag && this.getType() == ((Tag)object).getType();
   }

   public int hashCode() {
      return this.getType();
   }

   protected String asString() {
      return this.toString();
   }
}
