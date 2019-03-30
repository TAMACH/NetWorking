/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package td;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 *
 * @author Maroine
 */
public class C2 {

    C1 c1;
    C2 c2;
    private static byte ONE = 1;
    private static byte ZERO = 0;

    protected C2() {
    }

    public C2(C1 c1, C2 c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    public C2(double f, int i, String s, C2 c2) {
        this.c1 = new C1(f, i, s);
        this.c2 = c2;
    }

    @Override
    public String toString() {
        return "C1(" + c1 + "," + c2 + ")";
    }

    public void serialize(ByteBuffer bb) {
        c1.serialize(bb);
        if (c2 != null) {
            bb.put(ONE);
            c2.serialize(bb);
        } else {
            bb.put(ZERO);
        }
    }

    public static C2 deserialize(ByteBuffer bb) throws BufferUnderflowException {
        C1 c1 = C1.deserialize(bb);

        C2 c2 = null;
        if (bb.get() != ZERO) {
            c2 = C2.deserialize(bb);
        }
        return new C2(c1, c2);
    }

    public static void main(String[] args) {
        C1 c1 = new C1(14.2, 16, "test");
        C2 c2 = new C2(c1, new C2(10.0, 20, "gfv", null));
        ByteBuffer bb = ByteBuffer.allocate(512);
        c2.serialize(bb);
        System.out.println(c2);
        bb.flip();
        C2 c22 = C2.deserialize(bb);
        System.out.println(c22);
    }
}
