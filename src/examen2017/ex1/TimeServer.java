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

/**
 *
 * @author tamac
 */
public class TimeServer implements Runnable {

    private final Selector selector;
    private final Charset c = Charset.forName("UTF-8");
    private ByteBuffer bb = ByteBuffer.allocate(512);

    public TimeServer(String url, int port) throws IOException {
        selector = Selector.open();
        DatagramChannel server = DatagramChannel.open();
        server.configureBlocking(false);
        InetSocketAddress remote = new InetSocketAddress(url, port);
        server.bind(remote);
        server.register(selector, SelectionKey.OP_READ);
    }

    public static void main(String[] args) throws IOException {
        TimeServer time = new TimeServer("localhost", 8080);
        time.run();
    }

    @Override
    public void run() {
        try {
            while (true) {
                selector.select();
                for (SelectionKey sk : selector.selectedKeys()) {
                    System.out.println(sk.isAcceptable());
                    if (sk.isReadable()) {
                        read(sk);
                    }
                }
                selector.selectedKeys().clear();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void read(SelectionKey sk) throws IOException {
        DatagramChannel channel = (DatagramChannel) sk.channel();
        int n = channel.read(bb);
        if (n == 8) {
            CharBuffer decode = c.decode(bb);
            System.out.println(decode.toString());
        }
    }

}
