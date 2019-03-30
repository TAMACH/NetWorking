/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package td2;

import td.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 *
 * @author tamac
 */
public class SerializerBuffer {

    public final ByteBuffer bb = ByteBuffer.allocate(512);
    public final Charset charset = Charset.forName("UTF-8");

    public SerializerBuffer() {

    }

    public void writeMySerialisable(MySerialisable ms) {
        ms.writeToBuff(this);
    }

    public <T extends MySerialisable> T readMySerialisable(Creator<T> creator) {
        T init = creator.init();
        init.readFromBuff(this);
        return init;
    }

    public void writeString(String s) {
        ByteBuffer encode = charset.encode(s);
        bb.putInt(encode.remaining());
        bb.put(encode);
    }

    public String readString() {
        int remaining = bb.getInt();
        int limit = bb.position();
        bb.limit(limit + remaining);
        CharBuffer decode = charset.decode(bb);
        bb.limit(limit);
        return decode.toString();
    }

    public void writeInt(int i) {
        bb.putInt(i);
    }

    public void writeDouble(double f) {
        bb.putDouble(f);
    }

    public int readInt() {
        int i = 0;
        i = bb.getInt();
        return i;
    }

    public double readDouble() {
        double f = 0;
        f = bb.getDouble();
        return f;
    }

    public void flip() {
        bb.flip();
    }
}
