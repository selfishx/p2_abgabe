package events.impl;

import models.impl.FirmaModel;
import events.AbstractEvent;

/**
 * Konkrete Implementierung eines Events.
 * Diese Klasse repr�sentiert das konkrete Event <tt>FirmaData</tt>.
 * Es enth�lt Informationen, wenn sich die Firmadaten ver�ndern
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
	 * Erzeugt ein FirmaDataEvent-Objekt und bef�llt es mit den notwendigen
	 * Informationen.
	 * Es muss der Typ des Events sowie das ver�nderte Firma-Objekt
	 * �bergeben werden.
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
	 * @param data Das zu �bermittelnde Firma-Objekt
	 */
	@Override
	public void setData(Object data) {
		if (data instanceof FirmaModel) {
			this.data = data;
		}
	}

}