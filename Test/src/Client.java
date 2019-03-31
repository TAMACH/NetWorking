
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Client implements Runnable {

    public static final String PATH = Paths.get(".").toAbsolutePath().normalize().toString();
    SocketChannel socketChannel;
    ByteBuffer buffer = ByteBuffer.allocate(2048);
    HashMap<Integer, String> Paires_connectes;
    HashMap<String, Long> Fichiers_partages = new HashMap<>();

    public Client(String url, int port) throws IOException {
        SocketAddress adresse = new InetSocketAddress(url, port);
        socketChannel = socketChannel.open();
        socketChannel.connect(adresse);

    }

    /**
     * Fonction Demande_Paires: Demender les paires connectés
     *
     */
    public void Demande_Paires() throws IOException {
        buffer.clear();
        buffer.put((byte) 3);
        buffer.flip();
        socketChannel.write(buffer);

    }

    /**
     * Fonction Recuperer_Paires: Recuperer les paires connectés
     *
     */
    public void Recuperer_Paires() {
        int nbpair = buffer.getInt();
        if (nbpair == 0) {
            System.out.println("aucune paire connecté");
            return;
        }
        System.out.println("nombre de client connecté " + nbpair);
        Deserialisation.desarialiserPaire(buffer, nbpair);

    }

    /**
     * Fonction Demande_Fichier : demande les fichiers partagés.
     *
     */
    public void Demander_Fichiers() throws IOException {
        buffer.clear();
        buffer.put((byte) 5);
        buffer.flip();
        socketChannel.write(buffer);
    }

    /**
     * Fonction Recuperer_Fichier : Recuperer les fichier partagés
     *
     */
    public void Recuperer_Fichier() {
        int nbfichier = buffer.getInt();
        if (nbfichier == 0) {
            System.out.println("Aucun fichier retrouver ");
            return;
        }
        System.out.println("nombre de fichiers " + nbfichier);
        Deserialisation.desarialiserficher(buffer, nbfichier);
    }

    /**
     * Demander_fragments : demander un fragments des fichiers partagés , en
     * envoyant le nom du fichier la taille totale et la position du début du
     * fragment et la taille à recuperer
     *
     */
    public void Demander_Fragement(String nom_fichier, long taille_fichier, long position, int taille_fragment) throws IOException {

        buffer.clear();
        buffer.put((byte) 7);
        Serialisation.Serialiserchaine(nom_fichier, buffer);
        buffer.putLong(taille_fichier);
        buffer.putLong(position);
        buffer.putInt(taille_fragment);
        buffer.flip();
        socketChannel.write(buffer);

    }

    /**
     * Recuperer_fragment : recupere le fragment aprés avoir choisi le fichier
     *
     */
    public void Recuperer_Fragment() throws SQLException, IOException {
        String nom_fichier = Deserialisation.desarialisationchaine(buffer);
        long taille_fichier = buffer.getLong();
        long position = buffer.getLong();
        int taille_fragment = buffer.getInt();
        byte[] blob = new byte[taille_fragment];
        for (int i = 0; i < taille_fragment; i++) {
            blob[i] = buffer.get();
        }
        File file = new File(PATH + nom_fichier);
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(blob);
        fo.flush();
        fo.close();
        FileInputStream fileInputStream = new FileInputStream(file);

    }

    /**
     *
     * Demande d'ajout a la liste des paire
     */
    public void Demande_Ajout() throws IOException {
        buffer.clear();
        buffer.put((byte) 2);
        buffer.putInt(1234);
        buffer.flip();
        socketChannel.write(buffer);
    }

    /**
     * Envoyer la liste des paires connectés
     *
     * @throws IOException
     */
    public void fournir_listePaire() throws IOException {
        buffer.clear();
        buffer.put((byte) 4);
        buffer.putInt(Paires_connectes.size());
        Serialisation.SerialisationListClient(Paires_connectes, buffer);
        buffer.flip();
        socketChannel.write(buffer);

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
    public void fournir_listeFichier() throws IOException {
        System.out.println("je suis dans fournir liste fichier");
        Recuperer_MesFichiers();
        buffer.clear();
        buffer.put((byte) 6);
        buffer.putInt(Fichiers_partages.size());
        System.out.println("la taille des fichiers est " + Fichiers_partages.size());
        Serialisation.SerialisationListFichier(Fichiers_partages, buffer);
        buffer.flip();
        socketChannel.write(buffer);
        System.out.println("j ai envoyé liste de fichier");

    }

    /**
     * foutnir le fragment demander par le serveur
     *
     * @throws IOException
     */
    public void fragment_demander() throws IOException {
        String fichier_demander = Deserialisation.desarialisationchaine(buffer);
        Long Taille_total = buffer.getLong();
        int debut = (int) buffer.getLong();
        int taille_demander = buffer.getInt();
        byte Blob[] = new byte[taille_demander];
        FileInputStream file = new FileInputStream(new File(PATH + fichier_demander));
        file.read(Blob, debut, taille_demander);
        buffer.clear();
        buffer.put((byte) 8);
        Serialisation.Serialiserchaine(fichier_demander, buffer);
        buffer.putLong(Taille_total);
        buffer.putLong(debut);
        buffer.putInt(taille_demander);
        for (int i = 0; i < Blob.length; i++) {
            buffer.put(Blob[i]);
        }
        // buffer.put(Blob);
        buffer.flip();
        socketChannel.write(buffer);
        System.out.println("fichier envoyé");

        // System.out.println(fichier_demander +" : "+ Taille_total+" : " + debut+" : " + taille_demander);
    }

    public void run() {
        try {
            while (true) {
                buffer.clear();
                socketChannel.read(buffer);
                buffer.flip();
                byte id = buffer.get();

                if (id == 1) {
                    String message = Deserialisation.desarialisationchaine(buffer);
                    System.out.println("ID" + "=" + id + " " + message);
                    // Demander_Fichiers();
                    //Recuperer_MesFichiers();
                    //buffer.put((byte)1);

                }

                if (id == 4) {
                    System.out.println("je suis dans 4");
                    Recuperer_Paires();

                }
                if (id == 6) {
                    Recuperer_Fichier();

                }

                if (id == 8) {
                    Recuperer_Fragment();
                }
                /**
                 * Quand le server demande et on doit repondre
                 *
                 */
                if (id == 3) {
                    // fournir_listePaire();
                    System.out.println("demade 3");
                }

                if (id == 5) {
                    System.out.println("demande la liste des fichier");
                    fournir_listeFichier();
                }
                if (id == 7) {
                    System.out.println("demander un fragment ");
                    fragment_demander();

                }
                if (id >= 64 && id < 128) {
                    System.out.println(id);
                } else {
                    System.out.println(id);
//						buffer.clear();
//						buffer.put((byte)1);
//					 	Serialisation.Serialiserchaine("Message incomprehensive", buffer);
//						buffer.flip();
//						socketChannel.write(buffer);
//						socketChannel.close();
                }

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException, SQLException {
        //Thread t1 = new Thread(Client);
        Client c = new Client("localhost", 2020);
        //Client c= new Client("prog-reseau-m1.lacl.fr",5486);
        //Thread t1 = new Thread(c);
        c.run();
        //System.out.println(PATH);

    }

}
