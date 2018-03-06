import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Collections;
import java.util.Comparator;
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
				locatii.addElement(locatie);
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
	
	/*->modulul care afiseaza inforamtiile despre o locatie in functie de numele ei
	  ->iterez prin vectorul de locatii pana cand numele locatiei este la fel cu numele locatiei dorite
	  ->returnez toate informatiile despre acea locatie*/
	public String informatii_locatie (String nume_locatie) {
		String rez = "";
		Locatie aux;
		boolean check = false;
		for (int i = 0; i < locatii.size(); i++) {
			aux = locatii.get(i);
			if (aux.getNume().toLowerCase().equals(nume_locatie.toLowerCase())) {
				rez += aux.toString();
				check = true;
				break;
			}
		}
		if (check) {
			return rez;
		}
		else
			return "Locatia cu numele " + nume_locatie + " nu a fost gasita";
	}
	
	//verific daca imi afiseaza informatiile dorite daca numele locatiei e scris corect sau contine majuscule
	public void test_informatii_locatie() {
		System.out.println(informatii_locatie("Mamaia"));
		informatii_locatie("maMaia");
		informatii_locatie("Cheile Bicazului");
	}
	
	public static void main (String[] args) {
		Vacanta v = new Vacanta();
		v.citire_fisier("locatii.txt");
		v.test_informatii_locatie();
	}
}
