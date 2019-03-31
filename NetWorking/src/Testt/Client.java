package Testt;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import tdSerialisation.Creator;
import tdSerialisation.MySerialisable;
import tdSerialisation.SerializerBuffer;

class Message implements MySerialisable {

    String pseudo;
    int nb;
    String message;
    Message next = null;

    //private Message(){};
    static public Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message init() {
            return new Message();
        }
    };

    public Message(String pseudo, int nb, String message, Message next) {
        this.pseudo = pseudo;
        this.nb = nb;
        this.message = message;
        this.next = next;
    }

    public Message(String pseudo, int nb, String message) {
        this(pseudo, nb, message, null);
    }

    private Message() {
    }

    @Override
    public String toString() {
        return "Message@" + System.identityHashCode(this) + "(" + pseudo + "," + nb + "," + message + "," + System.identityHashCode(next) + ")";
    }

    @Override
    public void writeToBuff(SerializerBuffer ms) {
        ms.writeString(pseudo);
        ms.writeInt(nb);
        ms.writeString(message);
        ms.writeMySerialisable(next);
    }

    @Override
    public void readFromBuff(SerializerBuffer ms) {
        pseudo = ms.readString();
        nb = ms.readInt();
        message = ms.readString();
        next = ms.readMySerialisable(Message.CREATOR);
    }

}

class RepeatKeyboard implements Runnable {

    SerializerBuffer bb = new SerializerBuffer(ByteBuffer.allocateDirect(512));
    ReadableByteChannel keyboard;
    Client c;
    int nb = 0;

    public RepeatKeyboard(Client c) {
        this.c = c;
        keyboard = Channels.newChannel(System.in);
    }

    @Override
    public void run() {
        try {
            while (c.getConnected()) {
                int n = keyboard.read(bb.bb);
                if (n < 0) {
                    System.out.println("Deconnection -1");
                    c.socket.close();
                    c.setConnected(false);
                    return;
                }
                bb.bb.flip();
                String message = Client.charset.decode(bb.bb).toString();
                bb.bb.clear();
                Message msg1 = new Message(c.pseudo, nb, message, null);
                nb++;
                Message msg2 = new Message(c.pseudo, nb, "test", msg1);
                nb++;
                msg1.next = msg2;
                bb.writeMySerialisable(msg1);
                bb.writeMySerialisable(msg2);
                bb.bb.flip();
                c.socket.write(bb.bb);
                bb.bb.clear();
            }
        } catch (IOException e) {
            System.out.println("Deconnection Exception" + e);
            c.setConnected(false);
        }
    }

}

class RepeatNetwork implements Runnable {

    SerializerBuffer bb = new SerializerBuffer(ByteBuffer.allocateDirect(512));
    Client c;

    public RepeatNetwork(Client c) {
        this.c = c;
    }

    @Override
    public void run() {
        try {
            while (c.getConnected()) {
                int n = c.socket.read(bb.bb);
                if (n < 0) {
                    System.out.println("Deconnection -1");
                    c.socket.close();
                    c.setConnected(false);
                    return;
                }
                bb.bb.flip();
                try {
                    while (bb.bb.remaining() > 0) {
                        Message t = bb.readMySerialisable(Message.CREATOR);
                        System.out.println(t);
                        bb.bb.mark();
                    }
                    bb.bb.clear();
                } catch (BufferUnderflowException e) {
                    bb.bb.reset();
                    bb.bb.compact();
                }
            }
        } catch (IOException e) {
            System.out.println("Deconnection Exception" + e);
            c.setConnected(false);
        }
    }

}

public class Client {

    public static final Charset charset = Charset.forName("UTF-8");

    boolean isConnected = false;
    SocketChannel socket;
    String pseudo = "toto";

    public boolean getConnected() {
        return isConnected;
    }

    public void setConnected(boolean is) {
        isConnected = is;
    }

    public Client(String url, int port) throws IOException {
        SocketAddress sa = new InetSocketAddress(url, port);
        socket = SocketChannel.open();
        socket.configureBlocking(true);
        socket.connect(sa);
        isConnected = true;
    }

    public static void main(String[] args) throws IOException {
        Client c = new Client("prog-reseau-m1.lacl.fr", 5486);
        System.out.println(c.socket);
        RepeatNetwork rn = new RepeatNetwork(c);
        RepeatKeyboard rk = new RepeatKeyboard(c);
        Thread t1 = new Thread(rn);
        Thread t2 = new Thread(rk);
        t1.start();
        t2.run();
    }

}
