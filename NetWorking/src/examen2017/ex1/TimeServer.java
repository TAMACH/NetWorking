/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen2017.ex1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

/**
 *
 * @author tamac
 */
public class TimeServer implements Runnable {

    private final Charset c = Charset.forName("UTF-8");
    private ByteBuffer bb = ByteBuffer.allocate(512);
    private final DatagramChannel server;

    public TimeServer(String url, int port) throws IOException {
        server = DatagramChannel.open();
        InetSocketAddress remote = new InetSocketAddress(url, port);
        server.bind(remote);
    }

    @Override
    public void run() {
        try {
            while (true) {
                bb.clear();
                SocketAddress receive = server.receive(bb);
                bb.flip();
                if (bb.limit() == 8) {
                    bb.clear();
                    long time = System.currentTimeMillis();
                    System.out.println("the time is " + time);
                    bb.putLong(time);
                    bb.flip();
                    server.send(bb, receive);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        TimeServer time = new TimeServer("localhost", 8080);
        time.run();
    }

}
