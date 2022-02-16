import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Random;

public class Block {
	String mVorgaengerHash;
	String mBlockhash;
	String mBlockData;
	long mLaufNrRaetsel;
	String mHashRaetsel  ="cafe0"; //"affe0", "Badchef", "acdc0" , "dead0" , "abba0" 
	//
	//Konstruktor 2  
	public Block(String  pVorgaengerHash, String pBlockData) {
		mVorgaengerHash = pVorgaengerHash;
		mBlockData = pBlockData;
		mLaufNrRaetsel = 0;//new Date().getTime();

		System.out.print("erzeuge neuen Block (R�tsel Hash["+mHashRaetsel+"*])...");
		mBlockhash = getHash();

		// schwierige Regel um Zeit zu gewinnen
		// Hier k�nnten jetzt viele Rechner mit rechnen
		// Wer die L�sung findet, bekommt eine Belohnung
		while (!raetselGel�st() ) {
			
			//Wie soll ein Client den Hashwert finden?

			//mLaufNrRaetsel = new Date().getTime(); // aus aktueller Uhrzeit umrechnen
			//mLaufNr++; //durch hochz�hlen
			mLaufNrRaetsel=new Random().nextLong();//ein Zufallszahl ausw�hlen
			
			//Hashwert mit akuteller LaufNrRaetsel neu berechnen
			mBlockhash = getHash();
		}
		System.out.println("gefunden!");
	}

	//Konstruktor 1 (f�rs laden)  
	public Block() {
	}
	
	public boolean raetselGel�st() {
		return ( mBlockhash.startsWith(mHashRaetsel) );
	}
	
	private static String bytesToHex(byte[] hash) {
	    StringBuilder hexString = new StringBuilder(2 * hash.length);
	    for (int i = 0; i < hash.length; i++) {
	        String hex = Integer.toHexString(0xff & hash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
	
	//SAH = Secure Hash Algorithm 
	//SHA-256 Hash berechnen (Alternativ MD5,SHA-1 ) 
	public static String getHashSha256(String pInputString) {
		try {
			final MessageDigest digest = MessageDigest.getInstance("SHA-256");
			final byte[] hashbytes = digest.digest(
					pInputString.getBytes(StandardCharsets.UTF_8));
			String sha3Hex = bytesToHex(hashbytes);
			return sha3Hex;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public String getHash() {
		//Hash zum Aktuellen Block (VorgaengerHash+LaufNrRaetsel+BlockData) bilden
		return getHashSha256( mVorgaengerHash
                +Long.toString(mLaufNrRaetsel) 
                +mBlockData 
	           );
	}
	
	
	
}
