package Testt;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Server {

    ServerSocketChannel ssc;
    Selector selector;
    ByteBuffer bb;

    public Server() throws IOException {
        ssc = ServerSocketChannel.open();
        selector = Selector.open();
        bb = ByteBuffer.allocateDirect(512);

        SocketAddress sa = new InetSocketAddress(2020);
        ssc.bind(sa);
        ssc.configureBlocking(false);
        ssc.register(selector, SelectionKey.OP_ACCEPT);
    }

    void accept() throws IOException {
        SocketChannel sc = ssc.accept();
        System.out.println("Nouvelle connection" + sc);
        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_READ);
    }

    public void repeat(SelectionKey sk) throws IOException {
        SocketChannel sc = (SocketChannel) sk.channel();
        int n = sc.read(bb);
        if (n < 0) {
            System.out.println("Client Leave");
            sk.cancel();
            sc.close();
            return;
        }

        bb.flip();
        Charset c = Charset.forName("UTF-8");
        CharBuffer cb = c.decode(bb);
        System.out.println(cb.toString());
        for (SelectionKey s : selector.keys()) {
            if (s.isReadable()) {
                bb.rewind();
                SocketChannel clientsock = (SocketChannel) s.channel();
                clientsock.write(bb);

            }
        }

        bb.clear();
    }

    public void run() throws IOException {
        while (true) {
            selector.select();
            for (SelectionKey sk : selector.selectedKeys()) {
                if (sk.isAcceptable()) {
                    accept();
                } else {
                    repeat(sk);
                }
            }
            selector.selectedKeys().clear();

        }

    }

    public static void main(String[] args) throws IOException {
        Server s = new Server();
        s.run();

    }

}
