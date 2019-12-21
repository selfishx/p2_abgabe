package events.impl;

import models.impl.PraxisModel;
import events.AbstractEvent;

/**
 * Konkrete Implementierung eines Events.
 * Diese Klasse repräsentiert das konkrete Event <tt>PraxisData</tt>.
 * Es enthält Informationen, wenn sich die Praxisdaten verändern
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
	 * Erzeugt ein PraxisDataEvent-Objekt und befüllt es mit den notwendigen
	 * Informationen.
	 * Es muss der Typ des Events sowie das veränderte Praxis-Objekt
	 * übergeben werden.
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
	 * @param data Das zu übermittelnde Praxis-Objekt
	 */
	@Override
	public void setData(Object data) {
		if (data instanceof PraxisModel) {
			this.data = data;
		}
	}

}