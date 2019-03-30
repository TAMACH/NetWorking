/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cours6;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tamac
 */
public class Task<T> implements Runnable {

    Callable<T> callable;
    FutureImpl<T> future;
    Exception exception = null;

    public Task(Callable<T> callable) {
        this.future = new FutureImpl();
        this.callable = callable;
    }

    @Override
    public void run() {
        try {
            T call = callable.call();
            future.set(call);
        } catch (Exception ex) {
            Logger.getLogger(Task.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
