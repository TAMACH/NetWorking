package Testt;

class T extends Thread {
	public Thread t;
	public Object o;
@Override
public void run() {
	synchronized(o) {
		for(long i =0 ; i< 10000000000l;i++ ) {
			if(this.isInterrupted())break;
		}
		if(!this.isInterrupted())System.out.println("finished");
	}
}
}

public class TestInterrupt {

	public static void main(String[] args) {
		final Object o = new Object();
		T t1 = new T();
		t1.o=o;
		
		t1.start();
		t1.interrupt();

		
	}
	
}
