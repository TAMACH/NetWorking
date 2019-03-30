/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sync;

import lock.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Maroine
 */
class Shared {

    int t = 0;
}

public class LockOfJave implements Runnable {

    int id;
    Shared sh;

    public LockOfJave(int id, Shared sh) {
        this.id = id;
        this.sh = sh;

    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            synchronized (sh) {
                sh.t++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Shared sh = new Shared();
        Thread t1 = new Thread(new LockOfJave(0, sh));
        Thread t2 = new Thread(new LockOfJave(1, sh));

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println(sh.t);
    }
}
