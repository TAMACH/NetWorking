/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen2018.ex1;

import java.nio.ByteBuffer;

/**
 *
 * @author Maroine
 */
public class Square extends Shape {

    int cote;

    public Square(int cote) {
        this.cote = cote;
    }

    private Square() {

    }

    public void serialize(ByteBuffer bb) {
        bb.put(SQUARE);
        bb.putInt(cote);
    }

    public static Square deserialize(ByteBuffer bb) {
        int cote = bb.getInt();
        Square square = new Square();
        square.cote = cote;
        return square;
    }

    public String toString() {
        return "S(" + cote + ")";
    }
}
