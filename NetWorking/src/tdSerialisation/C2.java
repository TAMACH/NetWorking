package tdSerialisation;

public class C2 implements MySerialisable {

    C1 c1;
    C2 c2;

    public C2() {
    }

    ;
	public C2(C1 c1, C2 c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public String toString() {
        return "C2(" + c1 + "," + c2 + ")";
    }

    @Override
    public void writeToBuff(SerializerBuffer bb) {
        c1.writeToBuff(bb);
        if (c2 == null) {
            bb.bb.put((byte) 0);
        } else {
            bb.bb.put((byte) 1);
            c2.writeToBuff(bb);
        }
    }

    @Override
    public void readFromBuff(SerializerBuffer bb) {
        c1 = new C1();
        c1.readFromBuff(bb);
        if (bb.bb.get() != (byte) 0) {
            c2 = new C2();
            c2.readFromBuff(bb);
        }
    }

}
