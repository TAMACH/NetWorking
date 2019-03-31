import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;

public class Deserialisation implements Serializable {
	public static final Charset c = Charset.forName("UTF-8");
/**
 * String deserialisationChaine : recupere le contenu du buffer et le deserialise
 * @param buffer
 * @return
 */
	public static String desarialisationchaine(ByteBuffer buffer) {

		int n = buffer.getInt();
		int lim = buffer.limit();
		buffer.limit(buffer.position() + n);
		String message = c.decode(buffer).toString();
		buffer.limit(lim);
		return message;

	}
	/**
	 * Deserialiser paires recuperer le contenu du byte buffer et le deserialiser et les 
	 * 
	 * @param bytebuffer
	 * @param nbpair
	 */

	public static void desarialiserPaire(ByteBuffer bytebuffer, int nbpair) {
		int port;
		String adresse;

		for (int i = 0; i < nbpair; i++) {
			port = bytebuffer.getInt();
			adresse = desarialisationchaine(bytebuffer);
			System.out.println(port + " : " + adresse + "\n");
			
		}
		

	}
	/**
	 * 
	 * @param bytebuffer
	 * @param nbfichier recuperer dans le byte buffer 
	 * 
	 */

	public static void desarialiserficher(ByteBuffer bytebuffer, int nbfichier) {
		long taille;
		String nomfichier;
		for (int i = 0; i < nbfichier; i++) {
			nomfichier = desarialisationchaine(bytebuffer);
			taille = bytebuffer.getLong();
			System.out.println(nomfichier + " : " + taille + "\n");

		}

	}

}
