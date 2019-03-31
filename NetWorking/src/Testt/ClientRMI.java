package Testt;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientRMI {

    public static void main(String args[])
            throws RemoteException, NotBoundException {
        String name = "TEST1";
        Registry registry
                = LocateRegistry.getRegistry("localhost");
        Welcomer welcomer
                = (Welcomer) registry.lookup(name);
        System.out.println(welcomer.hi("UPEC"));
    }

}
