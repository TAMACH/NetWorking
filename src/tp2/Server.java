/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import static java.nio.channels.SelectionKey.OP_ACCEPT;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 *
 * @author Maroine
 */
public class Server {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private ByteBuffer bb;
    private InetSocketAddress address;
    private final int SIZE = 512;
    private final Charset charset = Charset.forName("UTF-8");

    public Server(int port) throws IOException {
        bb = ByteBuffer.allocate(SIZE);
        address = new InetSocketAddress(port);
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(address);
        serverSocketChannel.configureBlocking(false);
        selector = Selector.open();
        SelectionKey key = serverSocketChannel.register(selector, OP_ACCEPT);
    }

    public void accept() throws IOException {
        SocketChannel clientSocket = serverSocketChannel.accept();
        clientSocket.configureBlocking(false);
        clientSocket.register(selector, SelectionKey.OP_READ);
        System.out.println("New connection from : " + clientSocket.getRemoteAddress());

    }

    public void run() throws IOException {
        while (true) {
            selector.select();
            for (SelectionKey sk : selector.selectedKeys()) {
                if (sk.isAcceptable()) {
                    accept();
                } else if (sk.isReadable()) {
                    repeat(sk);
                }
            }
            selector.selectedKeys().clear();
        }
    }

    private void repeat(SelectionKey sk) throws IOException {

        SocketChannel sc = (SocketChannel) sk.channel();
        int n = sc.read(bb);
        System.out.println(n);
        if (n < 0) {
            System.out.println("Client Leave");
            sk.cancel();
            sc.close();
            return;
        }
        bb.flip();
        CharBuffer cb = charset.decode(bb);
        System.out.println(cb.toString());
        writeToAllClient();
        bb.clear();
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(2020);
        server.run();
    }

    private void writeToAllClient() throws IOException {
        for (SelectionKey s : selector.keys()) {
            if (s.isReadable()) {
                bb.rewind();
                SocketChannel clientsock = (SocketChannel) s.channel();
                clientsock.write(bb);

            }
        }
    }

}
