
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;

public class Server implements Runnable {

    /**
     * la classe Server
     */
    static public final Charset c = Charset.forName("UTF-8");
    public static final String PATH = Paths.get(".").toAbsolutePath().normalize().toString();
    ServerSocketChannel serversocketchannel;
    Selector selector;
    ByteBuffer bytebuffer;
    HashMap<Integer, String> Paires_connectes = new HashMap<>();
    HashMap<String, Long> Fichiers_partages = new HashMap<>();

    /**
     * Constructeur Selctor permet de surveiller plusieurs cannaux
     *
     * @param port
     * @throws IOException
     */
    public Server(int port) throws IOException {
        serversocketchannel = ServerSocketChannel.open();
        selector = Selector.open();
        bytebuffer = ByteBuffer.allocateDirect(512);
        SocketAddress sa = new InetSocketAddress(port);
        serversocketchannel.bind(sa);
        serversocketchannel.configureBlocking(false);
        serversocketchannel.register(selector, SelectionKey.OP_ACCEPT);

    }

    /**
     * Accepter la connexion et envoyer un Id = 1 et un message
     *
     * @throws IOException
     */
    public void accept() throws IOException {
        SocketChannel socketChannel = serversocketchannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);

        bytebuffer.clear();
        bytebuffer.put((byte) 1);
        String reponse = "You are connected to " + serversocketchannel.getLocalAddress().toString() + " From " + socketChannel.getRemoteAddress().toString();
        Serialisation.Serialiserchaine(reponse, bytebuffer);
        bytebuffer.flip();
        socketChannel.write(bytebuffer);

    }

    /**
     * Tant que le client et connecté on verifie le bytebuffer si le client
     * demande , On lui offre le service qu'il veut
     *
     * @param sk
     */
    /**
     * Envoyer la liste des paires connectés
     *
     * @throws IOException
     */
    public void fournir_listePaire(SocketChannel socketChannel) throws IOException {
        bytebuffer.clear();
        bytebuffer.put((byte) 4);
        bytebuffer.putInt(Paires_connectes.size());
        Serialisation.SerialisationListClient(Paires_connectes, bytebuffer);
        bytebuffer.flip();
        socketChannel.write(bytebuffer);

    }

    /**
     * Fournir la liste des fichier
     *
     */
    public void Recuperer_MesFichiers() {
        File repertoire = new File(PATH);
        File[] files = repertoire.listFiles();
        for (int i = 0; i < files.length; i++) {
            //System.out.println(files[i].getName());
            Fichiers_partages.put(files[i].getName(), files[i].length());
        }
    }

    /**
     * Fournir la liste des fichier au serveur demandeur
     *
     * @throws IOException
     */
    public void fournir_listeFichier(SocketChannel socketChannel) throws IOException {
        System.out.println("je suis dans fournir liste fichier");
        Recuperer_MesFichiers();
        bytebuffer.clear();
        bytebuffer.put((byte) 6);
        bytebuffer.putInt(Fichiers_partages.size());
        System.out.println("la taille des fichiers est " + Fichiers_partages.size());
        Serialisation.SerialisationListFichier(Fichiers_partages, bytebuffer);
        bytebuffer.flip();
        socketChannel.write(bytebuffer);
        System.out.println("j ai envoyé liste de fichier");

    }

    public void fragment_demander(SocketChannel socketChannel) throws IOException {
        String fichier_demander = Deserialisation.desarialisationchaine(bytebuffer);
        Long Taille_total = bytebuffer.getLong();
        int debut = (int) bytebuffer.getLong();
        int taille_demander = bytebuffer.getInt();
        byte Blob[] = new byte[taille_demander];
        FileInputStream file = new FileInputStream(new File(PATH + fichier_demander));
        file.read(Blob, debut, taille_demander);
        bytebuffer.clear();
        bytebuffer.put((byte) 8);
        Serialisation.Serialiserchaine(fichier_demander, bytebuffer);
        bytebuffer.putLong(Taille_total);
        bytebuffer.putLong(debut);
        bytebuffer.putInt(taille_demander);
        for (int i = 0; i < Blob.length; i++) {
            bytebuffer.put(Blob[i]);
        }
        // buffer.put(Blob);
        bytebuffer.flip();
        socketChannel.write(bytebuffer);
        System.out.println("fichier envoyé");

        // System.out.println(fichier_demander +" : "+ Taille_total+" : " + debut+" : " + taille_demander);
    }

    public void repeat(SelectionKey sk) throws IOException {
        SocketChannel sc = (SocketChannel) sk.channel();
        bytebuffer.clear();
        sc.read(bytebuffer);

        bytebuffer.flip();
        byte ID = bytebuffer.get();
        System.out.println(ID);

        if (ID == 2) {
            System.out.println("envoi 2");
            int port = bytebuffer.getInt();
            Paires_connectes.put(port, sc.getRemoteAddress().toString());
            bytebuffer.clear();
            bytebuffer.put((byte) 1);
            sc.write(bytebuffer);
        }
        if (ID == 3) {
            System.out.println("j envoi le 4");
            fournir_listePaire(sc);
        }
        if (ID == 5) {
            System.out.println("j envoi le 6");
            fournir_listeFichier(sc);

        }
        if (ID == 7) {
            System.out.println("j envoi le 8");
            fragment_demander(sc);
        }

    }

    public void run() {
        try {

            while (true) {
                selector.select();
                for (SelectionKey sk : selector.selectedKeys()) {
                    if (sk.isAcceptable()) {
                        System.out.println("nouvelle connexion");
                        accept();
                    } else if (sk.isReadable()) {
                        //repeat(sk);
                    }
                }
                selector.selectedKeys().clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException, SQLException {

        Server server = new Server(2020);
        Thread t1 = new Thread(server);
        t1.start();

    }

}
