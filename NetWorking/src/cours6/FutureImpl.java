/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cours6;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author tamac
 */
public class FutureImpl<T> implements Future<T> {

    private T theResult = null;
    private boolean done = false;

    public boolean isDone() {
        return done;
    }

    public synchronized void set(T theResult) throws InterruptedException, ExecutionException {
        this.theResult = theResult;
        this.done = true;
        notifyAll();
    }

    @Override
    public synchronized T get() throws InterruptedException, ExecutionException {
        while (!done) {
            wait();
        }
        return theResult;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    public boolean isCancelled() {
        return false;
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        Thread.sleep(unit.convert(timeout, unit));
        return get();
    }

}
