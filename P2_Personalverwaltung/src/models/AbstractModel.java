package models;

import java.util.Observable;

/**
 * Diese Klasse dient als Oberklasse für alle Models innerhalb der Software.
 * Sie dient dazu, im späteren Verlauf über den gemeinsamen Typen AbstractModel die jeweiligen 
 * konkreten Models ansprechen zu können
 * 
 * Diese Klasse kann durch die Nutzung von "abstract" nicht eigenständig instanziiert werden, 
 * sondern benötigt eine konkrete Implementierung.
 * 
 * Die konkreten Implementierungen, die von dieser Klasse erben, müssen die Methode getInstance 
 * zwingend umsetzen.
 * 
 * @author  Jens Sterk
 * @version 1.0
 * 
 */
public abstract class AbstractModel extends Observable implements Comparable<AbstractModel> {

	/**
	 * Methode, die von allen Klassen, die diese abstrakte Klasse erweitern, implementiert werden 
	 * muss. Sie sollte die eigene Instanz der konkreten Implementierung zurückliefern.
	 * 
	 * @return Instanz der konkreten Implementierung
	 */
	public abstract AbstractModel getInstance();
	
	@Override
	public abstract int compareTo(AbstractModel model);

}