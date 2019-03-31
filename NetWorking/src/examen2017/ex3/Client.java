/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen2017.ex3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 *
 * @author Marine
 *
 *
 */
public class Client {
    // GET /getEvent/1 HTTP/1.1
    // host : prog-reseau-m1.lacl.fr

    // GET /getEventsByDates/4412 HTTP/1.1
    // host : prog-reseau-m1.lacl.fr
    // GET /getAllEvents HTTP/1.1
    // host : prog-reseau-m1.lacl.fr
    public Client(String url) throws IOException {
        SocketChannel client = SocketChannel.open();
        InetSocketAddress addr = new InetSocketAddress(url, 80);
        client.connect(addr);
    }
}
