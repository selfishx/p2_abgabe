package events;


/**
 * Die <tt>AbstractEvent</tt>-Klasse ist eine abstrakte Oberklasse
 * aller im Programm vorkommenden Events.
 * Die konkreten Implementierungen müssen bei Verwendung dieser abstrakten
 * Klasse die Methoden <tt>setData(Object data)</tt> und <tt>getData()</tt>
 * umsetzen.
 * Die konkreten Implementierungen können als Wrapper-Objekte genutzt werden,
 * um spezifische Informationen zu übertragen.
 * Die abstrakte Klasse setzt in den jeweils zu implementierenden Methoden das
 * <tt>Object</tt> voraus, womit in konkreten Implementierungen die jeweils
 * notwendige Objektklasse angegeben werden kann (da sie implizit von Object erbt)
 * 
 * @author  Jens Sterk
 * @version 1.0
 *
 */
public abstract class AbstractEvent {


	/**
	 * Liste der möglichen Arten eines Events.
	 * Wird beim Versenden und Empfangen von Event-
	 * Benachrichtungen innerhalb der Anwendung ge-
	 * nutzt, um eindeutige Aktionen zu definieren.
	 * 
	 * Folgende Möglichkeiten stehen zur Verfügung:
	 * 
	 * * CREATE: 	Ein neues Objekt wurde erzeugt.
	 * * READ:		Ein Objekt wurde eingelesen.
	 * * UPDATE:	Ein Objekt wurde aktualisiert.
	 * * DELETE:	Ein Objekt wurde gelöscht.
	 * * ADD:		Ein Objekt wurde hinzugefügt.
	 * * REMOVE:	Ein Objekt wurde entfernt.
	 * 
	 * @author Jens Sterk
	 *
	 */
	public enum EventType {
		/**
		 * Beschreibt ein Event, das eine Objekterzeugung ausgelöst hat.
		 */
		CREATE,

		/**
		 * Beschreibt ein Event, das DAten eingelesen hat oder zum Einlesen
		 * bereitstellt.
		 */
		READ,

		/**
		 * Beschreibt ein Event, dass aktualisierte Daten bereitstellt.
		 */
		UPDATE,

		/**
		 * Beschreibt ein Event, bei dem Objektinformationen gelöscht wurden.
		 */
		DELETE,

		/**
		 * Beschreibt ein Event, bei dem Informationen hinzugefügt wurden.
		 */
		ADD,

		/**
		 * Beschreibt ein Event, bei dem Objekte bzw. Informationen ganz oder
		 * in Teilen entfernt wurden.
		 */
		REMOVE
	}


	//Art des Events
	private   EventType type;


	//Gespeicherte Informationen zum Event
	protected Object data;


	/**
	 * Konstruktor.
	 * Erstellt eine Wrapper-Klasse für das Senden und
	 * Empfangen von Events mit definierten Typen und
	 * frei-übertragbaren Daten-Objekten.
	 * Bedarf konkreter Implementierungen, um genutzt
	 * werden zu können.
	 * 
	 * @param type 	Art des Events
	 * @param data	Daten-Objekt zum Event
	 */
	public AbstractEvent(EventType type, Object data) {
		setType(type);
		setData(data);
	}


	/**
	 * Liefert die zum Event gespeicherten
	 * Informationen	 *
	 * 
	 * @return Daten-Objekt
	 */
	public abstract Object getData();


	/**
	 * Liefert die Art des Events
	 * 
	 * @return Art des Events
	 */
	public EventType getType() {
		return this.type;
	}


	/**
	 * Setzt das Datenobjekt mit den zu übertragenden
	 * Informationen
	 * 
	 * @param data Daten-Objekt zum Event
	 */
	public abstract void setData(Object data);


	/**
	 * Setzt den Identifikationstyp des Events.
	 * 
	 * @param type Art des Events
	 */
	public void setType(EventType type) {
		this.type = type;
	}

}