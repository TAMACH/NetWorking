package Testt;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

class We implements Welcomer {

    @Override
    public String hi(String s) {
        return ("Hi " + s);
    }
}

public class ServerRMI {

    public static void main(String[] args) throws RemoteException {
        We w1 = new We();
        Welcomer w2 = (Welcomer) UnicastRemoteObject.exportObject(w1, 0);
        Registry registry = LocateRegistry.getRegistry();
        registry.rebind("TEST1", w2);
        System.out.println("Welcomerbound");
    }

}
