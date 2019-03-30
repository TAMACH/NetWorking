/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package td;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 *
 * @author Maroine
 */
public class C1 {

    double f;
    int i;
    String s;
    private static final Charset charset = Charset.forName("UTF-8");

    protected C1() {
    }

    public C1(double f, int i, String s) {
        this.f = f;
        this.i = i;
        this.s = s;
    }

    @Override
    public String toString() {
        return "C1(" + f + "," + i + ", " + s + ")";
    }

    public void serialize(ByteBuffer bb) {
        bb.putDouble(f);
        bb.putInt(i);
        ByteBuffer encode = charset.encode(s);
        bb.putInt(encode.remaining());
        bb.put(encode);

    }

    public static C1 deserialize(ByteBuffer bb) throws BufferUnderflowException {
        double f = bb.getDouble();
        int i = bb.getInt();
        int remaining = bb.getInt();
        int limit = bb.position();
        bb.limit(limit + remaining);
        CharBuffer decode = charset.decode(bb);
        bb.limit(limit);
        return new C1(f, i, decode.toString());
    }

    public static void main(String[] args) {
        C1 c1 = new C1(14.2, 16, "test");
        ByteBuffer bb = ByteBuffer.allocate(512);
        c1.serialize(bb);
        bb.flip();
        System.out.println(c1);
        C1 c11 = C1.deserialize(bb);
        System.out.println(c11);
    }
}
