package Testt;

class Flag {

    volatile boolean val = false;
}

class Shared {

    volatile int t;
    volatile int turn = 0;
}

public class Decker implements Runnable {

    final int id;
    Shared sh;
    Flag we;
    Flag weO;

    public Decker(int id, Shared sh, Flag we, Flag weO) {
        this.id = id;
        this.sh = sh;
        this.we = we;
        this.weO = weO;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            we.val = true;
            while (weO.val) {
                if (sh.turn != id) {
                    we.val = false;
                    while (sh.turn != id) {
                    };
                    we.val = true;
                }

            }
            sh.t++;
            sh.turn = 1 - id;
            we.val = false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Shared sh = new Shared();
        Flag we0 = new Flag();
        Flag we1 = new Flag();

        Thread t1 = new Thread(new Decker(0, sh, we0, we1));
        Thread t2 = new Thread(new Decker(1, sh, we1, we0));
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Array size:" + sh.t);
    }

}
