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

import models.impl.PatientModel;
import models.impl.PraxisModel;
import views.impl.PraxisView;
import controller.AbstractController;

/**
 * Diese Klasse repräsentiert die Logik der Praxis. Alle Aktivitäten innerhalb
 * der Software werden durch diese Klasse delegiert. Interaktionen des Benutzers
 * mit der grafischen Benutzeroberfläche (View) führen zu Methodenaufrufen in
 * dieser Klasse.
 * 
 * @author  Jens Sterk
 * @version 1.0
 *
 */
public class PraxisController extends AbstractController implements ActionListener {

	// Verweis auf das Hauptfenster, die PraxisView
	private final PraxisView praxisView;
	

	// Verweis auf das zugrundeliegende Model
	private PraxisModel praxisModel;

	
	/**
	 * Konstruktor des Controller. Es werden das Model und die View dem
	 * Controller als Parameter übergeben. Der Controller setzt sich innerhalb
	 * der View mittels der Funktion PraxisView.setController als
	 * Verantwortlicher für die auszuführende Logik.
	 * 
	 * @param praxis		Model
	 * @param praxisView	View
	 */
	public PraxisController(PraxisModel praxis, PraxisView praxisView) {
		this.praxisModel = praxis; 
		this.praxisView = praxisView;
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

		if (this.praxisView != null && actionCommand == this.praxisView.getBtnDateiLaden().getText()) {
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

		if (this.praxisView != null && actionCommand == this.praxisView.getBtnSpeichern().getText() && this.praxisModel.isDirty()) {
			File datei = oeffneDateiDialog(1);
			if (datei != null) {
				try {
					speichereDaten(datei);
					this.praxisModel.setDirty(false);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(new JFrame(), "Es trat ein Fehler beim Speichern der Datei auf. Abbruch!", "Fehler", 0);
					return;
				}
			}
		}
	}

	/**
	 * Setzt alle dargestellten Informationen zurück
	 */
	private void resetFenster() {
		this.praxisModel.removePatienten();
		this.praxisView.resetPatienten();
	}

	
	/**
	 * Liefert das Objekt des aktuell in der GUI selektierten Patienten zurück
	 * 
	 * @return PatientModel
	 */
	public PatientModel getAktuellerPatient() {
		return this.praxisView.getGewaehlterPatient();
	}
	

	/**
	 * Ruft die grafische Oberfläche (GUI) der PraxisView auf.
	 */
	public void zeigeFenster() {
		this.praxisView.zeigeFenster();
	}
	

	/**
	 * Liefert das zugrundeliegende Model zurück
	 */
	public PraxisModel getModel() {
		return this.praxisModel;
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
		PatientModel letzterPatient = null;
		
		this.praxisModel.removePatienten();

		//Datei einlesen
		BufferedReader dateiEinlesen = new BufferedReader(new FileReader(datei));
		
		//zeilenweises Abarbeiten
		while ((zeile = dateiEinlesen.readLine()) != null) {
			//Auftrennen der Informationen der jeweiligen Zeile. Komma dient als Separator
			String[] infos = zeile.split(",");

			//Debug-Ausgabe
			System.out.println(infos[0] + ", " + infos[1] + ", " + infos[2] + ", " + infos[3] + ", " + infos[4] + ", " + infos[5] + ", " + infos[6]);
			
			//Erster Eintrag muss immer "objekt" sein
			if (infos[0].equals("patient")) {

				//Jede Zeile enthält 7 Informationen
				if (infos.length != 7) {
					dateiEinlesen.close();
					throw new IOException();
				}

				//Eingelesene Informationen in das Objekt füllen.
				letzterPatient = new PatientModel();
				try {
					letzterPatient.setNr(Integer.valueOf(infos[1]).intValue());
					letzterPatient.setVorname(infos[2]);
					letzterPatient.setNachname(infos[3]);
					letzterPatient.setGeburtsdatum(infos[4]);
					letzterPatient.setGeschlecht(Integer.valueOf(infos[5]).intValue());
					letzterPatient.setTelefon(infos[6]);
				} catch (Exception e) {
					dateiEinlesen.close();
					throw new IOException();
				}
				
				//Hinzufügen des neuen Objektes zur Liste aller Objekte
				this.praxisModel.addPatient(letzterPatient);
				letzterPatient = null;
			}
		}
		
		//Schließen des Streams.
		dateiEinlesen.close();
	}

	
	/**
	 * Neues Objekt manuell hinzufügen
	 * 
	 * @param model Neues Objektmodell mit notwendigen Informationen
	 */
	public void patientHinzufuegen(PatientModel model) {
		
		//Prüfen, ob ein evtl. vorhandenes Objekt aktualisiert wurde. Dann an dieser Stelle in der Liste
		//der Modelle das neue Model einfügen
		for (int i = 0; i < this.praxisModel.getPatientenListe().size(); i++) {
			if (((PatientModel) this.praxisModel.getPatientenListe().get(i)).getNr() == model.getNr()) {
				this.praxisModel.getPatientenListe().set(i, model);
				this.praxisModel.setDirty(true);
				return;
			}
		}

		//Ansonsten neues Objekt der Liste hinzufügen
		this.praxisModel.setDirty(true);
		this.praxisModel.addPatient(model);
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
		ArrayList<PatientModel> patienten = this.praxisModel.getPatientenListe();

		//Liste einzeln durchgehen und kommaspearierten String erstellen
		for (int i = 0; i < patienten.size(); i++) {
			PatientModel patient = (PatientModel) patienten.get(i);

			inhalt = String.valueOf(inhalt) + "patient,";
			inhalt = String.valueOf(inhalt) + patient.getNr() + ",";
			inhalt = String.valueOf(inhalt) + patient.getVorname() + ",";
			inhalt = String.valueOf(inhalt) + patient.getNachname() + ",";
			inhalt = String.valueOf(inhalt) + patient.getGeburtsdatum() + ",";
			inhalt = String.valueOf(inhalt) + patient.getGeschlecht() + ",";
			inhalt = String.valueOf(inhalt) + patient.getTelefon() + "\r\n";

		}

		//Speichern :-)
		BufferedWriter b = new BufferedWriter(new FileWriter(datei));
		b.write(inhalt);
		b.close();
	}
}