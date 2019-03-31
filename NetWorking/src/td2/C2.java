/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package td2;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 *
 * @author Maroine
 */
public class C2 implements MySerialisable {

    C1 c1;
    C2 c2;
    private static byte ONE = 1;
    private static byte ZERO = 0;
    public static final Creator<C2> CREATOR = () -> new C2();

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

    @Override
    public void writeToBuff(SerializerBuffer sb) {
        sb.writeMySerialisable(c1);
        if (c2 != null) {
            sb.bb.put(ONE);
            sb.writeMySerialisable(c2);
        } else {
            sb.bb.put(ZERO);
        }
    }

    @Override
    public void readFromBuff(SerializerBuffer sb) {
        C1 c1 = sb.readMySerialisable(C1.CREATOR);
        C2 c2 = sb.readMySerialisable(C2.CREATOR);
    }

    public static void main(String[] args) {
        C1 c1 = new C1(14.2, 16, "test");
        C2 c2 = new C2(c1, new C2(10.0, 20, "gfv", null));
        System.out.println(c2);
        SerializerBuffer sb = new SerializerBuffer();
        sb.writeMySerialisable(c2);
        sb.flip();
        C2 c2p = sb.readMySerialisable(C2.CREATOR);
        System.out.println(c2p);
    }

}
