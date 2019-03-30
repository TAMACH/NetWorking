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

    public TimeClient(String url, int port) throws IOException {
        InetSocketAddress remote = new InetSocketAddress(url, port);
        client = DatagramChannel.open();
    }

    @Override
    public void run() {
        try {
            write();
            read();

        } catch (IOException ex) {
            Logger.getLogger(TimeClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void read() throws IOException {
        bb.clear();
        client.read(bb);
        bb.flip();
        long time = bb.getLong();
        System.out.println("the time is " + time);
    }

    private void write() throws IOException {
        byte[] bytes8 = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};
        bb.clear();
        bb.put(bytes8);
        bb.flip();
        client.write(bb);
    }

    public static void main(String[] args) throws IOException {
        TimeServer time = new TimeServer("localhost", 8080);
        time.run();
    }

}
