/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package td;

/**
 *
 * @author tamac
 */
public class C11 extends C1 implements MySerialisable {

    public static final Creator<C11> CREATOR = () -> new C11();

    public C11() {
        super();
    }

    public C11(double f, int i, String s) {
        super(f, i, s);
    }

    public void writeToBuff(SerializerBuffer ms) {
        ms.writeDouble(f);
        ms.writeInt(i);
        ms.writeString(s);
    }

    public void readFromBuff(SerializerBuffer ms) {
        f = ms.readDouble();
        i = ms.readInt();
        s = ms.readString();
    }

    public static void main(String[] args) {
        SerializerBuffer ms = new SerializerBuffer();
        C11 c11 = new C11(1.1, 2, "test");
        System.out.println(c11);
        c11.writeToBuff(ms);
        C11 c11p = new C11();
        ms.flip();
        c11p.readFromBuff(ms);
        System.out.println(c11p);
    }

}
