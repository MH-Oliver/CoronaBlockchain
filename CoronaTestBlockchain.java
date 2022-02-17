import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class CoronaTestBlockchain {

	static ArrayList<Block> mHauptKassenbuch = null;
	public static String mTrenner="|";

	
	public static boolean checkBlockchain() {
		Block lAkutellerBlock;
		Block lVorgaengerBlock;
		
		for (int n=1;n<mHauptKassenbuch.size();n++) {
			lAkutellerBlock = mHauptKassenbuch.get(n);
			lVorgaengerBlock = mHauptKassenbuch.get(n - 1);
			
			if (!lAkutellerBlock.mBlockhash.equals(lAkutellerBlock.getHash())) {
				return false;
			}
			if (!lVorgaengerBlock.mBlockhash.equals(lAkutellerBlock.mVorgaengerHash)) {
				return false;
			}

			if ( !lAkutellerBlock.raetselGeloest() ) {
				return false;
			}
		}
		return true;
	}
	
	
	private static String getLastArrayHash() {
		if (mHauptKassenbuch.size()==0) {
			return "0000000000000000000000000000000000000000000000000000000000000000";
		} else {
			return mHauptKassenbuch.get(mHauptKassenbuch.size() - 1).mBlockhash;
		}
	}
	
	private static void displayHauptbuch() {
		System.out.println("");
		System.out.println("Hauptbuch anzeigen, Anzahldatensaetze="+mHauptKassenbuch.size());
		System.out.println("Nr|VorgaengerBlockHash                                              "+
		                     "| BlockHash                                                      "+
				             "| Data (LaufNr-Raetsel)");
		
		int nr=0;
		for (Block chain:mHauptKassenbuch) {
			nr++;
			System.out.println(" "+nr+"|"+chain.mVorgaengerHash+
					                  "|"+chain.mBlockhash+
					                  "| "+chain.mBlockData+" ("+chain.mLaufNrRaetsel+")");
		}

		System.out.println("");
		System.out.println("Blockchain korrekt: "+checkBlockchain());
		System.out.println("");
		
	}
	
	public static void main(String[] args) throws IOException {
		
		//Testbloecke beim Programmstart anlegen
		mHauptKassenbuch = new ArrayList<Block>();
		Block lBlock1=new Block( getLastArrayHash(), "Coronatest;Harry Potter;31.07.1980;10.02.2022;negativ");
		mHauptKassenbuch.add(lBlock1);

		Block lBlock2=new Block( getLastArrayHash(), "Coronatest;Hermine Granger;15.10.1981;11.02.2022;positiv" );
		mHauptKassenbuch.add(lBlock2);

		for (int n=1;n<5;n++) {
			Block lBlock=new Block( getLastArrayHash(), "Coronatest;Weasley Fred Nr-"+n+";"+n+".12.1982;13.02.2022;negativ" );
			mHauptKassenbuch.add(lBlock);
		}

		displayHauptbuch();
		
		String lMenue="";
		while (!lMenue.equals("q")) {
			System.out.println("--------------------------------------------------------------------------------------------");
			System.out.println("Menue: a=addBlock, d=showBlockChain, e=editBlock1(nur Daten), r=editBlock2(+HashGenerierung),");
			System.out.print  ("       l=lade/s=speichern Hauptbuch, f=Suche Blockfehler, q=Programmende, Eingabe= ");
			
			InputStreamReader isr = new InputStreamReader(System.in);
	        BufferedReader br = new BufferedReader(isr);
	        lMenue = br.readLine();
	        
	        if (lMenue.equals("d")) {
	        	displayHauptbuch();
	        } else if (lMenue.equals("a")) {
				System.out.print("AddBlock: neuer DataString=");
		        String lNewBlockData = br.readLine();
				Block lBlock=new Block( getLastArrayHash(), lNewBlockData );
				mHauptKassenbuch.add(lBlock);
				displayHauptbuch();
	        } else if (lMenue.equals("e")||lMenue.equals("r")) {
				System.out.print("EditBlock: Input BlockHash=");
		        String lBlockHash = br.readLine();
		        Block lBlock=null;
				for (int n=1;n<mHauptKassenbuch.size();n++) {
					if (mHauptKassenbuch.get(n).mBlockhash.equals(lBlockHash) ) {
						lBlock=mHauptKassenbuch.get(n);
						break;
					}
				}
				if (lBlock==null) {
					System.out.println("Block nicht gefunden! Hash falsch?");
				} else {
					System.out.println("Block gefunden. DataString="+lBlock.mBlockData+", Uhrzeit:"+lBlock.mLaufNrRaetsel);
					System.out.print("new DataString=");
			        String lDataString = br.readLine();
			        lBlock.mBlockData=lDataString;
			        //BlockHash neu generieren?
			        if (lMenue.equals("r")) {
				        lBlock.mBlockhash = lBlock.getHash();
			        }

			        displayHauptbuch();
				}
	        } else if (lMenue.equals("s")) {
				System.out.print("Speicher Dateiname:");
		        String lSaveDateiname = br.readLine();
		        BufferedWriter lBufferedWriter=null;
		        try {
		        	lBufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(lSaveDateiname)));
		        	for (int n=1;n<mHauptKassenbuch.size();n++) {
		        		Block lBlock=mHauptKassenbuch.get(n);
		        		lBufferedWriter.write(lBlock.mVorgaengerHash
		        				             +mTrenner+lBlock.mBlockhash
		        				             +mTrenner+lBlock.mBlockData
		        				             +mTrenner+String.valueOf(lBlock.mLaufNrRaetsel)
		        				             );
		        		lBufferedWriter.newLine();
		        	}
		        	lBufferedWriter.close();
		        } catch (IOException e) {
		        	System.out.println("IOException: "+e.getMessage());
		        }
				finally {
					if (lBufferedWriter!=null) {
				        try {
						lBufferedWriter.close();
				        } catch (IOException e) {
				        	System.out.println("IOException: "+e.getMessage());
				        }
					}
				}
	        	System.out.println("Datei: "+lSaveDateiname+" gespeichert.");
	        } else if (lMenue.equals("l")) {
				System.out.print("Lade Dateiname (keine Eingabe->Abbrechen) :");
		        String lLadeDateiname = br.readLine();
		        if (lLadeDateiname.length()>0) {
		        	BufferedReader lBufferedReader=null;
		        	try {
		        		mHauptKassenbuch=null;
		        		mHauptKassenbuch = new ArrayList<Block>();
		        		
		        		lBufferedReader = new BufferedReader(new FileReader(lLadeDateiname));
		        		String csvLine;
		        		
		        		while (null != (csvLine = lBufferedReader.readLine()) ) {
		        			String[] lSpalte = csvLine.split(mTrenner);
		        			Block lBlock=new Block();
		        			lBlock.mVorgaengerHash=lSpalte[0];
		        			lBlock.mBlockhash=lSpalte[1];
		        			lBlock.mBlockData=lSpalte[2];
		        			lBlock.mLaufNrRaetsel=Long.valueOf(lSpalte[3]);
		        			mHauptKassenbuch.add(lBlock);
		        		}
		        		lBufferedReader.close();
		        		
		        	} catch (IOException e) {
		        		System.out.println("IOException: "+e.getMessage());
		        	}
		        	finally {
		        		if (lBufferedReader!=null) {
		        			try {
		        				lBufferedReader.close();
		        			} catch (IOException e) {
		        				System.out.println("IOException: "+e.getMessage());
		        			}
		        		}
		        	}
		        	System.out.println("Datei: "+lLadeDateiname+" geladen.");
		        	displayHauptbuch();
		        }
	        } else if (lMenue.equals("f")) {
	    		Block lAkutellerBlock;
	    		Block lVorgaengerBlock;
	    		int lFehler=0;
	    		
	    		for (int n=1;n<mHauptKassenbuch.size();n++) {
	    			lAkutellerBlock = mHauptKassenbuch.get(n);
	    			lVorgaengerBlock = mHauptKassenbuch.get(n - 1);
	    			
	    			if (!lAkutellerBlock.mBlockhash.equals(lAkutellerBlock.getHash())) {
			        	System.out.println("Blockfehler gefunden: Zeile="+n);
			        	System.out.println("BlockHashwert weicht ab!");
			        	lFehler++;
	    			}
	    			if (!lVorgaengerBlock.mBlockhash.equals(lAkutellerBlock.mVorgaengerHash)) {
			        	System.out.println("Blockfehler gefunden: Zeile="+n);
			        	System.out.println("VorgaengerHash passt nicht!");
			        	lFehler++;
	    			}
	    			if (!lVorgaengerBlock.raetselGeloest() ) {
			        	System.out.println("Blockfehler! Raetzel nicht erfaellt! Zeile:"+n);
			        	lFehler++;
	    			}
	    		}
	        	System.out.println("Anzahl Fehler in BlockChain: "+lFehler);
			}
		}
		System.out.println("Ende");
	}
	
}
