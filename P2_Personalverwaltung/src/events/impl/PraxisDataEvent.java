package events.impl;

import models.impl.PraxisModel;
import events.AbstractEvent;

/**
 * Konkrete Implementierung eines Events.
 * Diese Klasse repr�sentiert das konkrete Event <tt>PraxisData</tt>.
 * Es enth�lt Informationen, wenn sich die Praxisdaten ver�ndern
 * (<tt>Praxis-Objekt</tt>).
 * Der <tt>EventType</tt> definiert, um welche Aktion es sich handelt.
 * 
 * @author  Jens Sterk
 * @version 1.0
 *
 */
public class PraxisDataEvent extends AbstractEvent {


	/**
	 * Constructor.
	 * Erzeugt ein PraxisDataEvent-Objekt und bef�llt es mit den notwendigen
	 * Informationen.
	 * Es muss der Typ des Events sowie das ver�nderte Praxis-Objekt
	 * �bergeben werden.
	 * 
	 * @param type	Art des Events
	 * @param model	Praxis-Objekt
	 */
	public PraxisDataEvent(EventType type, PraxisModel model) {
		super(type, model);
	}


	/**
	 * Liefert das Praxis-Objekt.
	 * 
	 * @return Praxis-Objekt
	 */
	@Override
	public PraxisModel getData() {
		return (PraxisModel) this.data;
	}


	/**
	 * setzt das Praxis-Objekt
	 * 
	 * @param data Das zu �bermittelnde Praxis-Objekt
	 */
	@Override
	public void setData(Object data) {
		if (data instanceof PraxisModel) {
			this.data = data;
		}
	}

}