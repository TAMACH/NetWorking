/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp3;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;

/**
 *
 * @author Maroine
 */
public class RepeatKeyboard implements Runnable {

    private final int SIZE = 512;
    private final ByteBuffer bb;
    private final Client client;
    private final Charset charset = Charset.forName("UTF-8");
    private final ReadableByteChannel readableByteChannel;

    public RepeatKeyboard(Client client) {
        bb = ByteBuffer.allocate(SIZE);
        this.client = client;
        readableByteChannel = Channels.newChannel(System.in);

    }

    @Override
    public void run() {
        try {
            while (true) {
                int n = readableByteChannel.read(bb);
                if (n < 0) {
                    System.out.println("Client Leave : ");
                    client.getSocket().close();
                    client.isConnected(false);
                    return;
                }
                bb.flip();
                client.getSocket().write(bb);
                bb.clear();
            }
        } catch (IOException ex) {
            System.out.println("Client Leave : ");
            this.client.isConnected(false);
        }
    }
}
