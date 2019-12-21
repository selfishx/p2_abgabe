package events.impl;

import models.impl.FirmaModel;
import events.AbstractEvent;

/**
 * Konkrete Implementierung eines Events.
 * Diese Klasse repräsentiert das konkrete Event <tt>FirmaData</tt>.
 * Es enthält Informationen, wenn sich die Firmadaten verändern
 * (<tt>Firma-Objekt</tt>).
 * Der <tt>EventType</tt> definiert, um welche Aktion es sich handelt.
 * 
 * @author  Jens Sterk
 * @version 1.0
 *
 */
public class FirmaDataEvent extends AbstractEvent {


	/**
	 * Constructor.
	 * Erzeugt ein FirmaDataEvent-Objekt und befüllt es mit den notwendigen
	 * Informationen.
	 * Es muss der Typ des Events sowie das veränderte Firma-Objekt
	 * übergeben werden.
	 * 
	 * @param type	Art des Events
	 * @param model	Firma-Objekt
	 */
	public FirmaDataEvent(EventType type, FirmaModel model) {
		super(type, model);
	}


	/**
	 * Liefert das Firma-Objekt.
	 * 
	 * @return Firma-Objekt
	 */
	@Override
	public FirmaModel getData() {
		return (FirmaModel) this.data;
	}


	/**
	 * setzt das Firma-Objekt
	 * 
	 * @param data Das zu übermittelnde Firma-Objekt
	 */
	@Override
	public void setData(Object data) {
		if (data instanceof FirmaModel) {
			this.data = data;
		}
	}

}