package events;


/**
 * Die <tt>AbstractEvent</tt>-Klasse ist eine abstrakte Oberklasse
 * aller im Programm vorkommenden Events.
 * Die konkreten Implementierungen m�ssen bei Verwendung dieser abstrakten
 * Klasse die Methoden <tt>setData(Object data)</tt> und <tt>getData()</tt>
 * umsetzen.
 * Die konkreten Implementierungen k�nnen als Wrapper-Objekte genutzt werden,
 * um spezifische Informationen zu �bertragen.
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
	 * Liste der m�glichen Arten eines Events.
	 * Wird beim Versenden und Empfangen von Event-
	 * Benachrichtungen innerhalb der Anwendung ge-
	 * nutzt, um eindeutige Aktionen zu definieren.
	 * 
	 * Folgende M�glichkeiten stehen zur Verf�gung:
	 * 
	 * * CREATE: 	Ein neues Objekt wurde erzeugt.
	 * * READ:		Ein Objekt wurde eingelesen.
	 * * UPDATE:	Ein Objekt wurde aktualisiert.
	 * * DELETE:	Ein Objekt wurde gel�scht.
	 * * ADD:		Ein Objekt wurde hinzugef�gt.
	 * * REMOVE:	Ein Objekt wurde entfernt.
	 * 
	 * @author Jens Sterk
	 *
	 */
	public enum EventType {
		/**
		 * Beschreibt ein Event, das eine Objekterzeugung ausgel�st hat.
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
		 * Beschreibt ein Event, bei dem Objektinformationen gel�scht wurden.
		 */
		DELETE,

		/**
		 * Beschreibt ein Event, bei dem Informationen hinzugef�gt wurden.
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
	 * Erstellt eine Wrapper-Klasse f�r das Senden und
	 * Empfangen von Events mit definierten Typen und
	 * frei-�bertragbaren Daten-Objekten.
	 * Bedarf konkreter Implementierungen, um genutzt
	 * werden zu k�nnen.
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
	 * Setzt das Datenobjekt mit den zu �bertragenden
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