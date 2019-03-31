/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cours4;

import java.util.ArrayList;

/**
 *
 * @author Maroine
 */
public class Concurence implements Runnable {

    ArrayList<Integer> al;

    public Concurence(ArrayList<Integer> al) {
        this.al = al;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (al.isEmpty()) {
                al.add(i);
            } else {
                int j = i + al.get(al.size() - 1);
                al.remove(al.size() - 1);
                al.add(j);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Integer> al = new ArrayList<>();
        Thread t1 = new Thread(new Concurence(al));
        Thread t2 = new Thread(new Concurence(al));

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Array size : " + al.size() + " [0] : " + al.get(0));
    }

}
