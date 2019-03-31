package Testt;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

class ValPremier implements Callable<Long> {
	private long n;
	public ValPremier(long n) {
		this.n=n;
	}
	
	private boolean estPremier(long n) {
	    final long max = (long) Math.floor(Math.sqrt(n));
	    for(long j = 0; j<100000L;j++) {}
	    for(int i=2;i<=max;i++) {
	      if(n%i==0) return false;
	    }
	    return true;
	}

	@Override
	public Long call() throws Exception {
		if(estPremier(n))return n;
		return 0L;
	}
}

public class TestFuture {
	public static long sommePremierConcur(int poolsize, long n) {
	ExecutorService es = java.util.concurrent.Executors.newWorkStealingPool();	
	ArrayList<Future<Long>> al = new ArrayList<>();
	for(long i=0;i<n;i++) {
		Callable<Long> c = new ValPremier(i);
		al.add(es.submit(c));
	}
	es.shutdown();
	System.out.println("finish submitting");
	long s=0;
	try {
	for(Future<Long> f : al) {
		s+=f.get();
	}
	} catch (Exception e) {}
	
	return s;
	}
		
	public static long sommePremier(long n) {
		long s =0;
		try {
			for(long i=0;i<n;i++) {
				Callable<Long> c = new ValPremier(i);
				s+=c.call();
			}
		}catch (Exception e) {}
		return s;
	}
	
	public static void main(String[] args) {
		long n=100000;
		long t1 = System.currentTimeMillis();
		long s= sommePremierConcur(4,n);
		long t2 = System.currentTimeMillis();
		System.out.println("result:"+s);
		System.out.println("time:"+ (t2-t1));
		
	}
}
