/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen2017.ex1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maroine
 */
public class TimeClient implements Runnable {

    private final Charset c = Charset.forName("UTF-8");
    private ByteBuffer bb = ByteBuffer.allocate(512);
    private final DatagramChannel client;
    private final InetSocketAddress addr;

    public TimeClient(String url, int port) throws IOException {
        addr = new InetSocketAddress(url, port);
        client = DatagramChannel.open();
    }

    @Override
    public void run() {
        try {
            write();
            read();
        } catch (IOException ex) {
        }
    }

    private void read() throws IOException {
        bb.clear();
        client.receive(bb);
        bb.flip();
        System.out.println(bb);
        long time = bb.getLong();
        System.out.println("the time is " + time);
    }

    private void write() throws IOException {
        byte[] bytes8 = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};
        bb.clear();
        bb.put(bytes8);
        bb.flip();
        client.send(bb, addr);
    }

    public static void main(String[] args) throws IOException {
        TimeClient client = new TimeClient("localhost", 8080);
        client.run();
    }

}
