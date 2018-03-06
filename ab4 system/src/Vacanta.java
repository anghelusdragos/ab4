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
	
	/*
	 ->in functie de numele locatiei dorite si in functie de perioada aleasa functia returneaza maxim 5 locatii in
	 functie de costul total pentru acea perioada
	 ->caut numele locatiei in structura ierarhica a locului, iar daca o gaseste verific daca e disponibila in perioada
	 dorita. Daca ambele conditii sunt adevarate, adaug acea locatie intr-un vector pe care il sortez descrescator in
	 functie de pret.
	 ->la final returnez toate informatiile despre primele 5 locatii
	 */
	public String top_locatii(String nume_oras, String perioada) {
		Vector<Locatie> aux = new Vector<>();
		for (Locatie locatie:locatii) {
			if (locatie.getLocatie().toUpperCase().contains(nume_oras.toUpperCase()))
				if (locatie.CheckPerioada(perioada))
					aux.add(locatie);
		}
		Collections.sort(aux, new Comparator() {

			@Override
			public int compare(Object o1, Object o2) {
				Locatie locatie1 = (Locatie) o1;
				Locatie locatie2 = (Locatie) o2;
				return locatie2.getPret() - locatie1.getPret();
			}
		});
		String rez = "Top 5 locatii: " + "\n";
		for (int i = 0; i < aux.size(); i++) {
			rez += i + 1 + ":";
			rez += aux.get(i).toString() + "\n";
			if (i == 4)
				break;
		}
		return rez;
	}
	/*
	  	->functie care verifica daca perioada trimisa ca parametru este inclusa in perioada disponibila locatiei
		->funcita testeaza combinatiile posibile de date: incluse, egale si gresite
	*/
	public void test_perioada() {
		System.out.println(locatii.get(0).CheckPerioada("01.01.2018->01.01.2019"));
		System.out.println(locatii.get(0).CheckPerioada("01.02.2018->01.01.2019"));
		System.out.println(locatii.get(0).CheckPerioada("01.02.2018->01.12.2018"));
		System.out.println(locatii.get(0).CheckPerioada("01.01.2017->01.01.2019"));
		System.out.println();
	}

	public void test_locatie() {
		System.out.println(top_locatii("Romania", "05.06.2018->04.07.2018"));
		System.out.println(top_locatii("Maramures", "02.04.2018->05.05.2018"));
	}
	public static void main (String[] args) {
		Vacanta v = new Vacanta();
		v.citire_fisier("locatii.txt");
		v.test_informatii_locatie();
		v.test_perioada();
		v.test_locatie();
	}
}
