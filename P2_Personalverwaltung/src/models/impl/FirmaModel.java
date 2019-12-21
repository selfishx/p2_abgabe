package models.impl;

import java.util.ArrayList;

import models.AbstractModel;
import events.AbstractEvent.EventType;
import events.impl.FirmaDataEvent;
import events.impl.FirmaAngestellterEvent;

/**
 * Das FirmaModel repr�sentiert die Firma innerhalb der Software. Die Firma verwaltet Angestellte 
 * (deren Stammdaten) und ihre Termine (Datum, Zeitpunkt). Hierf�r werden Methoden bereitgestellt, 
 * um Angestellte der Firma hinzuzuf�gen oder wieder zu entfernen.
 * 
 * Das FirmaModel ist gleichzeitig Teil des Beobachter-Konzeptes und kann von anderen Objekten 
 * "beobachtet" werden. Die Beobachter k�nnen sich als Listener �ber �nderungen informieren lassen
 *
 * @author  Jens Sterk
 * @version 1.0
 *
 */
public class FirmaModel extends AbstractModel {


	//Name der Firma. Gilt gleichzeitig als Name der Software
	private String name;
	
	
	//Status, ob es �nderungen gab, die es zu Speichern gilt.
	private boolean isDirty;

	
	//Liste der Angestellten, die von der Software verwaltet werden
	private final ArrayList<AngestellterModel> angestellte;

	
	/**
	 * Erzeugt ein Objekt zur Speicherung von Daten, die f�r eine Firma relevant sind.
	 * Dazu geh�ren Name der Firma, Adresse der Firma und eine Liste aller Angestellten der Firma
	 */
	public FirmaModel() {
		this.name = "";
		this.angestellte = new ArrayList<AngestellterModel>(); //vor Benutzung initialisieren!
		setDirty(false);
	}


	/**
	 * F�gt der Firma einen Angestellten als PatientModel-Objekt hinzu.
	 * Alle angemeldeten Listener werden �ber die �nderung informiert.
	 * 
	 * @param patient Neuer Patient der Firma
	 */
	public void addAngestellter(AngestellterModel patient) {
		this.angestellte.add(patient);
		setChanged();
		this.notifyObservers(new FirmaAngestellterEvent(EventType.ADD, patient));
		setDirty(true);
	}
	

	/**
	 * Liefert die eigene Objektinstanz zur�ck
	 * 
	 * return FirmaModel
	 */
	@Override
	public FirmaModel getInstance() {
		return this;
	}


	/**
	 * Liefert die n�chst-m�gliche Angestellten-Nr, die verf�gbar ist. Dabei gilt, es werden alle 
	 * Angestellten und ihre Nr gepr�ft, um die derzeit h�chste Angestellten-Nr zu ermitteln.
	 * Auf diese wird dann eins addiert und dies an den Aufrufer zur�ckgegeben
	 * 
	 * @return N�chste freie Angestellten-Nr
	 */
	public int getNaechstePatientNr() {
		int zwischenspeicher = 0;
		for (int i = 0; i < this.angestellte.size(); i++) {
			AngestellterModel patient = this.angestellte.get(i);
			if (zwischenspeicher < patient.getNr()) {
				zwischenspeicher = patient.getNr();
			}
		}
		return zwischenspeicher + 1;
	}


	/**
	 * Liefert den Namen der Firma zur�ck
	 * 
	 * @return Name der Firma oder leerer String
	 */
	public String getName() {
		return this.name;
	}


	/**
	 * Liefert zu einer Angestellten-Nr das passenden AngestelltenModel-Objekt oder NULL zur�ck.
	 * 
	 * @param nr Angestellten-Nr eines Firma-Angestellten
	 * 
	 * @return Patient mit der angefragten Angestellten-Nr oder NULL, wenn kein Patient existiert.
	 */
	public AngestellterModel getAngestellter(int nr) {
		for (int i = 0; i < this.angestellte.size(); i++) {
			if (this.angestellte.get(i).getNr() == nr) {
				return this.angestellte.get(i);
			}
		}
		return null;
	}


	/**
	 * Liefert eine Liste aller Angestellten der Firma.
	 * 
	 * @return Liste aller Angestellten
	 */
	public ArrayList<AngestellterModel> getAngestellteListe() {
		return this.angestellte;
	}


	/**
	 * Liefert eine Liste aller Angestellten, die einem bestimmtem Geschlecht
	 * (0 = M�nnlich, 1 = weiblich) entsprechen.
	 * 
	 * @param geschlecht 0 = Mann, 1 = Frau
	 * 
	 * @return Liste aller Angestellten eines Geschlechts
	 */
	public ArrayList<AngestellterModel> getAngestellteListe(int geschlecht) {
		ArrayList<AngestellterModel> tmpAngestellte = new ArrayList<AngestellterModel>();
		for (int i = 0; i < this.angestellte.size(); i++) {
			if (this.angestellte.get(i).getGeschlecht() == geschlecht) {
				tmpAngestellte.add(this.angestellte.get(i));
			}
		}
		return tmpAngestellte;
	}


	/**
	 * Liefert den Status, ob das Model ver�ndert wurde
	 * und daher gespeichert werden muss
	 * 
	 * @return true = Zustand hat sich ver�ndert, false = Zustand unver�ndert
	 */
	public boolean isDirty() {
		return this.isDirty;
	}


	/**
	 * Entfernt einen spezifischen Angestellten aus der
	 * Liste aller Angestellten.
	 * 
	 * @param nummerPatient Nr des Angestellten
	 */
	public void removePatient(int nummerPatient) {
		for (int i = this.angestellte.size()-1; i>=0; i--) {
			//Wenn der Patient gefunden wurde,
			//informiere die Listener �ber die �nderung
			//und entferne dann den Angestellten
			if (this.angestellte.get(i).getNr() == nummerPatient) {
				setChanged();
				this.notifyObservers(new FirmaAngestellterEvent(EventType.REMOVE, 
						this.angestellte.get(i)));
				this.angestellte.remove(i);
				setDirty(true);
				return;
			}
		}
	}


	/**
	 * Entfernt alle Angestellten der Firma.
	 * Alle angemeldeten Listener werden �ber die �nderung informiert.
	 */
	public void removeAngestellte() {
		for (int i = this.angestellte.size()-1; i >= 0 ; i--) {
			//Informiere die Listener �ber jede einzelne �nderung
			setChanged();
			this.notifyObservers(new FirmaAngestellterEvent(EventType.REMOVE, this.angestellte.get(i)));
			this.angestellte.remove(i);
		}
		setDirty(true);
	}


	/**
	 * Erlaubt das Setzen des Zustandes von isDirty.
	 * Abh�ngig vom Zustand von isDirty ergibt sich die Option zum Speichern
	 * 
	 * @param isDirty true = Zustand wurde ver�ndert.
	 */
	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
		setChanged();
		this.notifyObservers(new FirmaDataEvent(EventType.UPDATE, this));
	}


	/**
	 * Setzt den Namen der Firma und informiert die angemeldeten Listener �ber die �nderung
	 * 
	 * @param name Name der Firma
	 */
	public void setName(String name) {
		this.name = name;
		setChanged();
		this.notifyObservers(new FirmaDataEvent(EventType.UPDATE, this));
		setDirty(true);
	}

	
	/**
	 * Firma-Objekte werden anhand ihres Firmanamens
	 * sortiert, sofern dies ben�tigt wird.
	 * 
	 * @param amodel Zu vergleichendes Objekt, muss vom Typ FirmaModel sein.
	 * 
	 * @return 0: kein Unterscheid (feststellbar)
	 *         1: Aktuelles Objekt ist unterwertig
	 *        -1: �bergebenes Objekt ist unterwertig
	 */
	@Override
	public int compareTo(AbstractModel amodel) {
		
		if (!(amodel instanceof FirmaModel)) {
			return 0;
		}
		
		FirmaModel m = (FirmaModel) amodel;
		
		if (m.getName() == null && this.getName() == null) {
			return 0;
		}
		
		if (this.getName() == null) {
			return 1;
		}
		
		if (m.getName() == null) {
			return -1;
		}
		
		//compareTo-Methode der String-Klasse nutzen! Firmaname ist String.
		return this.getName().compareTo(m.getName());
	}
}