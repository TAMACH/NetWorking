/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen2017.ex3;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 *
 * @author Maroine
 */
public class C2 {

    C1 c1;// not null
    C2 c2;

    private C2() {
    }

    public C2(C1 c1, C2 c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    public void serialize(ByteBuffer bb) {
        c1.serialize(bb);
        if (c2 != null) {
            bb.put((byte) 1);
            c2.serialize(bb);
        } else {
            bb.put((byte) 0);
        }
    }

    public static C2 deserialize(ByteBuffer bb) throws BufferUnderflowException {
        C2 c2Result = new C2();
        c2Result.c1 = C1.deserialize(bb);
        if (bb.get() == (byte) 1) {
            c2Result.c2 = C2.deserialize(bb);
        }
        return c2Result;
    }

    public String toString() {
        return "C2(" + c1 + ", " + c2 + ")";
    }

    public static void main(String[] args) {
        ByteBuffer bb = ByteBuffer.allocate(1024);
        C2 c2 = new C2(new C1(1, 2), new C2(new C1(15, 02), null));
        System.out.println(c2);
        c2.serialize(bb);
        bb.flip();
        c2 = C2.deserialize(bb);
        System.out.println(c2);
    }

}
