/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cours;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 *
 * @author Maroine
 */
public class Msg {

    int n;
    String message;
    Msg follow;
    final static Charset c = Charset.forName("UTF-8");
    final static byte ZERO = 0;
    final static byte ONE = 1;

    @Override
    public String toString() {
        return "Msg(" + n + "," + message + "," + follow + ")";
    }

    public Msg(int n, String message, Msg follow) {
        this.n = n;
        this.message = message;
        this.follow = follow;

    }

    public void serialize(ByteBuffer bb) {
        bb.putInt(n);
        ByteBuffer encode = c.encode(message);
        bb.putInt(encode.remaining());
        bb.put(encode);
        if (follow != null) {
            bb.put(ONE);
            follow.serialize(bb);
        } else {
            bb.put(ZERO);
        }
    }

    public static Msg deserialize(ByteBuffer bb) {
        int n = bb.getInt();
        int t = bb.getInt();
        byte[] dst = new byte[t];
        bb.get(dst, 0, t);
        String message = new String(dst);
        Msg msg = null;
        if (bb.get() != ZERO) {
            msg = deserialize(bb);
        }
        return new Msg(n, message, msg);
    }

    public static void main(String[] args) {
        Msg m1 = new Msg(42, "Toto", new Msg(32, "test ♉ ♥♥", null));
        Msg m2 = new Msg(3, "t", null);

        ByteBuffer bb = ByteBuffer.allocate(512);
        m1.serialize(bb);
        m2.serialize(bb);
        bb.flip();
        System.out.println(m1);
        System.out.println(m2 + "\n");

        Msg m3 = deserialize(bb);
        Msg m4 = deserialize(bb);
        System.out.println(m3);
        System.out.println(m4);

    }
}
