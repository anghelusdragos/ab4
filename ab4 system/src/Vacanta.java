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
	 	->iterez prin tot vectorul de locatii si verific daca activitatea dorita se afla printre vectorul de activitati
	 	a locatiei respective si daca numarul de zile este mai mare sau egal cu 10, daca da adaug locatia intr-un vector.
	 	->sortez crescator vectorul in functie de pret si returnez toate informatiile despre acea locatie
	 */
	public String activitate_zece_zile(String nume_activitate) {
		String rez = "";
		boolean check = false;
		Vector<Locatie> aux = new Vector<>();
		for (Locatie locatie:locatii) {
			if (locatie.getActivitati().contains(nume_activitate.toLowerCase()) && locatie.ten_days()) {
				check = true;
				aux.add(locatie);
			}
		}
		if (check == false)
			rez += "Activitatea " + nume_activitate + " nu este disponibila in nicio locatie pentru 10 zile de activitate";
		else {
			Collections.sort(aux, new Comparator() {

				@Override
				public int compare(Object o1, Object o2) {
					Locatie locatie1 = (Locatie) o1;
					Locatie locatie2 = (Locatie) o2;
					return locatie1.getPret() - locatie2.getPret();
				}
			});
			rez += aux.get(0).toString();
		}
		return rez;
	}
	
	//verific daca imi afiseaza informatiile dorite daca numele locatiei e scris corect sau contine majuscule
	public void test_informatii_locatie() {
		System.out.println("Test informatii locatie:");
		System.out.println(informatii_locatie("Mamaia"));
		informatii_locatie("maMaia");
		informatii_locatie("Cheile Bicazului");
	}
	
	/*
	  	->functie care verifica daca perioada trimisa ca parametru este inclusa in perioada disponibila locatiei
		->funcita testeaza combinatiile posibile de date: incluse, egale si gresite
	*/
	public void test_perioada() {
		System.out.println("Test perioada:");
		System.out.println(locatii.get(0).CheckPerioada("01.01.2018->01.01.2019"));
		System.out.println(locatii.get(0).CheckPerioada("01.02.2018->01.01.2019"));
		System.out.println(locatii.get(0).CheckPerioada("01.02.2018->01.12.2018"));
		System.out.println(locatii.get(0).CheckPerioada("01.01.2017->01.01.2019"));
		System.out.println();
	}

	public void test_locatie() {
		System.out.println("Test locatie:");
		System.out.println(top_locatii("Romania", "05.06.2018->04.07.2018"));
		System.out.println(top_locatii("Maramures", "02.04.2018->05.05.2018"));
	}
	
	public void test_ten_days() {
		System.out.println("Test 10 zile:");
		System.out.println(locatii.get(0).ten_days());
		System.out.println(locatii.get(locatii.size() - 1).ten_days());
		Locatie loc = new Locatie("Transfagarasanu","Romania->Arges->Pitesti",300,"excursie","14.12.2018->01.01.2019");
		System.out.println(loc.ten_days());
		Locatie loc1 = new Locatie("Transfagarasanu","Romania->Arges->Pitesti",300,"excursie","24.12.2018->01.01.2019");
		System.out.println(loc1.ten_days());
		System.out.println();
	}
	
	public void test_activitate() {
		System.out.println("Test activitate:");
		System.out.println(activitate_zece_zile("trasee montane"));
		System.out.println(activitate_zece_zile("fotografie"));
	}
	public static void main (String[] args) {
		Vacanta v = new Vacanta();
		v.citire_fisier("locatii.txt");
		v.test_informatii_locatie();
		v.test_perioada();
		v.test_locatie();
		v.test_ten_days();
		v.test_activitate();
	}
}
