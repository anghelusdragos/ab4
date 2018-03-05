import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.StringTokenizer;
import java.util.Vector;

public class Vacanta {
	//Vectorul de tip "Locatie" in care imi salvez locatiile citite din fisier
	Vector<Locatie> locatii;
	
	public Vacanta() {
		locatii = new Vector<>();
	}
	
	public void citire_fisier(String nume_fisier) {
		StringTokenizer st;
		String cuvant;
		FileReader f = null;
		LineNumberReader fr = null;
		Locatie locatie;
		try{
			//citesc cate o linie si o parsez in functie de ","
			f = new FileReader(nume_fisier);
			fr = new LineNumberReader(f);
			String linie = fr.readLine();
			while( linie != null){
				st = new StringTokenizer(linie, ",");
				cuvant = st.nextToken();
				locatie = new Locatie(cuvant,st.nextToken(), Integer.parseInt(st.nextToken()), st.nextToken(), st.nextToken());
				System.out.println(locatie);
				linie = fr.readLine();
			}
		} catch(FileNotFoundException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		} finally {
			try {
				if( f != null ) {
					f.close();
				}
				if( fr != null ) {
					fr.close();
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main (String[] args) {
		Vacanta v = new Vacanta();
		v.citire_fisier("locatii.txt");
	}
}
