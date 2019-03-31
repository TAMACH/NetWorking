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
public abstract class Shape {

    static final byte STILL_SHAPE_CCMPOSITE = 0;
    static final byte END_SHAPE_CCMPOSITE = 1;
    static final byte SHAPE_CCMPOSITE = 2;
    static final byte RECTANGLE = 3;
    static final byte SQUARE = 4;

    public abstract void serialize(ByteBuffer bb);

    public static Shape deserialize(ByteBuffer bb) {
        switch (bb.get()) {
            case SHAPE_CCMPOSITE:
                return ShapeComposite.deserialize(bb);
            case RECTANGLE:
                return Rectangle.deserialize(bb);
            case SQUARE:
                return Square.deserialize(bb);
        }
        return null;
    }

    public static void main(String[] args) {
        ByteBuffer bb = ByteBuffer.allocate(512);
        Rectangle r = new Rectangle(10, 3);
        Square s = new Square(7);
        ShapeComposite sc = new ShapeComposite();
        sc.shapeList.add(r);
        sc.shapeList.add(s);

        sc.serialize(bb);
        bb.flip();
        Shape deserialize = Shape.deserialize(bb);
        System.out.println(deserialize);
    }
}
