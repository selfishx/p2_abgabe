package models.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import models.AbstractModel;
import events.AbstractEvent.EventType;
import events.impl.PatientDataEvent;

/**
 * Diese Klasse repräsentiert einen Patienten im Programm.
 * Ein Patient enthält neben den Stammdaten auch eine Liste von Terminen.
 * Die Klasse stellt Methoden zum Setzen (<tt>set..</tt>) und Abfragen (<tt>get..</tt>)
 * zur Verfügung, um alle relevanten Informationen eines Patienten abbilden zu
 * können. Weiter werden Methoden zum Hinzufügen und Entfernen von Termin-Objekten ermöglicht
 * 
 * @author  Jens Sterk
 * @version 1.0
 * 
 */
public class PatientModel extends AbstractModel {


	@Override
	public String toString() {
		return vorname + " " + nachname;
	}


	/**
	 * Integer-Repräsentation für männliche Patienten
	 */
	public static final int MANN = 0;


	/**
	 * Integer-Repräsentation für weibliche Patienten
	 */
	public static final int FRAU = 1;


	//Patienten-Nr des jeweiligen Patienten
	private int nr;


	//Vorname des Patienten
	private String vorname;


	//Nachname des Patienten
	private String nachname;


	//Geschlecht des Patienten
	private int geschlecht;


	//Geburtsdatum des Patienten
	private String geburtsdatum;

	//Telefonnummer des Patienten
	private String telefon;


	/**
	 * Konstruktor.
	 * Erzeugt einen neuen Patienten mit leerer Terminliste.
	 */
	public PatientModel() {
		//nothing
	}


	/**
	 * Liefert das Geburtsdatum des Patienten
	 * 
	 * @return Geburtsdatum
	 */
	public String getGeburtsdatum() {
		return this.geburtsdatum;
	}


	/**
	 * Liefert das Geschlecht des Patienten
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
	 * return PatientnModel
	 */
	@Override
	public PatientModel getInstance() {
		return this;
	}


	/**
	 * Liefert den Nachnamen des Patienten
	 * 
	 * @return Nachname
	 */
	public String getNachname() {
		return this.nachname;
	}


	/**
	 * Liefert die Patientennummer
	 * 
	 * @return Patienten-Nr
	 */
	public int getNr() {
		return this.nr;
	}


	/**
	 * Liefert die Telefonnummer des Patienten
	 * 
	 * @return Telefonnummer
	 */
	public String getTelefon() {
		return this.telefon;
	}


	/**
	 * Liefert den Vornamen des Patienten
	 * 
	 * @return Vorname des Patienten
	 */
	public String getVorname() {
		return this.vorname;
	}


	/**
	 * Setzt das Geburtsdatum des Patienten.
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
		this.notifyObservers(new PatientDataEvent(EventType.UPDATE, this));
	}


	/**
	 * Setzt das Geschlecht des Patienten.
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
		this.notifyObservers(new PatientDataEvent(EventType.UPDATE, this));
	}


	/**
	 * Setzt den Nachnamen des Patienten
	 * 
	 * @param nachname Nachname
	 */
	public void setNachname(String nachname) {
		this.nachname = nachname;
		setChanged();
		this.notifyObservers(new PatientDataEvent(EventType.UPDATE, this));
	}


	/**
	 * Setzt die Patienten-Nr.
	 * Die Nummer muss größer 0 sein, ansonsten wird eine Exception an den Aufrufer weitergeleitet.
	 * 
	 * @param  nr		             Patienten-Nr
	 * @throws NumberFormatException Fehlerhafte Eingabe
	 */
	public void setNr(int nr) throws NumberFormatException {
		if (nr > 0) {
			this.nr = nr;
			setChanged();
			this.notifyObservers(new PatientDataEvent(EventType.UPDATE, this));
		} else {
			throw new NumberFormatException();
		}
	}


	/**
	 * Setzt die Telefonnummer des Patienten.
	 * 
	 * @param telefon Telefonnummer
	 */
	public void setTelefon(String telefon) {
		this.telefon = telefon;
		setChanged();
		this.notifyObservers(new PatientDataEvent(EventType.UPDATE, this));
	}


	/**
	 * Setzt den Vornamen des Patienten
	 * 
	 * @param vorname Vorname
	 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
		setChanged();
		this.notifyObservers(new PatientDataEvent(EventType.UPDATE, this));
	}

	
	/**
	 * Patient-Objekte werden anhand ihres Familiennamens
	 * sortiert, sofern dies benötigt wird.
	 * 
	 * @param amodel Zu vergleichendes Objekt, muss vom Typ PatientModel sein.
	 * 
	 * @return 0: kein Unterscheid (feststellbar)
	 *         1: Aktuelles Objekt ist unterwertig
	 *        -1: Übergebenes Objekt ist unterwertig
	 */
	@Override
	public int compareTo(AbstractModel amodel) {
		
		if (!(amodel instanceof PatientModel)) {
			return 0;
		}
		
		PatientModel m = (PatientModel) amodel;
		
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