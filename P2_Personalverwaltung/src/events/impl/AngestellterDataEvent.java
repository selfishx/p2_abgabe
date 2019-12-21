package events.impl;

import models.impl.AngestellterModel;
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
public class AngestellterDataEvent extends AbstractEvent {


	/**
	 * Konstruktor.
	 * Erzeugt ein AngestellterDataEvent-Objekt und befüllt es mit den notwendigen
	 * Informationen.
	 * Es muss der Typ des Events sowie das veränderte Angestellte-Objekt
	 * übergeben werden.
	 * 
	 * @param type	Art des Events
	 * @param model	Angestellter-Objekt
	 */
	public AngestellterDataEvent(EventType type, AngestellterModel model) {
		super(type, model);
	}


	/**
	 * Liefert das Angestellter-Objekt.
	 * 
	 * @return Angestellter-Objekt
	 */
	@Override
	public AngestellterModel getData() {
		return (AngestellterModel) this.data;
	}


	/**
	 * setzt das Angestellter-Objekt
	 * 
	 * @param data Das zu übermittelnde Angestellter-Objekt
	 */
	@Override
	public void setData(Object data) {
		if (data instanceof AngestellterModel) {
			this.data = data;
		}
	}

}