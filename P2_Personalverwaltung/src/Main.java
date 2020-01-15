
//import models.impl.AdresseModel;
import models.impl.FirmaModel;
import views.impl.FirmaView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import controller.impl.FirmaController;

/**
 * <p>
 * Startklasse des Programms.
 * </p>
 * <p>
 * Erzeugt das Model (<tt>FirmaModel</tt>) und setzt den Namen der Software.
 * Daneben wird das Anzeigefenster (<tt>FirmaView</tt>) der Software erzeugt und
 * das Model als Parameter übergeben. Zuletzt erfolgt die Erzeugung der Logik
 * (<tt>FirmaController</tt>) mit Übergabe des Models und der View als
 * Parameter. Die Logik ruft das Anzeigefenster auf.
 * 
 * <p>
 * Es wird das MVC-Entwurfsmuster (<tt>MVC-Pattern</tt>) mit Beobachterkonzept
 * (<tt>Observer-Pattern</tt>) innerhalb dieser Software angewandt.
 * </p>
 * 
 * @see models.impl.FirmaModel
 * @see views.impl.FirmaView
 * @see controller.impl.FirmaController
 * 
 * @author Jens Sterk
 * @version 1.0
 *
 */
public class Main {

	/**
	 * Startpunkt der Java-VM für diese Software.
	 * 
	 * @param args keine
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		// Model erzeugen und Namen der Software setzen
		FirmaModel firma = new FirmaModel();

		firma.setName("Personalverwaltung");

		// View der Software erzeugen
		FirmaView firmaFenster = new FirmaView(firma);

		// Logik der Software erzeugen
		FirmaController firmaLogik = new FirmaController(firma, firmaFenster);
		firmaFenster.setController(firmaLogik);

		// Anzeigefenster darstellen
		firmaLogik.zeigeFenster();

		// Gehaltsliste laden
		File gehaltsliste = new File("C:\\Gehaltstabelle\\Gehaltstabelle.csv");
		try {
			firmaLogik.leseGehaltstabelle(gehaltsliste);
		} catch (FileNotFoundException e) {
			System.out.println("Die Gehaltstabelle konnte nicht geladen werden");
			e.printStackTrace();
		}
	}

}
