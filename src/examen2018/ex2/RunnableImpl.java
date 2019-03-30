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
public class RunnableImpl implements Runnable {

    SocketAddress addr;
    final int HTTP_PORT = 80;
    private final String url;
    private final ByteBuffer bb;
    final static Charset c = Charset.forName("ASCII");

    public RunnableImpl(String url) {
        this.url = url;
        addr = new InetSocketAddress(url, HTTP_PORT);
        bb = ByteBuffer.allocate(1024);
    }

    public void writeString(String s) {
        ByteBuffer encode = c.encode(s);
        bb.put(encode);

    }

    public String readString() {
        int remaining = bb.getInt();
        int limit = bb.position();
        bb.limit(limit + remaining);
        CharBuffer decode = c.decode(bb);
        bb.limit(limit);
        return decode.toString();
    }

    @Override
    public void run() {
        try {
            SocketChannel socket = SocketChannel.open();
            socket.connect(addr);

            String req
                    = "GET / HTTP/1.1\n"
                    + "Host: " + url + "\n\n";

            PrintWriter pw = new PrintWriter(socket.socket().getOutputStream());
            pw.print(req);
            pw.flush();
            int n;
            while ((n = socket.read(bb)) > 0) {
                bb.limit(bb.position());
                bb.flip();
                CharBuffer decode = c.decode(bb);
                System.out.print(decode.toString());
                bb.clear();
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }

    public static void main(String[] args) {
        RunnableImpl r = new RunnableImpl("www.google.com");
        r.run();
    }

}
