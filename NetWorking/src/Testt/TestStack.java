package Testt;

import java.util.ArrayList;

public class TestStack {
	ArrayList<Integer> al = new ArrayList<>();
	
	public void push(int i) {
		synchronized(this){
			al.add(i);
		}
	}
	
	public synchronized int pop() {
		int j = al.get(al.size()-1);
		al.remove(al.size()-1);
		return j;
	}
	public synchronized boolean isEmpty() {
		return al.isEmpty();
	}
	
	static class Th extends Thread{
		TestStack ts;
		public Th(TestStack ts) {
			this.ts=ts;
		}
		
			@Override
			public void run() {
				for(int i=0;i<100;i++) {	
					if(ts.isEmpty()) {
						ts.push(i);
					}else {
						int j = i+ ts.pop();
						ts.push(j);
					}
				}
			}
	
	public static void main(String[] args) throws InterruptedException {
		String toto= "totoç 🚀";
		System.out.println(toto.length());
		System.out.println(toto.getBytes().length);
		
		
		
		final TestStack ts = new TestStack();
		Thread t1 = new Th(ts);
		Thread t2 = new Th(ts);
		
		t1.start();
		t2.run();
		
		t1.join();

		
		System.out.println(ts.pop());
		};
		
		
	}
	
}
