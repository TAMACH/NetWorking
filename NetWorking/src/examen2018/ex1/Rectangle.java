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
public class Rectangle extends Shape {

    int largeur;
    int longueur;

    public Rectangle(int largeur, int longueur) {
        this.largeur = largeur;
        this.longueur = longueur;
    }

    private Rectangle() {

    }

    public void serialize(ByteBuffer bb) {
        bb.put(RECTANGLE);
        bb.putInt(largeur);
        bb.putInt(longueur);
    }

    public static Rectangle deserialize(ByteBuffer bb) {
        int longueur = bb.getInt();
        int largeur = bb.getInt();
        return new Rectangle(largeur, longueur);
    }

    public String toString() {
        return "R(" + largeur + ", " + longueur + ")";
    }
}
