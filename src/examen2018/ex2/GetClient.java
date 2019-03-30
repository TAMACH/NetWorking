package examen2018.ex2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Maroine
 */
public class GetClient implements Runnable {

    SocketAddress addr;
    final int HTTP_PORT = 80;
    private final String url;
    private final ByteBuffer bb;
    final static Charset c = Charset.forName("ASCII");
    private SocketChannel socket;

    public GetClient(String url) {
        this.url = url;
        addr = new InetSocketAddress(url, HTTP_PORT);
        bb = ByteBuffer.allocate(1024);
    }

    public void sendGet() throws IOException {
        socket = SocketChannel.open();
        socket.connect(addr);

        String req
                = "GET / HTTP/1.1\n"
                + "Host: " + url + "\n\n";

        ByteBuffer encode = c.encode(req);
        bb.put(encode);
        bb.flip();
        socket.write(bb);
    }

    public void receiveGet() throws IOException {
        bb.clear();
        int n;
        while ((n = socket.read(bb)) > 0) {
            System.out.println(n);
            bb.limit(bb.position());
            bb.flip();
            CharBuffer decode = c.decode(bb);
            System.out.print(decode.toString());
            bb.clear();
        }
    }

    @Override
    public void run() {
        try {
            sendGet();
            receiveGet();
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }

    public static void main(String[] args) {
        GetClient r = new GetClient("www.google.com");
        r.run();
    }

}
