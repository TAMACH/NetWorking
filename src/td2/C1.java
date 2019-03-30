/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package td2;

import java.nio.charset.Charset;

/**
 *
 * @author Maroine
 */
public class C1 implements MySerialisable {

    double f;
    int i;
    String s;
    private static final Charset charset = Charset.forName("UTF-8");
    public static final Creator<C1> CREATOR = () -> new C1();

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

    @Override
    public void writeToBuff(SerializerBuffer ms) {
        ms.writeDouble(f);
        ms.writeInt(i);
        ms.writeString(s);
    }

    @Override
    public void readFromBuff(SerializerBuffer ms) {
        f = ms.readDouble();
        i = ms.readInt();
        s = ms.readString();

    }

    public static void main(String[] args) {
        C1 c1 = new C1(14.2, 16, "test");
        System.out.println(c1);
        SerializerBuffer sb = new SerializerBuffer();
        sb.writeMySerialisable(c1);
        sb.flip();
        C1 c1p = sb.readMySerialisable(C1.CREATOR);
        System.out.println(c1p);
    }
}
