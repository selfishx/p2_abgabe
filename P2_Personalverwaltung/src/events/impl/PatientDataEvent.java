package events.impl;

import models.impl.PatientModel;
import events.AbstractEvent;

/**
 * Konkrete Implementierung eines Events.
 * Diese Klasse repräsentiert das konkrete Event <tt>PatientData</tt>.
 * Es enthält Informationen zu einem geänderten oder neu hinzugefügtem
 * Patienten und dessen Daten-Objekt (<tt>Patient-Objekt</tt>).
 * Der <tt>EventType</tt> definiert, um welche Aktion es sich handelt.
 * 
 * @author  Jens Sterk
 * @version 1.0
 *
 */
public class PatientDataEvent extends AbstractEvent {


	/**
	 * Konstruktor.
	 * Erzeugt ein PatientDataEvent-Objekt und befüllt es mit den notwendigen
	 * Informationen.
	 * Es muss der Typ des Events sowie das veränderte Patienten-Objekt
	 * übergeben werden.
	 * 
	 * @param type	Art des Events
	 * @param model	Patient-Objekt
	 */
	public PatientDataEvent(EventType type, PatientModel model) {
		super(type, model);
	}


	/**
	 * Liefert das Patient-Objekt.
	 * 
	 * @return Patient-Objekt
	 */
	@Override
	public PatientModel getData() {
		return (PatientModel) this.data;
	}


	/**
	 * setzt das Patient-Objekt
	 * 
	 * @param data Das zu übermittelnde Patient-Objekt
	 */
	@Override
	public void setData(Object data) {
		if (data instanceof PatientModel) {
			this.data = data;
		}
	}

}