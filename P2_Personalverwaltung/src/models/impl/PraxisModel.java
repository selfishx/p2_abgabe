package models.impl;

import java.util.ArrayList;

import models.AbstractModel;
import events.AbstractEvent.EventType;
import events.impl.PraxisDataEvent;
import events.impl.PraxisPatientEvent;

/**
 * Das PraxisModel repr�sentiert die Praxis innerhalb der Software. Die Praxis verwaltet Patienten 
 * (deren Stammdaten) und ihre Termine (Datum, Zeitpunkt). Hierf�r werden Methoden bereitgestellt, 
 * um Patienten der Praxis hinzuzuf�gen oder wieder zu entfernen.
 * 
 * Das PraxisModel ist gleichzeitig Teil des Beobachter-Konzeptes und kann von anderen Objekten 
 * "beobachtet" werden. Die Beobachter k�nnen sich als Listener �ber �nderungen informieren lassen
 *
 * @author  Jens Sterk
 * @version 1.0
 *
 */
public class PraxisModel extends AbstractModel {


	//Name der Praxis. Gilt gleichzeitig als Name der Software
	private String name;
	
	
	//Status, ob es �nderungen gab, die es zu Speichern gilt.
	private boolean isDirty;

	
	//Liste der Patienten, die von der Software verwaltet werden
	private final ArrayList<PatientModel> patienten;

	
	/**
	 * Erzeugt ein Objekt zur Speicherung von Daten, die f�r eine Praxis relevant sind.
	 * Dazu geh�ren Name der Praxis, Adresse der Praxis und eine Liste aller Patienten der Praxis
	 */
	public PraxisModel() {
		this.name = "";
		this.patienten = new ArrayList<PatientModel>(); //vor Benutzung initialisieren!
		setDirty(false);
	}


	/**
	 * F�gt der Praxis einen Patienten als PatientModel-Objekt hinzu.
	 * Alle angemeldeten Listener werden �ber die �nderung informiert.
	 * 
	 * @param patient Neuer Patient der Praxis
	 */
	public void addPatient(PatientModel patient) {
		this.patienten.add(patient);
		setChanged();
		this.notifyObservers(new PraxisPatientEvent(EventType.ADD, patient));
		setDirty(true);
	}
	

	/**
	 * Liefert die eigene Objektinstanz zur�ck
	 * 
	 * return PraxisModel
	 */
	@Override
	public PraxisModel getInstance() {
		return this;
	}


	/**
	 * Liefert die n�chst-m�gliche Patienten-Nr, die verf�gbar ist. Dabei gilt, es werden alle 
	 * Patienten und ihre Nr gepr�ft, um die derzeit h�chste Patienten-Nr zu ermitteln.
	 * Auf diese wird dann eins addiert und dies an den Aufrufer zur�ckgegeben
	 * 
	 * @return N�chste freie Patienten-Nr
	 */
	public int getNaechstePatientNr() {
		int zwischenspeicher = 0;
		for (int i = 0; i < this.patienten.size(); i++) {
			PatientModel patient = this.patienten.get(i);
			if (zwischenspeicher < patient.getNr()) {
				zwischenspeicher = patient.getNr();
			}
		}
		return zwischenspeicher + 1;
	}


	/**
	 * Liefert den Namen der Praxis zur�ck
	 * 
	 * @return Name der Praxis oder leerer String
	 */
	public String getName() {
		return this.name;
	}


	/**
	 * Liefert zu einer Patienten-Nr das passenden PatientenModel-Objekt oder NULL zur�ck.
	 * 
	 * @param nr Patienten-Nr eines Praxis-Patienten
	 * 
	 * @return Patient mit der angefragten Patienten-Nr oder NULL, wenn kein Patient existiert.
	 */
	public PatientModel getPatient(int nr) {
		for (int i = 0; i < this.patienten.size(); i++) {
			if (this.patienten.get(i).getNr() == nr) {
				return this.patienten.get(i);
			}
		}
		return null;
	}


	/**
	 * Liefert eine Liste aller Patienten der Praxis.
	 * 
	 * @return Liste aller Patienten
	 */
	public ArrayList<PatientModel> getPatientenListe() {
		return this.patienten;
	}


	/**
	 * Liefert eine Liste aller Patienten, die einem bestimmtem Geschlecht
	 * (0 = M�nnlich, 1 = weiblich) entsprechen.
	 * 
	 * @param geschlecht 0 = Mann, 1 = Frau
	 * 
	 * @return Liste aller Patienten eines Geschlechts
	 */
	public ArrayList<PatientModel> getPatientenListe(int geschlecht) {
		ArrayList<PatientModel> tmpPatienten = new ArrayList<PatientModel>();
		for (int i = 0; i < this.patienten.size(); i++) {
			if (this.patienten.get(i).getGeschlecht() == geschlecht) {
				tmpPatienten.add(this.patienten.get(i));
			}
		}
		return tmpPatienten;
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
	 * Entfernt einen spezifischen Patienten aus der
	 * Liste aller Patienten.
	 * 
	 * @param nummerPatient Nr des Patienten
	 */
	public void removePatient(int nummerPatient) {
		for (int i = this.patienten.size()-1; i>=0; i--) {
			//Wenn der Patient gefunden wurde,
			//informiere die Listener �ber die �nderung
			//und entferne dann den Patienten
			if (this.patienten.get(i).getNr() == nummerPatient) {
				setChanged();
				this.notifyObservers(new PraxisPatientEvent(EventType.REMOVE, 
						this.patienten.get(i)));
				this.patienten.remove(i);
				setDirty(true);
				return;
			}
		}
	}


	/**
	 * Entfernt alle Patienten der Praxis.
	 * Alle angemeldeten Listener werden �ber die �nderung informiert.
	 */
	public void removePatienten() {
		for (int i = this.patienten.size()-1; i >= 0 ; i--) {
			//Informiere die Listener �ber jede einzelne �nderung
			setChanged();
			this.notifyObservers(new PraxisPatientEvent(EventType.REMOVE, this.patienten.get(i)));
			this.patienten.remove(i);
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
		this.notifyObservers(new PraxisDataEvent(EventType.UPDATE, this));
	}


	/**
	 * Setzt den Namen der Praxis und informiert die angemeldeten Listener �ber die �nderung
	 * 
	 * @param name Name der Praxis
	 */
	public void setName(String name) {
		this.name = name;
		setChanged();
		this.notifyObservers(new PraxisDataEvent(EventType.UPDATE, this));
		setDirty(true);
	}

	
	/**
	 * Praxis-Objekte werden anhand ihres Praxisnamens
	 * sortiert, sofern dies ben�tigt wird.
	 * 
	 * @param amodel Zu vergleichendes Objekt, muss vom Typ PraxisModel sein.
	 * 
	 * @return 0: kein Unterscheid (feststellbar)
	 *         1: Aktuelles Objekt ist unterwertig
	 *        -1: �bergebenes Objekt ist unterwertig
	 */
	@Override
	public int compareTo(AbstractModel amodel) {
		
		if (!(amodel instanceof PraxisModel)) {
			return 0;
		}
		
		PraxisModel m = (PraxisModel) amodel;
		
		if (m.getName() == null && this.getName() == null) {
			return 0;
		}
		
		if (this.getName() == null) {
			return 1;
		}
		
		if (m.getName() == null) {
			return -1;
		}
		
		//compareTo-Methode der String-Klasse nutzen! Praxisname ist String.
		return this.getName().compareTo(m.getName());
	}
}