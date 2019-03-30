/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen2018.ex1;

import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 *
 * @author Maroine
 */
public class ShapeComposite extends Shape {

    ArrayList<Shape> shapeList = new ArrayList<>();

    public static ShapeComposite deserialize(ByteBuffer bb) {
        ShapeComposite shapeComposite = new ShapeComposite();
        int size = bb.getInt();
        for (int i = 0; i < size; i++) {
            shapeComposite.shapeList.add(Shape.deserialize(bb));
        }
        return shapeComposite;
    }

    public String toString() {
        String ch = "SC(";
        for (Shape s : shapeList) {
            ch += s.toString() + ";";
        }
        return ch + ")";
    }

    @Override
    public void serialize(ByteBuffer bb) {
        bb.put(SHAPE_CCMPOSITE);
        bb.putInt(shapeList.size());
        for (Shape s : shapeList) {
            s.serialize(bb);
        }
    }
}
