package models;

import java.util.Observable;

/**
 * Diese Klasse dient als Oberklasse f�r alle Models innerhalb der Software.
 * Sie dient dazu, im sp�teren Verlauf �ber den gemeinsamen Typen AbstractModel die jeweiligen 
 * konkreten Models ansprechen zu k�nnen
 * 
 * Diese Klasse kann durch die Nutzung von "abstract" nicht eigenst�ndig instanziiert werden, 
 * sondern ben�tigt eine konkrete Implementierung.
 * 
 * Die konkreten Implementierungen, die von dieser Klasse erben, m�ssen die Methode getInstance 
 * zwingend umsetzen.
 * 
 * @author  Jens Sterk
 * @version 1.0
 * 
 */
public abstract class AbstractModel extends Observable implements Comparable<AbstractModel> {

	/**
	 * Methode, die von allen Klassen, die diese abstrakte Klasse erweitern, implementiert werden 
	 * muss. Sie sollte die eigene Instanz der konkreten Implementierung zur�ckliefern.
	 * 
	 * @return Instanz der konkreten Implementierung
	 */
	public abstract AbstractModel getInstance();
	
	@Override
	public abstract int compareTo(AbstractModel model);

}