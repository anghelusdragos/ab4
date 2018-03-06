import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.StringTokenizer;
import java.util.Vector;

public class Locatie {
	private String nume;
	private String tara;
	private String judet;
	private String oras;
	private String locatie;
	private int pret;
	private Vector<String> activitati = new Vector<>();
	private LocalDate start_date;
	private LocalDate end_date;
	
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
		
        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		st = new StringTokenizer(data, "->");
		start_date = LocalDate.parse(st.nextToken(), myFormat);
		end_date = LocalDate.parse(st.nextToken(), myFormat);
	}
	
	public String getNume() {
		return this.nume;
	}
	
	public String getTara() {
		return this.tara;
	}
	
	public String getJudet() {
		return this.judet;
	}
	
	public String getOras() {
		return this.oras;
	}
	
	public String getLocatie() {
		return this.locatie;
	}
	
	public int getPret() {
		return this.pret;
	}
	
	public Vector<String> getActivitati(){
		return this.activitati;
	}
	
	public String getPerioada() {
		return start_date.toString() + "->" + end_date.toString();
	}
	
	//toate informatiile despre o locatie
	public String toString() {
		String rez = "";
		rez += "Nume: " + this.nume + "\n";
		rez += "Tara: " + this.tara + "\n";
		rez += "Judet: " + this.judet + "\n";
		rez += "Oras: " + this.oras + "\n";
		rez += "Locatie: " + this.locatie + "\n";
		rez += "Pret: " + this.pret + "\n";
		rez += "Activitati: ";
		for (int j = 0; j < activitati.size() - 1; j++) {
			rez += activitati.get(j) + ", ";
		}
		rez += activitati.get(activitati.size() - 1) + "\n";
		rez += "Periada: " + getPerioada() + "\n";
		return rez;
	}
	
	/*
	  ->aceasta functie verifica daca perioada primita ca parametru este inclusa in perioada disponibila de aceasta locatie
	  ->ma folosesc de LocalDate pentru a compara datele si daca data primita e inclusa in cea disponibila returnez true
	  altfel false.
	 */
	public boolean CheckPerioada(String perioada) {
		StringTokenizer st = new StringTokenizer(perioada, "->");
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		LocalDate incepe_date = LocalDate.parse(st.nextToken(), myFormat);
		LocalDate termina_date = LocalDate.parse(st.nextToken(), myFormat);
		if (start_date.isEqual(incepe_date)) {
			if (end_date.isAfter(termina_date) || end_date.isEqual(termina_date))
				return true;
			else 
				return false;
		}
		if (start_date.isBefore(incepe_date)) {
			if (end_date.isAfter(termina_date) || end_date.isEqual(termina_date))
				return true;
			else
				return false;
		} else
			return false;
	}
	
	/*
	 	->aceasta functie verifica daca perioada de timp este mai mare sau egala cu 10 zile
	 	->verific mai intai daca e diferenta de ani intre cele doua date. Daca da verific daca data de inceput e Decembrie
	 	si luna de final e Ianuarie, daca nu returnez adevarat, alfel calculez cate zile din luna Decembrie mi-au ramas
	 	si le adun cu zilele din Ianuarie. Daca aceasta suma e mai mare sau egala cu 10 returnez true.
	 	->daca avem acelasi an calculez diferenta zilelor. Daca e mai mare sau egala cu 10 returnez true altfel false.
	 */
	public boolean ten_days() {
		if (start_date.getYear() != end_date.getYear()) {
			if (start_date.getMonthValue() == 12 && end_date.getMonthValue() == 1) {
				int days = 31 - start_date.getDayOfMonth();
				days += end_date.getDayOfMonth();
				if (days >= 10)
					return true;
				else
					return false;
			}
			else
				return true;
		}
		int dif_zile = end_date.getDayOfYear() - start_date.getDayOfYear();
		if (dif_zile >= 10)
			return true;
		return false;
	}
}
