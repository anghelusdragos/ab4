import java.util.StringTokenizer;
import java.util.Vector;

public class Locatie {
	String nume;
	String tara;
	String judet;
	String oras;
	String locatie;
	int pret;
	Vector<String> activitati = new Vector<>();
	String start_date;
	String end_date;
	
	//Constructorul clasei care primeste informatiile despre o locatie si initializeaza valorile necesare acestui obiect
	public Locatie(String nume, String locatie, int pret, String lista_activitati, String data) {
		this.nume = nume;
		this.locatie = locatie;
		this.pret = pret;
		
		StringTokenizer st = new StringTokenizer(locatie, "->");
		String cuvant = st.nextToken();
		this.tara = cuvant;
		
		cuvant = st.nextToken();
		this.judet = cuvant;
		
		cuvant = st.nextToken();
		this.oras = cuvant;
		
		//creez vectorul de activitati pe care le poti face in aceasta locatie
		st = new StringTokenizer(lista_activitati, "->");
		while(st.hasMoreTokens()) {
			activitati.addElement(st.nextToken());
		}
		
		//perioada de timp disponibila
		st = new StringTokenizer(data, "->");
		this.start_date = st.nextToken();
		this.end_date = st.nextToken();
		
	}
	
	public String toString() {
		String s = "";
		s += this.nume + " " + this.tara + " " + this.judet + " " + this.oras + " " + this.pret + " " + this.start_date + " " + this.end_date;
		return s;
	}
}
