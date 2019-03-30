/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cours4;

/**
 *
 * @author Maroine
 */
class Shared {

    volatile int t;
    volatile int turn = 0;
}

class Flag {

    volatile boolean val = false;
}

public class Decker implements Runnable {

    int id;
    Shared sh;
    Flag we;

    Flag we0;

    public Decker(int id, Shared sh, Flag we, Flag we0) {
        this.id = id;
        this.sh = sh;
        this.we = we;
        this.we0 = we0;

    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            sh.t++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Shared sh = new Shared();
        Flag we = new Flag();
        Flag we0 = new Flag();

        Thread t1 = new Thread(new Decker(0, sh, we, we0));
        Thread t2 = new Thread(new Decker(1, sh, we0, we));

        t1.start();
        t2.start();

        t1.join();
        t2.join();

    }
}
