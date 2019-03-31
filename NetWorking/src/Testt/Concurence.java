package Testt;

public class Concurence implements Runnable {

    StringBuilder al;

    public Concurence(StringBuilder al) {
        this.al = al;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            al.append(",");
            al.append(i);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        StringBuilder al = new StringBuilder();

        Thread t1 = new Thread(new Concurence(al));
        Thread t2 = new Thread(new Concurence(al));
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Array size:" + al);
    }
}
