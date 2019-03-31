package Testt;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class FutureImpl<V> implements Future<V> {

    private V v = null;
    private boolean b = false;
    private Exception e = null;

    @Override
    public boolean isDone() {
        return b;
    }

    public synchronized void set(V v) {
        this.v = v;
        b = true;
        notifyAll();
    }

    public synchronized void setExn(Exception e) {
        b = true;
        this.e = e;
        notifyAll();
    }

    @Override
    public synchronized V get() throws InterruptedException, ExecutionException {
        while (!b) {
            wait();
        }
        if (e != null) {
            throw (new ExecutionException(e));
        }
        return v;
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

}

class Task<T> implements Runnable {

    FutureImpl<T> f;
    Callable<T> c;

    public Task(Callable<T> c) {
        this.c = c;
        f = new FutureImpl<>();
    }

    @Override
    public void run() {
        T t;
        try {
            t = c.call();
            f.set(t);
        } catch (Exception e) {
            f.setExn(e);
        }

    }
}

public class Exec implements Executor {

    ArrayList<Thread> tlist;
    BlockingQueue<Runnable> bq;

    public <T> FutureImpl<T> submit(Callable<T> c) {
        Task<T> t = new Task<>(c);
        bq.add(t);
        return t.f;
    }

    @Override
    public void execute(Runnable command) {
        bq.add(command);
    }

    public Exec(int n) {
        tlist = new ArrayList<Thread>(n);
        bq = new LinkedBlockingQueue();
        for (int i = 0; i < n; i++) {
            Thread t = new Thread(() -> {
                try {
                    while (true) {
                        Runnable r = bq.take();
                        r.run();
                    }
                } catch (InterruptedException e) {

                }
            });

            t.start();
            tlist.add(t);
        }
    }

    public static void main(String[] args) {
        Executor e = new Exec(4);
        Future<Integer> flasti = null;
        for (int i = 0; i < 10; i++) {
            final Integer j = i;
            /*Future<Integer> fi = e.submit( () ->{
					Thread.sleep(1000);
					if(true)throw (new Exception("test"));
					System.out.println("test"+j);return j;}
					);
			System.out.println(fi.isDone());
			flasti = fi;*/
            e.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (Exception ex) {
                };
                System.out.println("test" + j);
            });
        }
        System.out.println("finish submiting");
        /*System.out.println(flasti.isDone());
		try {
			System.out.println(flasti.get());
		}catch (ExecutionException ex) {
			Exception ex2 = (Exception) ex.getCause();
			ex2.printStackTrace();

		}catch (InterruptedException ex) {
			// TODO: handle exception
		}
         */
    }

}
