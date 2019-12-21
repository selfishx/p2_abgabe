//package views.impl;
//
//import java.awt.BorderLayout;
//import java.awt.EventQueue;
//import java.util.Observable;
//import java.util.Observer;
//
//import javax.swing.GroupLayout;
//import javax.swing.GroupLayout.Alignment;
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JSeparator;
//import javax.swing.JSplitPane;
//import javax.swing.JToolBar;
//import javax.swing.LayoutStyle.ComponentPlacement;
//import javax.swing.SwingConstants;
//import javax.swing.UIManager;
//import javax.swing.border.EmptyBorder;
//
//import models.AbstractModel;
//import models.impl.PatientModel;
//import models.impl.PraxisModel;
//import views.InterfaceView;
//import controller.AbstractController;
//import controller.impl.PraxisController;
//import events.AbstractEvent.EventType;
//import events.impl.PatientDataEvent;
//import events.impl.PraxisDataEvent;
//import events.impl.PraxisPatientEvent;
package views.impl;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import models.AbstractModel;
import models.impl.PatientModel;
import models.impl.PraxisModel;
import views.InterfaceView;
import controller.AbstractController;
import controller.impl.PraxisController;
import events.AbstractEvent.EventType;
import events.impl.PatientDataEvent;
import events.impl.PraxisDataEvent;
import events.impl.PraxisPatientEvent;

/**
 * Die PraxisView stellt alle Informationen der Software gebündelt dar. Sie ist die 
 * Hauptdarstellungsfläche der Anwendung, auf der alle Komponenten positioniert sind. Die 
 * PraxisView enthält eine ToolBar, eine SplitPane sowie innerhalb der SplitPane eine Tabelle 
 * (links) und eine JPane mit weiteren Elementen zur Darstellung von Inhalten (rechts). Die 
 * PraxisView meldet sich als Beobachter bei dem PraxisModel sowie beim aktuell angezeigten 
 * PatientModel an (bzw ab, sobald der Kunde gewechselt wird)
 * 
 * @author  Jens Sterk
 * @version 1.0
 *
 */
public class PraxisView extends JFrame implements Observer, InterfaceView {


	//Wert zur Serialisierung von SWING-Objketen. Hier nur, um Compiler-WARNING zu unterdrücken!
	private static final long serialVersionUID = 1L;


	//Verweis zum PraxisModel, um auf dessen Daten zuzugreifen
	private PraxisModel model;


	//Verweis zum PraxisController, um Aufgaben an ihn zu delegieren
	private PraxisController controller;


	//Verweis auf den derzeit ausgewählten und dargestellten Patienten
	private PatientModel gewaehlterPatient;


	private JLabel lblPatientenNrWert;	  //Feld zur Anzeige der Patienten-Nr
	private JLabel lblNameWert;			  //Feld zur Anzeige des Patienten-Namen
	private JLabel lblVornameWert;		  //Feld zur Anzeige des Patienten-Vornamen
	private JLabel lblGeschlechtWert;	  //Feld zur Anzeige des Patienten-Geschlechts
	private JLabel lblGeburtsdatumWert;	  //Feld zur Anzeige des Patienten-Geburtsdatum
	private JLabel lblStrasseWert;		  //Feld zur Anzeige der Patienten-Straße
	private JLabel lblPlzWert;			  //Feld zur Anzeige der Patienten-PLZ
	private JLabel lblOrtWert;			  //Feld zur Anzeige der Patienten-Stadt
	private JLabel lblTelefonWert;		  //Feld zur Anzeige der Patienten-Telefonnummer

	private JList<PatientModel> listPatientenListe;	  	//Liste aller Patienten der Praxis
	private JButton btnPatientBearbeiten; 				//Schaltfläche "Patient bearbeiten"
	private JButton btnDateiSpeichern;	  				//Schaltfläche "Datei speichern"
	private JButton btnDateiLaden;		  				//Schaltfläche "Datei laden"
	private JButton btnNeuerPatient;	  				//Schaltfläche "Neuer Patient"


	/**
	 * Constructor.
	 * Erzeugt die PraxisView und setzt das Model. Dem Model wird als Beobachter die View 
	 * hinzugefügt, d.h. bei Änderungen am Model wird die View unverzüglich informiert.
	 * 
	 * @param praxis PraxisModel
	 */
	public PraxisView(PraxisModel praxis) {
		setResizable(false);
		this.model = praxis;

		//Die View als Beobachter beim PraxisModel-Objekt anmelden.
		//Änderungen im Model werden direkt der View mitgeteilt.
		//Dafür steht die Methode update zur Verfügung.
		this.model.addObserver(this);
	}


	/**
	 * Komponenten des Hauptfensters erzeugen und hinzufügen
	 */
	private void createContent() {

		/*
		 * Setzen des Fenster-Titels, Aktivieren des roten X (oben rechts) zum Fenster schließen
		 * Größe des Fensters und Startpunkt (X,Y) auf dem Bildschirm definieren
		 */
		setTitle(this.model.getName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 913, 551);

		/*
		 * Haupt-Zeichenfläche (JPane-Komponente) des Fensters, auf dem alle anderen Komponenten 
		 * hinzugefügt werden.
		 * 
		 * Setzen eines leeren Rahmens (EmptyBorder) mit 5px Abstand zu allen Seiten, Hinzufügen 
		 * der JPane-Komponente zum Fenster, Setzen des BorderLayouts zur Ausrichtung der 
		 * Komponenten auf der JPane
		 */
		JPanel contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		/*
		 * Toolbar mit Icon-Buttons zur Interaktion mit dem Benutzer der Software
		 */
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		contentPane.add(toolBar, BorderLayout.NORTH);

		//"Datei laden"-Button
		btnDateiLaden = new JButton("Datei laden");
		btnDateiLaden.setIcon(new ImageIcon(PraxisView.class.getResource("/images/datei_laden.png")));
		toolBar.add(btnDateiLaden);
		btnDateiLaden.setEnabled(true);

		//Weiterleiten der Aktion beim Mausklick an den Controller
		btnDateiLaden.addActionListener(this.controller);

		//"Neuer Patient"-Button
		btnNeuerPatient = new JButton("Neuer Patient");
		btnNeuerPatient.setIcon(new ImageIcon(PraxisView.class.getResource("/images/neuer_patient.png")));
		toolBar.add(btnNeuerPatient);
		btnNeuerPatient.setEnabled(true);

		//Weiterleiten der Aktion beim Mausklick an den Controller
		btnNeuerPatient.addActionListener(this.controller);

		//"Speichern"-Button
		this.btnDateiSpeichern = new JButton("Speichern");
		this.btnDateiSpeichern.setIcon(new ImageIcon(PraxisView.class.getResource("/images/datei_speichern.png")));
		toolBar.add(this.btnDateiSpeichern);
		this.btnDateiSpeichern.setEnabled(false);

		//Schaltfläche "Patient bearbeiten"
		this.btnPatientBearbeiten = new JButton("Patient bearbeiten");
		btnPatientBearbeiten.setBounds(496, 38, 121, 23);
		this.btnPatientBearbeiten.setEnabled(false);
		this.btnPatientBearbeiten.addActionListener(this.controller);
		
		//Weiterleiten der Aktion beim Mausklick an den Controller
		this.btnDateiSpeichern.addActionListener(this.controller);

		/*
		 * Die JSplitPane teilt das Hauptfenster in 2 Bereiche (links und rechts) auf.
		 * Links befindet sich die tabellarische Darstellung aller Patienten, rechts Informationen 
		 * zu einem selektierten Patienten
		 */
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.CENTER);
		splitPane.setDividerLocation(250);

		//Liste "Patienten" erstellen
		this.listPatientenListe = new JList<PatientModel>();	
		this.listPatientenListe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		/*
		 * SelectionListener der Patienten-Tabelle. Dieser Listener reagiert auf Veränderungen der 
		 * selektierten Elemente innerhalb der Tabelle. Wird bspw. ein Eintrag der Tabelle 
		 * selektiert, wird dieser Listener informiert.
		 * 
		 * Der SelectionListener ist als anonyme innere Klasse umgesetzt.
		 */
		this.listPatientenListe.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {

				//Wenn ein Eintrag in der Termin-Tabelle selektiert wird und es sich dabei um das 
				//letzte Event der Eventkette von Swing (valueIsAdjusting) handelt, wird der 
				//"Termin Bearbeiten"-Button aktiviert und die Variable gewaehlterTermin mit dem 
				//selektierten Index belegt.
				if (PraxisView.this.listPatientenListe.getSelectedIndex() > -1 && 
						!event.getValueIsAdjusting()) {

					//Wurde bereits zuvor ein Patient ausgewählt, so muss der Beobachterstatus
					//der View von diesem Patienten entfernt werden.
					//Danach Reset der Variable gewaehlterPatient inkl. GUI-Bereinigung
					if (PraxisView.this.gewaehlterPatient != null) {
						PraxisView.this.gewaehlterPatient.deleteObserver(PraxisView.this);
						PraxisView.this.gewaehlterPatient = null;
					}
					PraxisView.this.resetPatient();

					//Anhand des selektierten Eintrages wird die erste (0-basiert!) Spalte 
					//(PatientenNr) ausgelesen
					int patientenNr = PraxisView.this.listPatientenListe.getSelectedIndex() + 1;

					//Mittels des PraxisModel wird das passende PatientModel-Objekt
					//anhand der PatientenNr geliefert.
					PatientModel patient = PraxisView.this.model.getPatient(patientenNr);

					//Befüllung der Felder mit den Werten des PatientModel-Objektes
					PraxisView.this.lblPatientenNrWert.setText(""+patient.getNr());
					PraxisView.this.lblNameWert.setText(patient.getNachname());
					PraxisView.this.lblVornameWert.setText(patient.getVorname());
					PraxisView.this.lblGeschlechtWert.setText((patient.getGeschlecht() == 0) ? "Männlich" : "Weiblich");
					PraxisView.this.lblGeburtsdatumWert.setText(patient.getGeburtsdatum());
					PraxisView.this.lblTelefonWert.setText(patient.getTelefon());

					//Buttons aktivieren
					PraxisView.this.btnPatientBearbeiten.setEnabled(true);

					//Variable gewaehlterPatient belegen
					PraxisView.this.gewaehlterPatient = patient;

					//Die View als Beobachter beim PatientModel-Objekt anmelden.
					//Änderungen im Model werden direkt der View mitgeteilt.
					//Dafür steht die Methode update zur Verfügung.
					PraxisView.this.gewaehlterPatient.addObserver(PraxisView.this);
				}
			}
		});
		

		//Die Informationen liegwn innerhalb einer JScrollPane-Komponente, damit, wenn mehr 
		//Einträge als darstellbare Fläche vorhanden ist, Bildlaufleisten angezeigt werden.
		JScrollPane scrpaPatientenListe = new JScrollPane();
		splitPane.setLeftComponent(scrpaPatientenListe);
		scrpaPatientenListe.setViewportView(this.listPatientenListe);

		//Rechte Seite der JSplitPane
		JScrollPane scrpaPatient = new JScrollPane();
		splitPane.setRightComponent(scrpaPatient);

		//Patientendaten anzeigen.
		JPanel paPatient = new JPanel();
		scrpaPatient.setViewportView(paPatient);

		//Patienten-Nr
		JLabel lblPatientenNr = new JLabel("PatientenNr.:");
		lblPatientenNr.setBounds(20, 40, 115, 25);

		this.lblPatientenNrWert = new JLabel("");
		lblPatientenNrWert.setBounds(145, 40, 264, 25);

		//Trennlinie
		JSeparator sepTrenner1 = new JSeparator();
		sepTrenner1.setBounds(0, 66, 637, 2);

		//Patientenname
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(20, 74, 115, 25);

		this.lblNameWert = new JLabel("");
		lblNameWert.setBounds(145, 74, 170, 25);
		this.lblNameWert.setHorizontalAlignment(SwingConstants.RIGHT);

		//Patienten-Vorname
		JLabel lblVorname = new JLabel("Vorname:");
		lblVorname.setBounds(20, 105, 115, 25);

		this.lblVornameWert = new JLabel("");
		lblVornameWert.setBounds(145, 105, 168, 25);
		this.lblVornameWert.setHorizontalAlignment(SwingConstants.RIGHT);

		//Patienten-Geschlecht
		JLabel lblGeschlecht = new JLabel("Geschlecht:");
		lblGeschlecht.setBounds(20, 136, 115, 25);

		this.lblGeschlechtWert = new JLabel("");
		lblGeschlechtWert.setBounds(145, 136, 167, 25);
		this.lblGeschlechtWert.setHorizontalAlignment(SwingConstants.RIGHT);

		//Geburtsdatum
		JLabel lblGeburtsdatum = new JLabel("Geburtsdatum:");
		lblGeburtsdatum.setBounds(20, 167, 115, 25);

		this.lblGeburtsdatumWert = new JLabel("");
		lblGeburtsdatumWert.setBounds(145, 167, 166, 25);
		this.lblGeburtsdatumWert.setHorizontalAlignment(SwingConstants.RIGHT);

		this.lblStrasseWert = new JLabel("");
		lblStrasseWert.setBounds(452, 74, 165, 25);
		this.lblStrasseWert.setHorizontalAlignment(SwingConstants.RIGHT);

		this.lblPlzWert = new JLabel("");
		lblPlzWert.setBounds(452, 105, 165, 25);
		this.lblPlzWert.setHorizontalAlignment(SwingConstants.RIGHT);

		this.lblOrtWert = new JLabel("");
		lblOrtWert.setBounds(452, 136, 165, 25);
		this.lblOrtWert.setHorizontalAlignment(SwingConstants.RIGHT);

		//Telefon
		JLabel lblTelefon = new JLabel("Telefon:");
		lblTelefon.setBounds(20, 198, 115, 25);

		this.lblTelefonWert = new JLabel("");
		lblTelefonWert.setBounds(145, 198, 166, 25);
		this.lblTelefonWert.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//Es wird ein Absolute Layout verwendet. Deswegen muss kein explizites Layout gesetzt werden!
		paPatient.setLayout(null);
		paPatient.add(lblName);
		paPatient.add(lblPatientenNr);
		paPatient.add(lblVorname);
		paPatient.add(lblGeschlecht);
		paPatient.add(lblGeburtsdatum);
		paPatient.add(lblPatientenNrWert);
		paPatient.add(btnPatientBearbeiten);
		paPatient.add(lblGeburtsdatumWert);
		paPatient.add(lblGeschlechtWert);
		paPatient.add(lblVornameWert);
		paPatient.add(lblNameWert);
		paPatient.add(lblTelefon);
		paPatient.add(lblTelefonWert);
		paPatient.add(lblOrtWert);
		paPatient.add(lblPlzWert);
		paPatient.add(lblStrasseWert);
		paPatient.add(sepTrenner1);
	}


	/**
	 * Liefert den derzeit in der Views selektierten und dargestellten Patienten respektive sein
	 * Datenobjekt
	 * 
	 * @return Patientendaten des selektierten Patienten
	 */
	public PatientModel getGewaehlterPatient() {
		return this.gewaehlterPatient;
	}


	/**
	 * Liefert die Patienten-Tabelle der View
	 * 
	 * @return Patienten-Tabelle
	 */
	public JList<PatientModel> getPatientenTabelle() {
		return this.listPatientenListe;

	}


	/**
	 * Diese Methode setzt alle Anzeigefelder der Patientendaten auf "leer" zurück.
	 * 
	 * Sie setzt sozusagen die View in den Ursprungszustand zurück
	 */
	private void resetPatient() {
		this.lblPatientenNrWert.setText("");
		this.lblNameWert.setText("");
		this.lblVornameWert.setText("");
		this.lblGeschlechtWert.setText("");
		this.lblGeburtsdatumWert.setText("");
		this.lblStrasseWert.setText("");
		this.lblPlzWert.setText("");
		this.lblOrtWert.setText("");
		this.lblTelefonWert.setText("");

		//Schaltflächen zurücksetzen
		this.btnPatientBearbeiten.setEnabled(false);
	}


	/**
	 * Diese Methode entfernt alle Patienten aus der Patiententabelle. Dies ist z. B.
	 * notwendig, wenn eine neue Datei geöffnet wird, um alle zuvor dargestellten Daten
	 * zu entfernen.
	 */
	public void resetPatienten() {
		this.listPatientenListe.removeAll();
		resetPatient();
	}


	/**
	 * Setzt den Controller zur Delegation der Aufgaben. Er muss vom Typ PraxisController sein
	 * 
	 * @param controller Controller
	 */
	@Override
	public void setController(AbstractController controller) {
		this.controller = (PraxisController) controller;
	}


	/**
	 * Setzt das Model als Datenbasis. Es muss vom Typ PraxisModel sein
	 * 
	 * @param model PraxisModel
	 */
	@Override
	public void setModel(AbstractModel model) {
		this.model = (PraxisModel) model;
	}


	/**
	 * Diese Methode wird durch das Implementieren des Observer-Interfaces bereitgestellt.
	 * Meldet sich die View bei anderen, observierbaren Objekten als Beobachter an, so rufen diese 
	 * - beim Eintreten eines Ereignisses - diese Methode auf, um die View über Änderungen zu 
	 * informieren.
	 * 
	 * @param quelle Objekt, das beobachtet wird und das Ereignis meldet
	 * @param daten  Event-Informationen
	 */
	@Override
	public void update(Observable quelle, Object daten) {

		/*
		 * Prüfung, ob die Variable daten eine bestimmte Objektstruktur aufweist. Es wird davon 
		 * ausgegangen, dass daten Event-Informationen beinhaltet und daher vom Typ eine konkrete 
		 * Implementierung von AbstractEvent ist.
		 */
		if (daten instanceof PraxisDataEvent) {

			/*
			 * PraxisDataEvent. Es liegen veränderte PraxisModel-Informationen vor.
			 */
			PraxisDataEvent pde = (PraxisDataEvent) daten;
			setTitle(pde.getData().getName());

			if (pde.getData().isDirty()) {
				this.btnDateiSpeichern.setEnabled(true);
			} else {
				this.btnDateiSpeichern.setEnabled(false);
			}

		}

		else if (daten instanceof PraxisPatientEvent) {

			/*
			 * PraxisPatientEvent. Es liegen veränderte PatientModel-Informationen vor.
			 * Das Event kann unterschiedliche Formen des EventType annehmen. Im folgenden
			 * Fall werden ADD (Hinzufügen) und REMOVE (Entfernen) unterstützt
			 */
			PraxisPatientEvent ppe = (PraxisPatientEvent) daten;
			if (ppe.getType() == EventType.ADD) {
				//Patient in die Patienten-Liste aufnehmen
								
				//TODO: Hier müssen Sie einen Teil Ihrer Programmierung einfügen
				//TODO: die folgenden Zeilen dienen nur als Debug-Ausgabe und sollen
				//TODO: Ihnen als Anhaltspunkt dienen!
				System.out.println("PRAXISVIEW ADD = " + ppe.getData().getNr());
				System.out.println("PRAXISVIEW ADD = " +ppe.getData().getVorname());
				System.out.println("PRAXISVIEW ADD = " +ppe.getData().getNachname());
				
			}
			else if (ppe.getType() == EventType.REMOVE) {
				//Patient aus der Patienten-Liste entfernen
				
				//TODO: Hier müssen Sie einen Teil Ihrer Programmierung einfügen
				//TODO: die folgenden Zeilen dienen nur als Debug-Ausgabe und sollen
				//TODO: Ihnen als Anhaltspunkt dienen!
				System.out.println("PRAXISVIEW REMOVE = " + ppe.getData().getNr());
				System.out.println("PRAXISVIEW REMOVE = " +ppe.getData().getVorname());
				System.out.println("PRAXISVIEW REMOVE = " +ppe.getData().getNachname());

			}
		}

		else if (daten instanceof PatientDataEvent) {

			/*
			 * PatientDataEvent. Es liegen veränderte PatientModel-Informationen für den derzeit 
			 * dargestellten Patienten vor. Es werden alle Felder der View mit den PatientModel-
			 * Daten neu befüllt
			 */
			PatientDataEvent pde = (PatientDataEvent) daten;
			if (this.gewaehlterPatient.getNr() == pde.getData().getNr()) {
				this.lblPatientenNrWert.setText(""+pde.getData().getNr());
				this.lblNameWert.setText(pde.getData().getNachname());
				this.lblVornameWert.setText(pde.getData().getVorname());
				this.lblGeschlechtWert.setText((pde.getData().getGeschlecht() == 0) ? "M" : "W");
				this.lblGeburtsdatumWert.setText(pde.getData().getGeburtsdatum());
				this.lblTelefonWert.setText(pde.getData().getTelefon());
			}
		}

	}


	/**
	 * Setzt das Aussehen der Swing-Komponenten auf das System-Layout (z.b. Windows)
	 * und öffnet dann das Programmfenster (JFrame) mittels der Swing EventQueue (Threading).
	 * 
	 * Somit wird die GUI im EDT (Event Dispatch Thread) ausgeführt.
	 */
	public void zeigeFenster() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			System.out.println("Fehler beim Setzen des Look And Feel für die Software. Abbruch");
			System.exit(1);
		}

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					PraxisView.this.createContent();
					PraxisView.this.setVisible(true);
				} catch (Exception e) {
					return; //Keine EDT-Fehler tracken
				}
			}
		});
	}


	public JButton  getBtnDateiLaden() {
		return btnDateiLaden;
	}


	public JButton getBtnNeuerPatient() {
		return btnNeuerPatient;
	}


	public JButton getBtnSpeichern() {
		return btnDateiSpeichern;
	}


	public JButton getBtnPatientBearbeiten() {
		return btnPatientBearbeiten;
	}

}