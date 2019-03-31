/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exPile;

import java.util.ArrayList;
import lock.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Maroine
 */
class Stack<Integer> {

    ArrayList<Integer> stack = new ArrayList<>();

    public Stack(ArrayList<Integer> stack) {
        this.stack = stack;
    }

    public void push(Integer e) {
        stack.add(e);
    }

    public Integer pop() {
        if (isEmpty()) {
            return null;
        }
        return stack.get(stack.size() - 1);
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }
}

public class Impl implements Runnable {

    Stack sh;

    public Impl(Stack sh) {
        this.sh = sh;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            if (sh.isEmpty()) {
                sh.push(i);
            } else {
                //sh.po
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Integer> l = new ArrayList<Integer>();
        Stack sh = new Stack(l);
        Thread t1 = new Thread(new Impl(sh));
        Thread t2 = new Thread(new Impl(sh));

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println(sh.stack);
    }
}
