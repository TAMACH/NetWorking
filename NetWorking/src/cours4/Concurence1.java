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
public class Concurence1 implements Runnable {

    StringBuffer sb;

    public Concurence1(StringBuffer sb) {
        this.sb = sb;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            sb.append(i);
            sb.append(",");

        }
    }

    public static void main(String[] args) throws InterruptedException {
        StringBuffer sb = new StringBuffer("");
        Thread t1 = new Thread(new Concurence1(sb));
        Thread t2 = new Thread(new Concurence1(sb));

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("sb " + sb);
    }

}
