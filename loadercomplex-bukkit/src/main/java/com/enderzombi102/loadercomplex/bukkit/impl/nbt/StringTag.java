package com.enderzombi102.loadercomplex.bukkit.impl.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

public class StringTag extends Tag {
   private String field_30791;

   public StringTag() {
      this("");
   }

   public StringTag(String value) {
      Objects.requireNonNull(value, "Null string not allowed");
      this.field_30791 = value;
   }

   void write(DataOutput output) throws IOException {
      output.writeUTF(this.field_30791);
   }

   void method_32150(DataInput dataInput, int i, PositionTracker positionTracker) throws IOException {
      positionTracker.add(288L);
      this.field_30791 = dataInput.readUTF();
      positionTracker.add((long)(16 * this.field_30791.length()));
   }

   public byte getType() {
      return 8;
   }

   public String toString() {
      return method_32146(this.field_30791);
   }

   public StringTag copy() {
      return new StringTag(this.field_30791);
   }

   public boolean isEmpty() {
      return this.field_30791.isEmpty();
   }

   public boolean equals(Object object) {
      if (!super.equals(object)) {
         return false;
      } else {
         StringTag stringTag = (StringTag)object;
         return this.field_30791 == null && stringTag.field_30791 == null || Objects.equals(this.field_30791, stringTag.field_30791);
      }
   }

   public int hashCode() {
      return super.hashCode() ^ this.field_30791.hashCode();
   }

   public String asString() {
      return this.field_30791;
   }

   public static String method_32146(String string) {
      StringBuilder stringBuilder = new StringBuilder("\"");

      for(int i = 0; i < string.length(); ++i) {
         char c = string.charAt(i);
         if (c == '\\' || c == '"') {
            stringBuilder.append('\\');
         }

         stringBuilder.append(c);
      }

      return stringBuilder.append('"').toString();
   }
}
