package events.impl;

import models.impl.AngestellterModel;
import events.AbstractEvent;

/**
 * Konkrete Implementierung eines Events.
 * Diese Klasse repr�sentiert das konkrete Event <tt>FirmaAngestellter</tt>.
 * Es enth�lt Informationen zu einem ge�nderten oder neu hinzugef�gtem
 * Angestellter und dessen Daten-Objekt (<tt>Angestellter-Objekt</tt>).
 * Der <tt>EventType</tt> definiert, um welche Aktion es sich handelt.
 * 
 * @author  Jens Sterk
 * @version 1.0
 *
 */
public class FirmaAngestellterEvent extends AbstractEvent {


	/**
	 * Constructor.
	 * Erzeugt ein FirmaAngestellterEvent-Objekt und bef�llt es mit den notwendigen
	 * Informationen.
	 * Es muss der Typ des Events sowie das ver�nderte Angestellter-Objekt
	 * �bergeben werden.
	 * 
	 * @param type	Art des Events
	 * @param model	Angestellter-Objekt
	 */
	public FirmaAngestellterEvent(EventType type, AngestellterModel model) {
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
	 * @param data Das zu �bermittelnde Angestellter-Objekt
	 */
	@Override
	public void setData(Object data) {
		if (data instanceof AngestellterModel) {
			this.data = data;
		}
	}

}