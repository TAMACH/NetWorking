import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

public class Noeud {
	public Server server;
	public Client client;
	
	public Noeud() throws IOException {
		Server s = new Server(2020);
	    Thread t2 = new Thread(s);
	    t2.start();
	    Client c= new Client("localhost",2020);
	    Thread t1 = new Thread(c);
	    t1.start();
		
	}
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
	    Noeud noeud = new Noeud();
	    

	}

}
