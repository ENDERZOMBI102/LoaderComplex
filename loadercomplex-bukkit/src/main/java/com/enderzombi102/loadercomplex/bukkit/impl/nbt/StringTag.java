package com.enderzombi102.loadercomplex.bukkit.impl.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

public class StringTag extends Tag {
   private String value;

   public StringTag() {
      this("");
   }

   public StringTag(String value) {
      Objects.requireNonNull(value, "Null string not allowed");
      this.value = value;
   }

   void write(DataOutput output) throws IOException {
      output.writeUTF(this.value);
   }

   void read(DataInput dataInput, int i, PositionTracker positionTracker) throws IOException {
      positionTracker.add(288L);
      this.value = dataInput.readUTF();
      positionTracker.add( 16L * this.value.length() );
   }

   public byte getType() {
      return 8;
   }

   public String toString() {
      return escapeString(this.value);
   }

   public StringTag copy() {
      return new StringTag(this.value);
   }

   public boolean isEmpty() {
      return this.value.isEmpty();
   }

   public boolean equals(Object object) {
      if (! super.equals(object) ) {
         return false;
      } else {
         StringTag stringTag = (StringTag) object;
         return this.value == null && stringTag.value == null || Objects.equals( this.value, stringTag.value );
      }
   }

   public int hashCode() {
      return super.hashCode() ^ this.value.hashCode();
   }

   public String asString() {
      return this.value;
   }

   public static String escapeString(String string) {
      StringBuilder stringBuilder = new StringBuilder("\"");

      for ( char c : string.toCharArray() ) {
         if (c == '\\' || c == '"') {
            stringBuilder.append('\\');
         }

         stringBuilder.append(c);
      }

      return stringBuilder.append('"').toString();
   }
}
