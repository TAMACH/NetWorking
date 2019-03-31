package Testt;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Welcomer extends Remote{
	String hi(String s) throws RemoteException;
	
}
