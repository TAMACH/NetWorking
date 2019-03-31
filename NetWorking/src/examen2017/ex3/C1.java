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
public class C1 {

    double f;
    int i;

    private C1() {
    }

    public C1(double f, int i) {
        this.f = f;
        this.i = i;
    }

    public void serialize(ByteBuffer bb) {
        bb.putDouble(f);
        bb.putInt(i);
    }

    public String toString() {
        return "C1(" + f + ", " + i + ")";
    }

    public static C1 deserialize(ByteBuffer bb) throws BufferUnderflowException {
        return new C1(bb.getDouble(), bb.getInt());
    }

}
