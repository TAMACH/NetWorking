/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cours;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Maroine
 */
public class Bencode {

    ByteBuffer bb;
    String st;
    final static Charset c = Charset.forName("ASCII");

    Bencode(ByteBuffer bb) {
        this.bb = bb;
    }

    public void bencodeString(String str) {
        ByteBuffer bb2 = c.encode(str);
        int n = bb2.remaining();
        bb.put(c.encode("" + n));
        bb.putChar(':');
        bb.put(bb2);
    }

    public void show() {
        for (byte b : bb.array()) {
            System.out.print("(" + b + "," + (char) b + ") ");
        }
    }

    public static void main(String[] args) {
        Bencode bencode = new Bencode(ByteBuffer.allocate(32));
        bencode.bencodeString("df");
        bencode.show();

    }

    public <T> String serialize(T o) {
        Class<?> Myclass = o.getClass();
        String res = "";
        if (Myclass.equals(Integer.class)) {
            Integer n = (Integer) o;
            res = "i" + n + "e";
        } else if (Myclass.equals(String.class)) {
            String str = (String) o;
            res = str.length() + ":" + str;

        } else if (Myclass.equals(List.class)) {
            List l = (List) o;
            res = "l";
            for (Object e : l) {
                res += serialize(e);
            }
            res += "e";
        } else if (Myclass.equals(Map.class)) {
            Map<Object, Object> m = (Map) o;
            res = "d";
            for (Map.Entry<Object, Object> entry : m.entrySet()) {
                res += serialize(entry.getKey()) + "->" + serialize(entry.getValue());
            }
            res += "e";

        }
        return res;
    }
}
