package tdSerialisation;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class C1 implements MySerialisable {

    double f;
    int i;
    String s;

    public C1() {
    }

    ;
	public C1(double f, int i, String s) {
        this.f = f;
        this.i = i;
        this.s = s;
    }

    @Override
    public String toString() {
        return "C1(" + f + "," + i + "," + s + ")";
    }

    @Override
    public void writeToBuff(SerializerBuffer bb) {
        bb.writeDouble(f);
        bb.writeInt(i);
        bb.writeString(s);
    }

    @Override
    public void readFromBuff(SerializerBuffer bb) throws BufferUnderflowException {
        this.f = bb.readDouble();
        this.i = bb.readInt();
        this.s = bb.readString();
    }

    public static void main(String[] args) {
        C1 c1 = new C1(3.12, 12, "test €");
        C2 c2 = new C2(c1, new C2(c1, null));
        System.out.println(c2);
        SerializerBuffer bb = new SerializerBuffer(ByteBuffer.allocate(64));
        c2.writeToBuff(bb);
        System.out.println(bb);
        bb.bb.flip();
        System.out.println(bb);
        C2 c2p = new C2();
        c2p.readFromBuff(bb);
        System.out.println(c2p);

    }

}
