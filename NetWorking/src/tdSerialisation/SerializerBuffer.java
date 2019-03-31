package tdSerialisation;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SerializerBuffer {

    final static Charset charset = Charset.forName("UTF-8");
    public ByteBuffer bb;
    private Map<Integer, Integer> objMap = new HashMap<>();
    private ArrayList<MySerialisable> objArray = new ArrayList<>();

    @Override
    public String toString() {
        return bb.toString();
    }

    public SerializerBuffer(ByteBuffer bb) {
        this.bb = bb;
    }

    public void writeInt(int i) {
        bb.putInt(i);
    }

    public void writeDouble(double d) {
        bb.putDouble(d);
    }

    public int readInt() {
        return bb.getInt();
    }

    public double readDouble() {
        return bb.getDouble();
    }

    public void writeString(String s) {
        ByteBuffer bs = charset.encode(s);
        bb.putInt(bs.remaining());
        bb.put(bs);
    }

    public String readString() {
        int n = bb.getInt();
        int lim = bb.limit();
        bb.limit(bb.position() + n);
        String s = charset.decode(bb).toString();
        bb.limit(lim);
        return s;
    }

    public void writeMySerialisable(MySerialisable m) {
        if (m == null) {
            bb.putInt(-2);
        } else {
            Integer bind = objMap.get(System.identityHashCode(m));
            if (bind == null) {
                objArray.add(m);
                objMap.put(System.identityHashCode(m), objArray.size() - 1);
                bb.putInt(-1);
                m.writeToBuff(this);
            } else {
                bb.putInt(bind);
            }
        }
    }

    ;
	public <T extends MySerialisable> T readMySerialisable(Creator<T> c) {
        int bind = bb.getInt();
        if (bind == -2) {
            return null;
        }
        if (bind == -1) {
            T m = c.init();
            objArray.add(m);
            objMap.put(System.identityHashCode(m), objArray.size() - 1);
            m.readFromBuff(this);
            return m;
        } else {
            return (T) objArray.get(bind);
        }
    }
;

}
