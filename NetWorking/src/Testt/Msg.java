package Testt;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class Msg implements Serializable {

    int n;
    String message;
    Msg follow;

    public Msg(int n, String message, Msg follow) {
        this.n = n;
        this.message = message;
        this.follow = follow;
    }

    @Override
    public String toString() {
        return "Msg(" + n + "," + message + "," + follow + ")";
    }

    static final Charset c = Charset.forName("UTF-8");

    public void serialize(ByteBuffer bb) {
        bb.putInt(n);
        ByteBuffer cb = c.encode(message);
        bb.putInt(cb.remaining());
        bb.put(cb);
        if (follow != null) {
            bb.put((byte) 1);
            follow.serialize(bb);
        } else {
            bb.put((byte) 0);
        }
    }

    public static Msg deserialize(ByteBuffer bb) {
        int n = bb.getInt();
        int t = bb.getInt();
        int lim = bb.limit();
        bb.limit(bb.position() + t);
        String m = c.decode(bb).toString();
        bb.limit(lim);
        Msg f = null;
        if (bb.get() != (byte) 0) {
            f = deserialize(bb);
        }
        return new Msg(n, m, f);
    }

    static final Charset c2 = Charset.forName("UTF-8");

    public static void bencodeString(String s, ByteBuffer bb) {
        ByteBuffer b2 = c2.encode(s);
        int n = b2.remaining();
        bb.put(c2.encode(Integer.toString(n)));
        bb.put(c2.encode(":"));
        bb.put(b2);
    }

    public static void main(String[] args) {
        ByteBuffer bb = ByteBuffer.allocate(32);

        /*Msg m1 = new Msg(42,"Toto", new Msg(32, "test 🚀", null ));
		Msg m2 = new Msg(3,"t",null);

		ByteBuffer bb = ByteBuffer.allocate(512);
		m1.serialize(bb);
		m2.serialize(bb);
		bb.flip();

		Msg m3 = deserialize(bb);
		Msg m4 = deserialize(bb);

		System.out.println(m3);

		System.out.println(m4);
         */
        bb.clear();
        bencodeString("test 🚀", bb);
        System.out.println(bb);

        for (byte b : bb.array()) {
            System.out.print("'" + b + ":" + (char) b + "'");
        }

    }
}
