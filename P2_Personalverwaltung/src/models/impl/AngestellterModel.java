package models.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import models.AbstractModel;
import events.AbstractEvent.EventType;
import events.impl.AngestellterDataEvent;

/**
 * Diese Klasse repräsentiert einen Angestellten im Programm. Ein Angestellter
 * enthält neben den Stammdaten auch eine Liste von Terminen. Die Klasse stellt
 * Methoden zum Setzen (<tt>set..</tt>) und Abfragen (<tt>get..</tt>) zur
 * Verfügung, um alle relevanten Informationen eines Angestellten abbilden zu
 * können. Weiter werden Methoden zum Hinzufügen und Entfernen von
 * Termin-Objekten ermöglicht
 * 
 * @author Jens Sterk
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

	// Angestellten-Nr des jeweiligen Angestellten
	private int nr;

	// Vorname des Angestellten
	private String vorname;

	// Nachname des Angestellten
	private String nachname;

	// Geschlecht des Angestellten
	private int geschlecht;

	// Geburtsdatum des Angestellten
	private String geburtsdatum;

	// Telefonnummer des Angestellten
	private String telefon;

	// Beste Entscheidung für eine Einstellung!
	public static boolean isGeil = false;
	public static boolean isGeil2 = false;

	// Erfahrungsstufe des Angestellten (Gibt Auskunft über die Zeit die der
	// Angestellte bereits im Betrieb arbeitet)
	private int erfahrungsStufe;

	// Gehaltsgruppe des Angestellten
	private int gehaltsGruppe;

	// Gehalt des Angestellten
	private float gehalt;

	/**
	 * Konstruktor. Erzeugt einen neuen Angestellten mit leerer Terminliste.
	 */
	public AngestellterModel() {
		// nothing
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
	 * Liefert das Geschlecht des Angestellten als Integer-Wert
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
	 * Liefert die Erfahrungsstufe des Angestellten
	 * 
	 * @return erfahrungsStufe
	 */
	public int getErfahrungsstufe() {
		return this.erfahrungsStufe;
	}

	/**
	 * Liefert die Gehaltsgruppe des Angestellten
	 * 
	 * @return gehaltsGruppe
	 */
	public int getGehaltsgruppe() {
		return this.gehaltsGruppe;
	}

	/**
	 * Setzt das Geburtsdatum des Angestellten. Wird ein ungültiges Datum
	 * eingegeben, wird eine Exception erzeugt und zurückgeliefert.
	 * 
	 * @param geburtsdatum Datum im Format dd.mm.yyyy
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
	 * Setzt das Geschlecht des Angestellten. 0 für Mann, 1 für Frau. Bei ungültiger
	 * Eingabe wird eine Exception an den Aufrufer zurückgeliefert.
	 * 
	 * @param geschlecht 0 = Mann, 1 = Frau
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
	 * Setzt die Angestellten-Nr. Die Nummer muss größer 0 sein, ansonsten wird eine
	 * Exception an den Aufrufer weitergeleitet.
	 * 
	 * @param nr Angestellten-Nr
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

	/**
	 * Setzt die Gehaltsgruppe des Angestellten.
	 * 
	 * @param gehaltsGruppe
	 */
	public void setGehaltsgruppe(int gehaltsGruppe) {
		this.gehaltsGruppe = gehaltsGruppe;
		setChanged();
		this.notifyObservers(new AngestellterDataEvent(EventType.UPDATE, this));
	}

	/**
	 * Setzt die Erfahrungsstufe des Angestellten.
	 * 
	 * @param erfahrungsStufe
	 */
	public void setErfahrungsstufe(int erfahrungsStufe) {
		this.erfahrungsStufe = erfahrungsStufe;
		setChanged();
		this.notifyObservers(new AngestellterDataEvent(EventType.UPDATE, this));
	}

	/**
	 * Liefert den Index der Gehaltsliste des Angestellten.
	 * 
	 * @param gehaltsGruppe
	 * @param erfahrungsStufe
	 * @return
	 */
	public int getGehaltIndex(int gehaltsGruppe, int erfahrungsStufe) {

		this.gehaltsGruppe = gehaltsGruppe;
		this.erfahrungsStufe = erfahrungsStufe;
		int i = 0;

		if (gehaltsGruppe == 1) {
			switch (erfahrungsStufe) {
			case 1:
				i = 0;
				break;
			case 2:
				i = 1;
				break;
			case 3:
				i = 2;
				break;
			case 4:
				i = 3;
				break;
			case 5:
				i = 4;
				break;
			case 6:
				i = 5;
				break;
			}
		}

		else if (gehaltsGruppe == 2) {
			switch (erfahrungsStufe) {
			case 1:
				i = 6;
				break;
			case 2:
				i = 7;
				break;
			case 3:
				i = 8;
				break;
			case 4:
				i = 9;
				break;
			case 5:
				i = 10;
				break;
			case 6:
				i = 11;
				break;
			}
		}

		else if (gehaltsGruppe == 3) {
			switch (erfahrungsStufe) {
			case 1:
				i = 12;
				break;
			case 2:
				i = 13;
				break;
			case 3:
				i = 14;
				break;
			case 4:
				i = 15;
				break;
			case 5:
				i = 16;
				break;
			case 6:
				i = 17;
				break;
			}
		}

		else if (gehaltsGruppe == 4) {
			switch (erfahrungsStufe) {
			case 1:
				i = 18;
				break;
			case 2:
				i = 19;
				break;
			case 3:
				i = 20;
				break;
			case 4:
				i = 21;
				break;
			case 5:
				i = 22;
				break;
			case 6:
				i = 23;
				break;
			}
		}

		else if (gehaltsGruppe == 5) {
			switch (erfahrungsStufe) {
			case 1:
				i = 24;
				break;
			case 2:
				i = 25;
				break;
			case 3:
				i = 26;
				break;
			case 4:
				i = 27;
				break;
			case 5:
				i = 28;
				break;
			case 6:
				i = 29;
				break;
			}
		}

		else if (gehaltsGruppe == 6) {
			switch (erfahrungsStufe) {
			case 1:
				i = 30;
				break;
			case 2:
				i = 31;
				break;
			case 3:
				i = 32;
				break;
			case 4:
				i = 33;
				break;
			case 5:
				i = 34;
				break;
			case 6:
				i = 35;
				break;
			}
		}

		return i;
	}

	/**
	 * Setzt das Gehalt des Angestellten
	 * 
	 * @param gehalt
	 */
	public void setGehalt(float gehalt) {
		this.gehalt = gehalt;
	}

	/**
	 * Liefert das Gehalt des Angestellten
	 * 
	 * @return
	 */
	public float getGehalt() {
		return gehalt;
	}

	/**
	 * Setzt unter bestimmten Vorraussetzungen den boolean Wert isGeil auf true ;)
	 * 
	 */
	private void setIsGeil() {

		isGeil = false;
		isGeil2 = false;

		if (vorname.equals("Dirk") == true && nachname.equals("Tellmann") == true && geburtsdatum.equals("23.05.1989")
				&& telefon.equals("01738943162") && geschlecht == 0) {
			isGeil = true;
		}
		if (vorname.equals("Kevin") == true && nachname.equals("Klein") == true && geburtsdatum.equals("23.12.1995")
				&& telefon.equals("015256187832") && geschlecht == 0) {
			isGeil = true;
		}
		if (vorname.equals("Jens") == true && nachname.equals("Sterk") == true && geburtsdatum.equals("20.07.1985")
				&& geschlecht == 0) {
			isGeil2 = true;
		}
	}

	/**
	 * Angestellter-Objekte werden anhand ihres Familiennamens sortiert, sofern dies
	 * benötigt wird.
	 * 
	 * @param amodel Zu vergleichendes Objekt, muss vom Typ AngestellterModel sein.
	 * 
	 * @return 0: kein Unterscheid (feststellbar) 1: Aktuelles Objekt ist
	 *         unterwertig -1: Übergebenes Objekt ist unterwertig
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

		// compareTo-Methode der String-Klasse nutzen! Familienname ist String.
		return this.getNachname().compareTo(m.getNachname());
	}

}