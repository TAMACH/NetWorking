/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen2017.ex2;

/**
 *
 * @author Maroine
 */
public class BarriereCyclique {

    int count;
    int n;
    Runnable r;

    public BarriereCyclique(int n) {
        this.n = n;
        this.count = 0;
        this.r = null;
    }

    public BarriereCyclique(int n, Runnable r) {
        this.n = n;
        this.count = 0;
        this.r = r;
    }

    public void swait() throws InterruptedException {
        synchronized (this) {
            count++;
            if (count == n) {
                if (r != null) {
                    r.run();
                }
                notifyAll();
            } else {
                while (count < n) {
                    wait();
                }
            }
            if (count == n) {
                count = 0;
            };

        }
    }
}
