/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cours6;

import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maroine
 */
public class Pool implements Executor {

    private final int size;

    List<Thread> threads;
    BlockingQueue<Runnable> tasks;

    public Pool(int size) {
        this.size = size;
        threads = new ArrayList<>(size);
        tasks = new LinkedBlockingQueue<>();
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(() -> {
                try {
                    while (true) {
                        sleep(1000);
                        tasks.take().run();
                    }
                } catch (InterruptedException ex) {
                    System.out.println(ex);
                }
            }));
        }
        for (int i = 0; i < size; i++) {
            threads.get(i).start();
        }
    }

    @Override
    public void execute(Runnable command) {
        tasks.add(command);
    }

    public <T> Future<T> submit(Callable<T> callable) {
        Task<T> task = new Task<>(callable);
        tasks.add(task);
        return task.future;
    }

    public static void main(String[] args) {
        Pool pool = new Pool(100);
        for (int i = 0; i < 100; i++) {
            final int j = i;
            pool.execute(() -> {
                System.out.println(j);
            });
        }
    }
}
