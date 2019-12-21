//import models.impl.AdresseModel;
import models.impl.PraxisModel;
import views.impl.PraxisView;
import controller.impl.PraxisController;

/**
 * <p>Startklasse des Programms.</p>
 * <p>Erzeugt das Model (<tt>PraxisModel</tt>) und setzt den Namen der Software.
 * Daneben wird das Anzeigefenster (<tt>PraxisView</tt>) der Software erzeugt und das Model als Parameter übergeben.
 * Zuletzt erfolgt die Erzeugung der Logik (<tt>PraxisController</tt>) mit Übergabe des Models und der View als Parameter.
 * Die Logik ruft das Anzeigefenster auf.
 * 
 * <p>Es wird das MVC-Entwurfsmuster (<tt>MVC-Pattern</tt>) mit Beobachterkonzept (<tt>Observer-Pattern</tt>) innerhalb dieser Software angewandt.</p>
 * 
 * @see models.impl.PraxisModel
 * @see views.impl.PraxisView
 * @see controller.impl.PraxisController
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
	 */
	public static void main(String[] args) {
		
		//Model erzeugen und Namen der Software setzen
		PraxisModel      praxis        = new PraxisModel();
		
		praxis.setName("Super-Duper-Programm");
		
		//View der Software erzeugen
		PraxisView       praxisFenster = new PraxisView(praxis);
		
		//Logik der Software erzeugen
		PraxisController praxisLogik   = new PraxisController(praxis, praxisFenster);
		praxisFenster.setController(praxisLogik);
		
		//Anzeigefenster darstellen
		praxisLogik.zeigeFenster();
	}

}

//hund
