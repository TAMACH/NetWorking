package test;

import java.io.IOException;
import java.net.*;
import java.nio.channels.*;
import java.nio.*;
import java.util.Iterator;
import java.util.Set;

public class Server {

    private final int PORT = 2020;
    private final int BUFFSIZE = 512;
    private final String HOSTNAME = "127.0.0.1";

    public Server() throws IOException {

    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
    }
}
