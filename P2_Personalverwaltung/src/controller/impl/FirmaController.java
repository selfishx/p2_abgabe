package controller.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import models.impl.AngestellterModel;
import models.impl.FirmaModel;
import views.impl.FirmaView;
import controller.AbstractController;

/**
 * Diese Klasse repräsentiert die Logik der Firma. Alle Aktivitäten innerhalb
 * der Software werden durch diese Klasse delegiert. Interaktionen des Benutzers
 * mit der grafischen Benutzeroberfläche (View) führen zu Methodenaufrufen in
 * dieser Klasse.
 * 
 * @author  Jens Sterk
 * @version 1.0
 *
 */
public class FirmaController extends AbstractController implements ActionListener {

	
	// Verweis auf das Hauptfenster, die FirmaView
	private final FirmaView firmaView;
	

	// Verweis auf das zugrundeliegende Model
	private FirmaModel firmaModel;

	
	/**
	 * Konstruktor des Controller. Es werden das Model und die View dem
	 * Controller als Parameter übergeben. Der Controller setzt sich innerhalb
	 * der View mittels der Funktion FirmaView.setController als
	 * Verantwortlicher für die auszuführende Logik.
	 * 
	 * @param firma		Model
	 * @param firmaView	View
	 */
	public FirmaController(FirmaModel firma, FirmaView firmaView) {
		this.firmaModel = firma; 
		this.firmaView = firmaView;
	}

	
	/**
	 * Diese Methode wird vom Interface <tt>ActionListener</tt> bereitgestellt
	 * und muss in der konkreten Umsetzung implementiert werden. Im konkreten
	 * Fall werden den vom Typ <tt>InterfaceView</tt> vererbten Views
	 * Möglichkeiten bereitgestellt, Actions innerhalb der View an den
	 * Controller zu delegieren.
	 * 
	 * @param event		Ausgelöstes ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		String actionCommand = event.getActionCommand();

		if (this.firmaView != null && actionCommand == this.firmaView.getBtnDateiLaden().getText()) {
			File datei = oeffneDateiDialog(0);
			if (datei != null) {
				resetFenster();
				try {
					leseDaten(datei);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(new JFrame(), "Es trat ein Fehler beim Auswerten der Daten auf. Abbruch!", "Fehler", 0);
					return;
				} catch (IOException e) {
					JOptionPane.showMessageDialog(new JFrame(), "Es trat ein Fehler beim Einlesen der Datei auf. Abbruch!", "Fehler", 0);
					return;
				}
			}
		}

		if (this.firmaView != null && actionCommand == this.firmaView.getBtnSpeichern().getText() && this.firmaModel.isDirty()) {
			File datei = oeffneDateiDialog(1);
			if (datei != null) {
				try {
					speichereDaten(datei);
					this.firmaModel.setDirty(false);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(new JFrame(), "Es trat ein Fehler beim Speichern der Datei auf. Abbruch!", "Fehler", 0);
					return;
				}
			}
		}
		
		//Neuer Angestellter Fenster aufrufen
		if (this.firmaView != null && actionCommand == this.firmaView.getBtnNeuerAngestellter().getText()) {

			firmaView.showAddMitarbeiterWindow();
		}
		
		//Button zum Hinzufügen eines neuen Angestellten im Dialog neuen Mitarbeiter hinzufügen
		if (this.firmaView != null && actionCommand == this.firmaView.getBtnSpeicherNeuenAngestellten().getText()) {
			
			firmaView.addNewAngestellter();	
			firmaView.contentAddAngestellter.dispose();
			firmaView.removeListener();
		}
			
		//Button um den Vorgang neuer Mitarbeiter abzubrechen
		if (this.firmaView != null && actionCommand == this.firmaView.getBtnAbbrechen().getText()) {
			firmaView.contentAddAngestellter.dispose();
			firmaView.contentEntferneAngestellten.dispose();
			firmaView.removeListener();

		}
		
		//Angestellter Entfernen Fenster aufrufen
		if (this.firmaView != null && actionCommand == this.firmaView.getBtnEntferneAngestellten().getText()) {
			
			firmaView.showEntferneAngestelltenWindow();
		}
		
		//Button um den Vorgang Angestellten entfernen zu bestätigen
				if (this.firmaView != null && actionCommand == this.firmaView.getBtnEntfernen().getText()) {

					int i = firmaView.getGewaehlterAngestellter().getNr();
					firmaModel.removeAngestellter(i);
					firmaView.contentEntferneAngestellten.dispose();

				}
				
				
		//Angestellter Bearbeiten aktivieren
				
				if (this.firmaView != null && actionCommand == this.firmaView.getBtnAngestellterBearbeiten().getText()) {
										
					firmaView.showAngestelltenBearbeiten();					
						
				}
		
		//Angestellter Bearbeiten bestätigen
				
				if (this.firmaView != null && actionCommand == this.firmaView.getBtnBestätigen().getText()) {
					
					int i = firmaView.getGewaehlterAngestellter().getNr();
					
					String nachname = firmaView.txtName.getText();
					String vorname = firmaView.txtVorname.getText();
					String telefon = firmaView.txtTelefon.getText();
					String gehaltsgruppe = (String) firmaView.boxGehaltsgruppe.getSelectedItem();
					String erfahrungsstufe = (String) firmaView.boxErfahrungsstufe.getSelectedItem();
					
					firmaModel.changeAngestellterDaten(i, nachname, vorname, telefon, gehaltsgruppe, erfahrungsstufe);
					firmaView.contentAddAngestellter.dispose();
					firmaView.removeListener();
																					
				}
		
	}

	
	/**
	 * Lädt das Gehalt des ausgewählten Angestellten
	 * 
	 * @param angestellter
	 * @return
	 */
	public float gehaltLaden(AngestellterModel angestellter) {
		AngestellterModel gewählterAngestellter = angestellter;
		int gehaltsGruppe = gewählterAngestellter.getGehaltsgruppe();
		int erfahrungsStufe = gewählterAngestellter.getErfahrungsstufe();
		int gehaltsIndex = gewählterAngestellter.getGehaltIndex(gehaltsGruppe, erfahrungsStufe);
		gewählterAngestellter.setGehalt(firmaModel.getGehaltIndex(gehaltsIndex));
		float gehalt = gewählterAngestellter.getGehalt();	
		return gehalt;
	}
	
	
	/**
	 * Setzt alle dargestellten Informationen zurück
	 */
	private void resetFenster() {
		this.firmaModel.removeAngestellte();
		this.firmaView.resetAngestellte();
	}

	
	/**
	 * Liefert das Objekt des aktuell in der GUI selektierten Patienten zurück
	 * 
	 * @return AngestellterModel
	 */
	public AngestellterModel getAktuellerAngestellter() {
		return this.firmaView.getGewaehlterAngestellter();
	}
	

	/**
	 * Ruft die grafische Oberfläche (GUI) der FirmaView auf.
	 */
	public void zeigeFenster() {
		this.firmaView.zeigeFenster();
	}
	

	/**
	 * Liefert das zugrundeliegende Model zurück
	 */
	public FirmaModel getModel() {
		return this.firmaModel;
	}

	
	/**
	 * Einlesen einer komma-separierten Datei (CSV) mit entsprechenden Informationen.
	 * Jede Zeile muss mit dem Kenner "objekt" beginnen
	 * @param datei
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	private void leseDaten(File datei) throws NumberFormatException, IOException {
		String zeile = "";
		AngestellterModel letzterAngestellter = null;
		
		this.firmaModel.removeAngestellte();

		//Datei einlesen
		BufferedReader dateiEinlesen = new BufferedReader(new FileReader(datei));
		
		//zeilenweises Abarbeiten
		while ((zeile = dateiEinlesen.readLine()) != null) {
			//Auftrennen der Informationen der jeweiligen Zeile. Komma dient als Separator
			String[] infos = zeile.split(",");

			//Debug-Ausgabe
			System.out.println(infos[0] + ", " + infos[1] + ", " + infos[2] + ", " + infos[3] + ", " + infos[4] + ", " + infos[5] + ", " + infos[6] + ", " + infos[7] + ", " + infos[8]);
			
			//Erster Eintrag muss immer "objekt" sein
			if (infos[0].equals("angestellter")) {

				//Jede Zeile enthält 9 Informationen
				if (infos.length != 9) {
					dateiEinlesen.close();
					throw new IOException();
				}				

				//Eingelesene Informationen in das Objekt füllen.
				letzterAngestellter = new AngestellterModel();
				try {
					letzterAngestellter.setNr(Integer.valueOf(infos[1]).intValue());
					letzterAngestellter.setVorname(infos[2]);
					letzterAngestellter.setNachname(infos[3]);
					letzterAngestellter.setGeburtsdatum(infos[4]);
					letzterAngestellter.setGeschlecht(Integer.valueOf(infos[5]).intValue());
					letzterAngestellter.setTelefon(infos[6]);
					letzterAngestellter.setGehaltsgruppe(Integer.valueOf(infos[7]).intValue());
					letzterAngestellter.setErfahrungsstufe(Integer.valueOf(infos[8]).intValue());
				} catch (Exception e) {
					dateiEinlesen.close();
					throw new IOException();
				}
				
				//Hinzufügen des neuen Objektes zur Liste aller Objekte
				this.firmaModel.addAngestellter(letzterAngestellter);
				letzterAngestellter = null;
			}
		}
		
		//Schließen des Streams.
		dateiEinlesen.close();
	}

	
	/**
	 * Einlesen der Gehaltstabelle beim Starten des Programms
	 * 
	 * @param datei
	 * @throws IOException
	 */
	public void leseGehaltstabelle(File datei) throws IOException {
		String zeile = "";
		String daten = "";
		
		//Datei einlesen
		try {
			BufferedReader dateiEinlesen = new BufferedReader(new FileReader(datei));
			zeile = dateiEinlesen.readLine();
			if(zeile.equals("gehälter")) {
			//zeilenweise abarbeiten
				while ((zeile = dateiEinlesen.readLine()) != null) {
					daten = ""+zeile;
					daten = daten.replaceFirst(",",".");			
					//Eingelesene Zeilen in die Gehaltsliste (Array gehaltsliste im FirmaModel) übertragen
					getModel().addGehaltsListe(Float.parseFloat(daten));
					System.out.println(""+zeile);
				}
			}
			
			dateiEinlesen.close();
		}
		 catch (Exception e) {
			System.out.println("Datei Gehaltstabelle konnte nicht gefunden werden!");
			e.printStackTrace();
		}
	}
	
	
	/** 
	 * Neues Objekt AngestellterModel
	 * 
	 * @param angestellter
	 * @param vorname
	 * @param nachname
	 * @param geburtsdatum
	 * @param geschlecht
	 * @param telefon
	 * @param angestelltenNr
	 * @param gehaltsgruppe
	 * @param erfahrungsstufe
	 */
	public void createNewAngestellten(String angestellter,String vorname, String nachname,String geburtsdatum, String geschlecht, String telefon, String angestelltenNr, String gehaltsgruppe, String erfahrungsstufe){


		AngestellterModel neuerAngestellter = new AngestellterModel();
		String[] infos = {angestellter, angestelltenNr, vorname, nachname, geburtsdatum, geschlecht, telefon, gehaltsgruppe, erfahrungsstufe};
		
		//Erster Eintrag muss immer "objekt" sein
		if (infos[0].equals("angestellter")) {

			//Eingelesene Informationen in das Objekt füllen.
				neuerAngestellter.setNr(Integer.valueOf(infos[1]).intValue());
				neuerAngestellter.setVorname(infos[2]);
				neuerAngestellter.setNachname(infos[3]);
				neuerAngestellter.setGeburtsdatum(infos[4]);
				neuerAngestellter.setGeschlecht(Integer.valueOf(infos[5]).intValue());
				neuerAngestellter.setTelefon(infos[6]);
				neuerAngestellter.setGehaltsgruppe(Integer.valueOf(infos[7]).intValue());
				neuerAngestellter.setErfahrungsstufe(Integer.valueOf(infos[8]).intValue());
		}
		
		this.firmaModel.addAngestellter(neuerAngestellter);
		
		if(AngestellterModel.isGeil == true) {
			firmaView.showIsGeil();
		}
		
		if(AngestellterModel.isGeil2 == true) {
			firmaView.showIsGeil2();
		}
	}

	
	/**
	 * Fügt der Anzeigeliste im View einen neuen Angestellten hinzu
	 * 
	 * @param model
	 */
	public void angestellterHinzufuegen(AngestellterModel model) {
		
		//Prüfen, ob ein evtl. vorhandenes Objekt aktualisiert wurde. Dann an dieser Stelle in der Liste
		//der Modelle das neue Model einfügen
		for (int i = 0; i < this.firmaModel.getAngestellteListe().size(); i++) {
			if (((AngestellterModel) this.firmaModel.getAngestellteListe().get(i)).getNr() == model.getNr()) {
				this.firmaModel.getAngestellteListe().set(i, model);
				this.firmaModel.setDirty(true);
				return;
			}
		}

		//Ansonsten neues Objekt der Liste hinzufügen
		this.firmaModel.setDirty(true);
		this.firmaModel.addAngestellter(model);
	}

	
	/**
	 * Dialog zum Öffnen/Speichern einer (kompatiblen) CSV-Datei darstellen
	 * 
	 * @param type	0 = "Datei Öffnen"-Dialog, 1 = "Datei Speicerhn"-Dialog
	 * @return	Pfad + Dateiname der selektierten Datei
	 */
	private File oeffneDateiDialog(int type) {
		File gewaehlteDatei = null;
		File pfad = new File("user.dir");
		JFileChooser chooser = new JFileChooser("Verzeichnis wählen");

		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV-Datei", new String[] { "csv", "CSV" });
		chooser.setFileFilter(filter);

		chooser.setDialogType(type);
		chooser.setFileSelectionMode(0);
		chooser.setCurrentDirectory(pfad);
		chooser.setVisible(true);

		int result = -1;

		if (type == 0) {
			result = chooser.showOpenDialog(null);
		} else {
			result = chooser.showSaveDialog(null);
		}

		if (result == 0) {
			gewaehlteDatei = chooser.getSelectedFile();
		}
		chooser.setVisible(false);

		return gewaehlteDatei;
	}

	
	/**
	 * Speichern der Programminhalte in eine Datei als kommaspearierte Informationen
	 * 
	 * @param datei				Dateiname, in der die Informationen gespeichert werden
	 * @throws IOException		Fehler bei Dateioperation
	 */
	private void speichereDaten(File datei) throws IOException {
		String inhalt = "";
		
		//Liste aller Objekte
		ArrayList<AngestellterModel> angestellte = this.firmaModel.getAngestellteListe();

		//Liste einzeln durchgehen und kommaspearierten String erstellen
		for (int i = 0; i < angestellte.size(); i++) {
			AngestellterModel angestellter = (AngestellterModel) angestellte.get(i);

			inhalt = String.valueOf(inhalt) + "angestellter,";
			inhalt = String.valueOf(inhalt) + angestellter.getNr() + ",";
			inhalt = String.valueOf(inhalt) + angestellter.getVorname() + ",";
			inhalt = String.valueOf(inhalt) + angestellter.getNachname() + ",";
			inhalt = String.valueOf(inhalt) + angestellter.getGeburtsdatum() + ",";
			inhalt = String.valueOf(inhalt) + angestellter.getGeschlecht() + ",";
			inhalt = String.valueOf(inhalt) + angestellter.getTelefon() + ",";
			inhalt = String.valueOf(inhalt) + angestellter.getGehaltsgruppe() + ",";
			inhalt = String.valueOf(inhalt) + angestellter.getErfahrungsstufe() + "\r\n";

		}

		//Speichern :-)
		BufferedWriter b = new BufferedWriter(new FileWriter(datei));
		b.write(inhalt);
		b.close();
	}
}