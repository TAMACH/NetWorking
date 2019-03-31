/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package td;

/**
 *
 * @author Maroine
 */
public class C22 extends C2 {

    private C22() {
        super();
    }

    public C22(C1 c1, C2 c2) {
        super(c1, c2);
    }

    public C22(double f, int i, String s, C2 c2) {
        super(f, i, s, c2);

    }

    public void writeToBuff(SerializerBuffer ms) {
    }

    public void readFromBuff(SerializerBuffer ms) {
    }

}
