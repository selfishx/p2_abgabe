package models.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import models.AbstractModel;
import events.AbstractEvent.EventType;
import events.impl.AngestellterDataEvent;

/**
 * Diese Klasse repräsentiert einen Angestellten im Programm.
 * Ein Angestellter enthält neben den Stammdaten auch eine Liste von Terminen.
 * Die Klasse stellt Methoden zum Setzen (<tt>set..</tt>) und Abfragen (<tt>get..</tt>)
 * zur Verfügung, um alle relevanten Informationen eines Angestellten abbilden zu
 * können. Weiter werden Methoden zum Hinzufügen und Entfernen von Termin-Objekten ermöglicht
 * 
 * @author  Jens Sterk
 * @version 1.0
 * 
 */
public class AngestellterModel extends AbstractModel {


	@Override
	public String toString() {
		return vorname + " " + nachname;
	}


	/**
	 * Integer-Repräsentation für männliche Angestellten
	 */
	public static final int MANN = 0;


	/**
	 * Integer-Repräsentation für weibliche Angestellten
	 */
	public static final int FRAU = 1;


	//Angestellten-Nr des jeweiligen Angestellten
	private int nr;


	//Vorname des Angestellten
	private String vorname;


	//Nachname des Angestellten
	private String nachname;


	//Geschlecht des Angestellten
	private int geschlecht;


	//Geburtsdatum des Angestellten
	private String geburtsdatum;

	//Telefonnummer des Angestellten
	private String telefon;
	
	//Beste Entscheidung für eine Einstellung!
	public static boolean isGeil = false;
	public static boolean isGeil2 = false;


	/**
	 * Konstruktor.
	 * Erzeugt einen neuen Angestellten mit leerer Terminliste.
	 */
	public AngestellterModel() {
		//nothing
	}


	/**
	 * Liefert das Geburtsdatum des Angestellten
	 * 
	 * @return Geburtsdatum
	 */
	public String getGeburtsdatum() {
		return this.geburtsdatum;
	}


	/**
	 * Liefert das Geschlecht des Angestellten
	 * als Integer-Wert
	 * 
	 * @return 0 = Mann, 1 = Frau
	 */
	public int getGeschlecht() {
		return this.geschlecht;
	}


	/**
	 * Liefert die eigene Objektinstanz zurück
	 * 
	 * return AngestellterModel
	 */
	@Override
	public AngestellterModel getInstance() {
		return this;
	}


	/**
	 * Liefert den Nachnamen des Angestellten
	 * 
	 * @return Nachname
	 */
	public String getNachname() {
		return this.nachname;
	}


	/**
	 * Liefert die Angestelltennnummer
	 * 
	 * @return Angestellten-Nr
	 */
	public int getNr() {
		return this.nr;
	}


	/**
	 * Liefert die Telefonnummer des Angestellten
	 * 
	 * @return Telefonnummer
	 */
	public String getTelefon() {
		return this.telefon;
	}


	/**
	 * Liefert den Vornamen des Angestellten
	 * 
	 * @return Vorname des Angestellten
	 */
	public String getVorname() {
		return this.vorname;
	}


	/**
	 * Setzt das Geburtsdatum des Angestellten.
	 * Wird ein ungültiges Datum eingegeben, wird eine Exception erzeugt und zurückgeliefert.
	 * 
	 * @param  geburtsdatum 		 Datum im Format dd.mm.yyyy
	 * @throws NumberFormatException Ungültiges Datum
	 */
	public void setGeburtsdatum(String geburtsdatum) throws NumberFormatException {
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		df.setLenient(false);
		try {
			df.parse(geburtsdatum);
			this.geburtsdatum = geburtsdatum;
		} catch (ParseException e) {
			throw new NumberFormatException();
		}
		setChanged();
		this.notifyObservers(new AngestellterDataEvent(EventType.UPDATE, this));
	}


	/**
	 * Setzt das Geschlecht des Angestellten.
	 * 0 für Mann, 1 für Frau. Bei ungültiger Eingabe wird eine Exception an den Aufrufer 
	 * zurückgeliefert.
	 * 
	 * @param  geschlecht            0 = Mann, 1 = Frau
	 * @throws NumberFormatException Fehlerhafte Eingabe
	 */
	public void setGeschlecht(int geschlecht) throws NumberFormatException {
		if (geschlecht != MANN && geschlecht != FRAU) {
			throw new NumberFormatException();
		} else {
			this.geschlecht = geschlecht;
		}
		setChanged();
		this.notifyObservers(new AngestellterDataEvent(EventType.UPDATE, this));
	}


	/**
	 * Setzt den Nachnamen des Angestellten
	 * 
	 * @param nachname Nachname
	 */
	public void setNachname(String nachname) {
		this.nachname = nachname;
		setChanged();
		this.notifyObservers(new AngestellterDataEvent(EventType.UPDATE, this));
	}


	/**
	 * Setzt die Angestellten-Nr.
	 * Die Nummer muss größer 0 sein, ansonsten wird eine Exception an den Aufrufer weitergeleitet.
	 * 
	 * @param  nr		             Angestellten-Nr
	 * @throws NumberFormatException Fehlerhafte Eingabe
	 */
	public void setNr(int nr) throws NumberFormatException {
		if (nr > 0) {
			this.nr = nr;
			setChanged();
			this.notifyObservers(new AngestellterDataEvent(EventType.UPDATE, this));
		} else {
			throw new NumberFormatException();
		}
	}


	/**
	 * Setzt die Telefonnummer des Angestellten.
	 * 
	 * @param telefon Telefonnummer
	 */
	public void setTelefon(String telefon) {
		this.telefon = telefon;
		setChanged();
		this.notifyObservers(new AngestellterDataEvent(EventType.UPDATE, this));
		setIsGeil();
	}


	/**
	 * Setzt den Vornamen des Angestellten
	 * 
	 * @param vorname Vorname
	 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
		setChanged();
		this.notifyObservers(new AngestellterDataEvent(EventType.UPDATE, this));
	}

	private void setIsGeil(){
		
		isGeil = false;
		isGeil2 = false;
		
		if(vorname.equals("Dirk") == true && nachname.equals("Tellmann") == true && geburtsdatum.equals("23.05.1989") && telefon.equals("01738943162") && geschlecht == 0) {
			isGeil = true;
		}
		if(vorname.equals("Kevin") == true && nachname.equals("Klein") == true && geburtsdatum.equals("23.12.1995") && telefon.equals("015256187832") && geschlecht == 0) {
			isGeil = true;
		}
		if(vorname.equals("Jens") == true && nachname.equals("Sterk") == true && geburtsdatum.equals("20.07.1985") && geschlecht == 0) {
			isGeil2 = true;
		}
	}
	
	/**
	 * Angestellter-Objekte werden anhand ihres Familiennamens
	 * sortiert, sofern dies benötigt wird.
	 * 
	 * @param amodel Zu vergleichendes Objekt, muss vom Typ AngestellterModel sein.
	 * 
	 * @return 0: kein Unterscheid (feststellbar)
	 *         1: Aktuelles Objekt ist unterwertig
	 *        -1: Übergebenes Objekt ist unterwertig
	 */
	@Override
	public int compareTo(AbstractModel amodel) {
		
		if (!(amodel instanceof AngestellterModel)) {
			return 0;
		}
		
		AngestellterModel m = (AngestellterModel) amodel;
		
		if (m.getNachname() == null && this.getNachname() == null) {
			return 0;
		}
		
		if (this.getNachname() == null) {
			return 1;
		}
		
		if (m.getNachname() == null) {
			return -1;
		}
		
		//compareTo-Methode der String-Klasse nutzen! Familienname ist String.
		return this.getNachname().compareTo(m.getNachname());
	}
	
}