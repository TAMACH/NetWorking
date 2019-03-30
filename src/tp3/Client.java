/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 *
 * @author Maroine
 */
public class Client {

    private final int SIZE = 512;
    private final SocketChannel clientSocket;
    private final ByteBuffer bb = ByteBuffer.allocate(SIZE);
    private final InetSocketAddress address;
    private final Charset charset = Charset.forName("UTF-8");

    private boolean isConnected;

    public Client(String addrString, int port) throws IOException {
        address = new InetSocketAddress(addrString, port);
        clientSocket = SocketChannel.open();
        clientSocket.connect(address);
        isConnected = true;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void isConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public SocketChannel getSocket() {
        return clientSocket;
    }

    public void start() throws InterruptedException {
        Thread networkThread = new Thread(new RepeatNetwork(this));
        Thread keyboardThread = new Thread(new RepeatKeyboard(this));
        networkThread.start();
        keyboardThread.start();
        networkThread.join();
        keyboardThread.join();
    }

    public void sendPrivateMessage(String message, Client other) throws IOException {
        ByteBuffer encode = charset.encode(message);
        bb.put(encode);
        int n = other.getSocket().write(bb);
        bb.flip();
        bb.clear();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Client client = new Client("127.0.0.1", 2020);
        client.start();
        //client.sendPrivateMessage("hello", client);
    }

}
