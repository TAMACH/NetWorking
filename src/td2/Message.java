/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package td2;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 *
 * @author Maroine
 */
public class Message implements MySerialisable {

    String pseudo;
    int nb;
    String message;
    Charset charset = Charset.forName("UTF-8");
    public static final Creator<Message> CREATOR = () -> new Message();

    private Message() {

    }

    public Message(String pseudo, int nb, String message) {
        this.pseudo = pseudo;
        this.nb = nb;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message(" + pseudo + ", " + nb + ", " + message + ")";
    }

    @Override

    public void writeToBuff(SerializerBuffer ms) {
        ms.writeMySerialisable(this);
    }

    @Override
    public void readFromBuff(SerializerBuffer ms) {
        ms.readMySerialisable(Message.CREATOR);
    }

    public void writeMySerialisable(MySerialisable ms) {
        if (ms == null) {
        }
    }

    public <T extends MySerialisable> T readMySerialisable(Creator<T> creator) {
        T init = creator.init();
        //init.readFromBuff(this);
        return init;
    }

    public static void main(String[] args) {
        ByteBuffer bb = ByteBuffer.allocate(512);
        SerializerBuffer sb = new SerializerBuffer();

    }
}
