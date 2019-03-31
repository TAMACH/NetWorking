/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp3;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 *
 * @author Maroine
 */
public class RepeatNetwork implements Runnable {

    private int SIZE = 512;
    private ByteBuffer bb;
    private Client client;
    private final Charset charset = Charset.forName("UTF-8");

    public RepeatNetwork(Client client) {
        bb = ByteBuffer.allocate(SIZE);
        this.client = client;

    }

    @Override
    public void run() {
        try {
            while (client.isConnected()) {
                int n = client.getSocket().read(bb);
                if (n < 0) {
                    System.out.println("Client Leave : " + client.getSocket().getRemoteAddress());
                    client.getSocket().close();
                    client.isConnected(false);
                    return;
                }
                bb.flip();
                CharBuffer cb = charset.decode(bb);
                System.out.println("You send this message :" + cb.toString());
                bb.clear();

            }
        } catch (IOException ex) {
            System.out.println("Client Leave : ");
            this.client.isConnected(false);
        }
    }

}
