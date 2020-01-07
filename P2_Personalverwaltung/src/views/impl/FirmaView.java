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
//import models.impl.AngestellterModel;
//import models.impl.FirmaModel;
//import views.InterfaceView;
//import controller.AbstractController;
//import controller.impl.FirmaController;
//import events.AbstractEvent.EventType;
//import events.impl.AngestellterDataEvent;
//import events.impl.FirmaDataEvent;
//import events.impl.FirmaAngestellterEvent;
package views.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import models.AbstractModel;
import models.impl.AngestellterModel;
import models.impl.FirmaModel;
import views.InterfaceView;
import controller.AbstractController;
import controller.impl.FirmaController;
import events.AbstractEvent.EventType;
import events.impl.AngestellterDataEvent;
import events.impl.FirmaDataEvent;
import events.impl.FirmaAngestellterEvent;

/**
 * Die FirmaView stellt alle Informationen der Software gebündelt dar. Sie ist die 
 * Hauptdarstellungsfläche der Anwendung, auf der alle Komponenten positioniert sind. Die 
 * FirmaView enthält eine ToolBar, eine SplitPane sowie innerhalb der SplitPane eine Tabelle 
 * (links) und eine JPane mit weiteren Elementen zur Darstellung von Inhalten (rechts). Die 
 * FirmaView meldet sich als Beobachter bei dem FirmaModel sowie beim aktuell angezeigten 
 * AngestellterModel an (bzw ab, sobald der Kunde gewechselt wird)
 * 
 * @author  Jens Sterk
 * @version 1.0
 *
 */
public class FirmaView extends JFrame implements Observer, InterfaceView {


	//Wert zur Serialisierung von SWING-Objketen. Hier nur, um Compiler-WARNING zu unterdrücken!
	private static final long serialVersionUID = 1L;


	//Verweis zum FirmaModel, um auf dessen Daten zuzugreifen
	private FirmaModel model;


	//Verweis zum FirmaController, um Aufgaben an ihn zu delegieren
	private FirmaController controller;


	//Verweis auf den derzeit ausgewählten und dargestellten Angestellten
	private AngestellterModel gewaehlterAngestellter;

	public JDialog contentAddAngestellter = new JDialog(this,true);//Dialog für Angestellter hinzufügen
	public JDialog contentEntferneAngestellten = new JDialog(this,true);//Dialog für Angestellten entfernen
	public JDialog contentBearbeiteAngestellten = new JDialog(this,true);//Dialog für Angestellten bearbeiten	
	
	private JLabel lblAngestellteNrWert;	  //Feld zur Anzeige der Angestellten-Nr
	private JLabel lblNameWert;			  //Feld zur Anzeige des Angestellten-Namen
	private JLabel lblVornameWert;		  //Feld zur Anzeige des Angestellten-Vornamen
	private JLabel lblGeschlechtWert;	  //Feld zur Anzeige des Angestellten-Geschlechts
	private JLabel lblGeburtsdatumWert;	  //Feld zur Anzeige des Angestellten-Geburtsdatum
	private JLabel lblTelefonWert;		  //Feld zur Anzeige der Angestellten-Telefonnummer
	private JLabel lblGehaltsgruppeWert;  //Feld zur Anzeige der Gehaltsstufe
	private JLabel lblErfahrungsstufeWert;//Feld zur Anzeige der Erfahrungsstufe
	private JLabel lblGehaltWert;		  //Feld zur Anzeige des Gehalts
	
	//AnzeigeLabels für Mitarbeiter hinzufügen deklarieren und initialisieren
	private JLabel lblName = new JLabel("Name: ");
	private JLabel lblVorname = new JLabel("Vorname: ");
	private JLabel lblGeburtstag = new JLabel("Geburtstag: ");
	private JLabel lblGeschlecht = new JLabel("Geschlecht: ");
	private JLabel lblAngestelltenNr = new JLabel("AngestelltenNr.: "); //Wird automatisch ermittelt
	private JLabel lblTelefon = new JLabel("Telefon: ");
	private JLabel lblGehaltsgruppe = new JLabel("Gehaltsgrp.: ");
	private JLabel lblErfahrungsstufe = new JLabel("Erfahrungsst.: ");
	private JLabel lblAngestellter = new JLabel("angestellter");//Gibt den Wert "angestellter" zur Überprüfung weiter
	
	//TextFelder für "Mitarbeiter hinzufügen" deklarieren
	public JTextField txtName = new JTextField(50);
	public JTextField txtVorname = new JTextField(50);
	public  JTextField txtTelefon = new JTextField(50);
	private JTextField txtAngestelltenNummer = new JTextField(50);
	private JTextField txtGeburtsdatum = new JTextField(50);
	private JTextField txtGeschlecht = new JTextField(50);
	
	private String geburtsdatumNewAngestellter; //String Geburtsdatum zur Umwandlung aus der ComboBox
	
	//ComboBoxesfür Mitarbeiter hinzufügen deklarieren
	private JComboBox<String> boxTag = new JComboBox<>();
	private JComboBox<String> boxMonat = new JComboBox<>();
	private JComboBox<String> boxJahr = new JComboBox<>();
	private JComboBox<String> boxGeschlecht = new JComboBox<>();
	public JComboBox<String> boxGehaltsgruppe = new JComboBox<>();
	public JComboBox<String> boxErfahrungsstufe = new JComboBox<>();

	private JList<AngestellterModel> listAngestellteListe;	  	//Liste aller Angestellten der Firma
	
	private JButton btnAngestellterBearbeiten; 				//Schaltfläche "Angestellter bearbeiten"
	private JButton btnDateiSpeichern;	  				//Schaltfläche "Datei speichern"
	private JButton btnDateiLaden;		  				//Schaltfläche "Datei laden"
	private JButton btnNeuerAngestellter;	  				//Schaltfläche "Neuer Angestellter"
	private JButton btnEntferneAngestellten;			//Schaltfläche "Angestellten entfernen"
	private JButton btnSpeicherNeuenAngestellten = new JButton("Hinzufügen");//Neuen mitarbeiter hinzufügen im Dialog neuer Angestellter
	private JButton btnAbbrechen = new JButton("Abbrechen");//Aktion Mitarbeiter hinzufügen abbrechen im Dialog neuer Angestellter
	private JButton btnEntfernen = new JButton("Entfernen");//Aktion Mitarbeiter entfernen bestätigen im Dialog Angestellten entfernen
	private JButton btnBestätigen = new JButton("Bestätigen");//Aktion Mitarbeiter bearbeiten bestätigen im Dialog Angestellten bearbeiten
	private JButton btnGehalt;   //Schaltfläche Gehalt einesehen
	
	/*
	 * Liste aller Angestellten, die bei einem EVENT update befüllt wird
	 * Die Liste wird der JList listAngestellteListe übergeben
	 */
	private DefaultListModel<AngestellterModel> mitarbeiter = new DefaultListModel<>(); 
	
	//Textfelder / Comboboxes für Mitarbeiter bearbeiten deklarieren
	public  JTextField txtTelefonBearbeiten = new JTextField(50);

		
		
	
	
	

	/**
	 * Constructor.
	 * Erzeugt die FirmaView und setzt das Model. Dem Model wird als Beobachter die View 
	 * hinzugefügt, d.h. bei Änderungen am Model wird die View unverzüglich informiert.
	 * 
	 * @param firma FirmaModel
	 */
	public FirmaView(FirmaModel firma) {
		setResizable(false);
		this.model = firma;

		//Die View als Beobachter beim FirmaModel-Objekt anmelden.
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
		btnDateiLaden.setIcon(new ImageIcon(FirmaView.class.getResource("/images/datei_laden.png")));
		toolBar.add(btnDateiLaden);
		btnDateiLaden.setEnabled(true);

		//Weiterleiten der Aktion beim Mausklick an den Controller
		btnDateiLaden.addActionListener(this.controller);

		//"Neuer Angestellter"-Button
		btnNeuerAngestellter = new JButton("Neuer Angestellter");
		btnNeuerAngestellter.setIcon(new ImageIcon(FirmaView.class.getResource("/images/neuer_patient.png")));
		toolBar.add(btnNeuerAngestellter);
		btnNeuerAngestellter.setEnabled(true);
		
		//Weiterleiten der Aktion beim Mausklick an den Controller
		btnNeuerAngestellter.addActionListener(this.controller);

		//"Angestellten entfernen"-Button
		btnEntferneAngestellten = new JButton("Angestellten entfernen");
		btnEntferneAngestellten.setIcon(new ImageIcon(FirmaView.class.getResource("/images/remove_angestellter.png")));
		toolBar.add(btnEntferneAngestellten);
		btnEntferneAngestellten.setEnabled(false);
		//Weiterleiten der Aktion beim Mausklick an den Controller
		btnEntferneAngestellten.addActionListener(this.controller);
		
		//"Speichern"-Button
		this.btnDateiSpeichern = new JButton("Speichern");
		this.btnDateiSpeichern.setIcon(new ImageIcon(FirmaView.class.getResource("/images/datei_speichern.png")));
		toolBar.add(this.btnDateiSpeichern);
		this.btnDateiSpeichern.setEnabled(false);

		//Schaltfläche "Angestellter bearbeiten"
		this.btnAngestellterBearbeiten = new JButton("Angestellten bearbeiten");
		btnAngestellterBearbeiten.setBounds(440, 38, 190, 23);
		this.btnAngestellterBearbeiten.setEnabled(false);
		this.btnAngestellterBearbeiten.addActionListener(this.controller);
		
		//Weiterleiten der Aktion beim Mausklick an den Controller
		this.btnDateiSpeichern.addActionListener(this.controller);

		/*
		 * Die JSplitPane teilt das Hauptfenster in 2 Bereiche (links und rechts) auf.
		 * Links befindet sich die tabellarische Darstellung aller Angestellten, rechts Informationen 
		 * zu einem selektierten Angestellten
		 */
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.CENTER);
		splitPane.setDividerLocation(250);

		//Liste "Angestellte" erstellen. Sie dient zur Anzeige im Auswahlfenster der Mitarbeiter (das Fenster auf der linken Seite)
		this.listAngestellteListe = new JList<AngestellterModel>(mitarbeiter);	
		this.listAngestellteListe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		/*
		 * SelectionListener der Angestellte-Tabelle. Dieser Listener reagiert auf Veränderungen der 
		 * selektierten Elemente innerhalb der Tabelle. Wird bspw. ein Eintrag der Tabelle 
		 * selektiert, wird dieser Listener informiert.
		 * 
		 * Der SelectionListener ist als anonyme innere Klasse umgesetzt.
		 */
		this.listAngestellteListe.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				
				//Wenn ein Eintrag in der Termin-Tabelle selektiert wird und es sich dabei um das 
				//letzte Event der Eventkette von Swing (valueIsAdjusting) handelt, wird der 
				//"Termin Bearbeiten"-Button und der "Angestellten entfernen"-Button aktiviert und die Variable gewaehlterTermin mit dem 
				//selektierten Index belegt.
				
				if(listAngestellteListe.getSelectedIndex() > -1) {
					btnEntferneAngestellten.setEnabled(true);
				}
				else {
					btnEntferneAngestellten.setEnabled(false);
				}
				
				if (FirmaView.this.listAngestellteListe.getSelectedIndex() > -1 && 
						!event.getValueIsAdjusting()) {

					//Wurde bereits zuvor ein Angestellter ausgewählt, so muss der Beobachterstatus
					//der View von diesem Angestellten entfernt werden.
					//Danach Reset der Variable gewaehlterAngestellter inkl. GUI-Bereinigung
					if (FirmaView.this.gewaehlterAngestellter != null) {
						FirmaView.this.gewaehlterAngestellter.deleteObserver(FirmaView.this);
						FirmaView.this.gewaehlterAngestellter = null;
					}
					FirmaView.this.resetAngestellter();

					/*
					 * Anhand des selektierten Eintrages wird die erste (0-basiert!) Spalte 
					 * (AngestellteNr) ausgelesen
					 * Eine Schleife zählt die nicht gelöschten Mitarbeiter in der ArrayList angestellte (FirmaModel).
					 * Für jeden gelöschten Mitarbeiter (Telefonnummer wird überprüft) wird der Wert "gelöschte" um eins erhöht.
					 * Die Schleife läuft solange, bis die Anzahl der nicht gelöschten Mitarbeiter = des Wertes  getSelctedIndex() ist.
					 * Der Wert gelöschte + getSelectedIndex() wird dannn im Wert angestellter gespeichert.
					*/
					
					int gelöschte = 1;
					int i = 0;
					int k = 0;
					int j = 0;
											
						while(i <= FirmaView.this.listAngestellteListe.getSelectedIndex()) {
							
							if(model.getAngestellteListe().get(j).getTelefon().length()<=1) {
								int t = Integer.parseInt(model.getAngestellteListe().get(j).getTelefon());								
								if(t == 0) {
									k++;
									while(k > j) {
										
										if(model.getAngestellteListe().get(j).getTelefon().length()<=1) {
										t = Integer.parseInt(model.getAngestellteListe().get(j).getTelefon());
										
										if(t == 0) {
											gelöschte++;
											k++;
											j++;
										}
										}
										else {
											k=j;
										}	
								}
							}
							}
							i++;
							k++;
							j++;
						}

						int angestellteNr = FirmaView.this.listAngestellteListe.getSelectedIndex()+gelöschte;
					

					//Mittels des FirmaModel wird das passende AngestellterModel-Objekt
					//anhand der AngestellteNr geliefert.
					AngestellterModel angestellter = FirmaView.this.model.getAngestellter(angestellteNr);
					
					//Sobald ein Eintrag ausgewählt wird, wird das Gehalt des Angestellten berechnet und geladen
					lblGehaltWert.setText(""+controller.gehaltLaden(angestellter));
					
					//Befüllung der Felder mit den Werten des AngestellterModel-Objektes
					FirmaView.this.lblAngestellteNrWert.setText(""+angestellter.getNr());
					FirmaView.this.lblNameWert.setText(angestellter.getNachname());
					FirmaView.this.lblVornameWert.setText(angestellter.getVorname());
					FirmaView.this.lblGeschlechtWert.setText((angestellter.getGeschlecht() == 0) ? "Männlich" : "Weiblich");
					FirmaView.this.lblGeburtsdatumWert.setText(angestellter.getGeburtsdatum());
					FirmaView.this.lblTelefonWert.setText(angestellter.getTelefon());
					FirmaView.this.lblGehaltsgruppeWert.setText(""+angestellter.getGehaltsgruppe());
					FirmaView.this.lblErfahrungsstufeWert.setText(""+angestellter.getErfahrungsstufe());
					FirmaView.this.lblGehaltWert.setText(""+angestellter.getGehalt());

					//Buttons aktivieren
					FirmaView.this.btnAngestellterBearbeiten.setEnabled(true);

					//Variable gewaehlterAngestellter belegen
					FirmaView.this.gewaehlterAngestellter = angestellter;

					//Die View als Beobachter beim AngestellterModel-Objekt anmelden.
					//Änderungen im Model werden direkt der View mitgeteilt.
					//Dafür steht die Methode update zur Verfügung.
					FirmaView.this.gewaehlterAngestellter.addObserver(FirmaView.this);
				}
			}
		});
		

		//Die Informationen liegwn innerhalb einer JScrollPane-Komponente, damit, wenn mehr 
		//Einträge als darstellbare Fläche vorhanden ist, Bildlaufleisten angezeigt werden.
		JScrollPane scrpaAngestellteListe = new JScrollPane();
		splitPane.setLeftComponent(scrpaAngestellteListe);
		scrpaAngestellteListe.setViewportView(this.listAngestellteListe);

		//Rechte Seite der JSplitPane
		JScrollPane scrpaAngestellter = new JScrollPane();
		splitPane.setRightComponent(scrpaAngestellter);

		//Angestelltendaten anzeigen.
		JPanel paAngestellter = new JPanel();
		scrpaAngestellter.setViewportView(paAngestellter);

		//Angestellten-Nr
		JLabel lblAngestellteNr = new JLabel("AngestellterNr.:");
		lblAngestellteNr.setBounds(20, 40, 115, 25);

		this.lblAngestellteNrWert = new JLabel("");
		lblAngestellteNrWert.setBounds(145, 40, 264, 25);

		//Trennlinie
		JSeparator sepTrenner1 = new JSeparator();
		sepTrenner1.setBounds(0, 66, 637, 2);

		//Angestelltenname
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(20, 74, 115, 25);

		this.lblNameWert = new JLabel("");
		lblNameWert.setBounds(145, 74, 170, 25);
		this.lblNameWert.setHorizontalAlignment(SwingConstants.RIGHT);

		//Angestellten-Vorname
		JLabel lblVorname = new JLabel("Vorname:");
		lblVorname.setBounds(20, 105, 115, 25);

		this.lblVornameWert = new JLabel("");
		lblVornameWert.setBounds(145, 105, 168, 25);
		this.lblVornameWert.setHorizontalAlignment(SwingConstants.RIGHT);

		//Angestellten-Geschlecht
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

		//Telefon
		JLabel lblTelefon = new JLabel("Telefon:");
		lblTelefon.setBounds(20, 198, 115, 25);

		this.lblTelefonWert = new JLabel("");
		lblTelefonWert.setBounds(145, 198, 166, 25);
		this.lblTelefonWert.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//Gehaltsgruppe
		JLabel lblGehaltsgruppe = new JLabel("Gehaltsgruppe:");
		lblGehaltsgruppe.setBounds(20, 229, 115, 25);

		this.lblGehaltsgruppeWert = new JLabel("");
		lblGehaltsgruppeWert.setBounds(145, 229, 166, 25);
		this.lblGehaltsgruppeWert.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//Erfahrungsstufe
		JLabel lblErfahrungsstufe = new JLabel("Erfahrungsstufe:");
		lblErfahrungsstufe.setBounds(20, 260, 115, 25);

		this.lblErfahrungsstufeWert = new JLabel("");
		lblErfahrungsstufeWert.setBounds(145, 260, 166, 25);
		this.lblErfahrungsstufeWert.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//Gehalt
		JLabel lblGehalt = new JLabel("Gehalt:");
		lblGehalt.setBounds(20, 291, 115, 25);
		
		this.lblGehaltWert = new JLabel("");
		lblGehaltWert.setBounds(145, 291, 166, 25);
		this.lblGehaltWert.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//Es wird ein Absolute Layout verwendet. Deswegen muss kein explizites Layout gesetzt werden!
		paAngestellter.setLayout(null);
		paAngestellter.add(lblName);
		paAngestellter.add(lblAngestellteNr);
		paAngestellter.add(lblVorname);
		paAngestellter.add(lblGeschlecht);
		paAngestellter.add(lblGeburtsdatum);
		paAngestellter.add(lblAngestellteNrWert);
		paAngestellter.add(btnAngestellterBearbeiten);
		paAngestellter.add(lblGeburtsdatumWert);
		paAngestellter.add(lblGeschlechtWert);
		paAngestellter.add(lblVornameWert);
		paAngestellter.add(lblNameWert);
		paAngestellter.add(lblTelefon);
		paAngestellter.add(lblTelefonWert);
		paAngestellter.add(lblGehaltsgruppe);
		paAngestellter.add(lblGehaltsgruppeWert);
		paAngestellter.add(lblErfahrungsstufe);
		paAngestellter.add(lblErfahrungsstufeWert);
		paAngestellter.add(lblGehalt);
		paAngestellter.add(lblGehaltWert);
		paAngestellter.add(sepTrenner1);
	}
	
	/*
		 * Ruft ein Fenster für "neuer Mitarbeiter" auf
		 */
	
	public void showAddMitarbeiterWindow() {
		//Erzeugt das Bearbeitungsfenster
		contentAddAngestellter.setResizable(false);
		contentAddAngestellter.setTitle("Angestellten hinzufügen");		
		contentAddAngestellter.setBounds(100, 100, 350, 280);
		
		JPanel bearbeitenPane = new JPanel();
		bearbeitenPane.setLayout(new BorderLayout(0,0));
		contentAddAngestellter.setContentPane(bearbeitenPane);
			
		txtAngestelltenNummer.setEditable(false); //Verhindert das Bearbeiten des Feldes AngestelltenNr. Die Nummer wird automatisch generiert.
			
		//Arrays für die Comboboxes deklarieren und initialisieren
		String[] tage = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
		String[] monate = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		String[] jahr = new String[75];
			
			for(int i = 0; jahr.length > i ;i++) {
				int k = 1944+i;
				Integer.toString(k);
				jahr[i] = ""+k;
			}
		//Comboboxes für Geburtsdatum initialisieren
		boxTag.removeAllItems();
		boxMonat.removeAllItems();
		boxJahr.removeAllItems();
		int t = 0;
		int m = 0;
		int y = 0;
			
		while(t<tage.length) {
			boxTag.addItem(tage[t]);
			t++;
		}
		while(m<monate.length) {
			boxMonat.addItem(monate[m]);
			m++;
		}
		while(y<jahr.length) {
			boxJahr.addItem(jahr[y]);
			y++;
		}
		
		//ComboBox Geschlecht initialisieren
		boxGeschlecht.removeAllItems();
		boxGeschlecht.addItem("Männlich");
		boxGeschlecht.addItem("Weiblich");
		
		//ComboBox Gehaltsgruppe und Erfahrungsstufe initialisieren
		boxGehaltsgruppe.removeAllItems();
		boxErfahrungsstufe.removeAllItems();
		boxGehaltsgruppe.addItem("1");
		boxGehaltsgruppe.addItem("2");
		boxGehaltsgruppe.addItem("3");
		boxGehaltsgruppe.addItem("4");
		boxGehaltsgruppe.addItem("5");
		boxGehaltsgruppe.addItem("6");
		boxErfahrungsstufe.addItem("1");	
		boxErfahrungsstufe.addItem("2");
		boxErfahrungsstufe.addItem("3");
		boxErfahrungsstufe.addItem("4");
		boxErfahrungsstufe.addItem("5");
		boxErfahrungsstufe.addItem("6");
		
		//Buttons Speichern und Abbrechen ActionListener
		btnSpeicherNeuenAngestellten.addActionListener(this.controller);
		btnAbbrechen.addActionListener(this.controller);
			
		//Textfelder, Buttons und Labels dem Panel hinzufügen und positionieren
		
		//Labels
		lblVorname.setBounds(20, 30, 70, 15);
		lblName.setBounds(20, 52, 70, 15);
		lblGeburtstag.setBounds(20, 74, 70, 15);
		lblGeschlecht.setBounds(20,96,70,15);
		lblTelefon.setBounds(20, 118, 70, 15);
		lblAngestelltenNr.setBounds(20, 140, 100, 15);
		lblGehaltsgruppe.setBounds(20, 162, 100, 15);
		lblErfahrungsstufe.setBounds(20, 184, 100, 15);
		bearbeitenPane.add(lblVorname);
		bearbeitenPane.add(lblName);
		bearbeitenPane.add(lblGeburtstag);
		bearbeitenPane.add(lblTelefon);
		bearbeitenPane.add(lblAngestelltenNr);
		bearbeitenPane.add(lblGeschlecht);
		bearbeitenPane.add(lblGehaltsgruppe);
		bearbeitenPane.add(lblErfahrungsstufe);
			
		//Textfelder
		txtVorname.setBounds(120, 30, 200, 19);
		txtName.setBounds(120, 52, 200, 19);
		txtTelefon.setBounds(120, 118, 200, 19);
		txtAngestelltenNummer.setBounds(120, 140, 200, 19);
		
		bearbeitenPane.add(txtVorname);
		bearbeitenPane.add(txtName);
		bearbeitenPane.add(txtTelefon);
		bearbeitenPane.add(txtAngestelltenNummer);
			
		//Datumboxes
		boxTag.setBounds(120, 74, 40, 18);
		boxMonat.setBounds(165, 74, 40, 18);
		boxJahr.setBounds(210, 74, 53, 18);
		bearbeitenPane.add(boxTag);
		bearbeitenPane.add(boxMonat);
		bearbeitenPane.add(boxJahr);
		
		//Geschlechtboxes
		boxGeschlecht.setBounds(120,96,80,18);
		bearbeitenPane.add(boxGeschlecht);
		
		//Gehalts- und Erfahrungsboxen
		boxGehaltsgruppe.setBounds(120, 162, 40, 18);
		boxErfahrungsstufe.setBounds(120, 184, 40, 18);
		bearbeitenPane.add(boxGehaltsgruppe);
		bearbeitenPane.add(boxErfahrungsstufe);
		
		//Buttons
		btnSpeicherNeuenAngestellten.setBounds(220, 210, 100, 20);
		btnAbbrechen.setBounds(118, 210, 100, 20);
		bearbeitenPane.add(btnSpeicherNeuenAngestellten);
		bearbeitenPane.add(btnAbbrechen);
		btnSpeicherNeuenAngestellten.setEnabled(false);
		
		//Wert "angestellter" als Überprüfung
		lblAngestellter.setVisible(false);
		bearbeitenPane.add(lblAngestellter);
		
		//Reset Felder "Angestellter hinzufügen"
		txtVorname.setText("");
		txtName.setText("");
		boxGeschlecht.setSelectedIndex(0);
		boxTag.setSelectedIndex(0);
		boxMonat.setSelectedIndex(0);
		boxJahr.setSelectedIndex(0);
		txtTelefon.setText("");
		txtAngestelltenNummer.setText(Integer.toString(model.getAngestellteListe().size()+1)); 	//generiert eine neue Nummer für den Mitarbeiter. Sie ist fortlaufend, basierend auf Länge model.getAngestellteListe().size()
		
		/*
		 *KeyListener für txtTelefon zur Eingabeüberprüfung aller Felder hinzufügen 
		 */
		txtTelefon.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {

			    String tex = txtTelefon.getText() ;
			     int len = tex.length() ;

			     if (len > 0)  // falls die Eingabe leer ist
			     {
			       char zeichen = tex.charAt(len-1) ;
			       if (! ( (zeichen >= '0') && (zeichen <= '9')))
			       {			         
			         // String berichtigen !!!!
			         txtTelefon.setText(tex.substring(0, len-1));
			       }
			     }
			     
					/*
					 * If else zur Überprüfung, ob etwas eingegeben wurde. 
					 * Vorher wird der Hinzufügen-Butten false gesetzt. 
					 * Es müssen alle Felder befüllt sein
					 */
			     String vorname = txtVorname.getText();
			     String name = txtName.getText();
			     String telefon = txtTelefon.getText();
			     
			     
					if(vorname.length() > 0 && name.length() > 0 && telefon.length() > 1){
						btnSpeicherNeuenAngestellten.setEnabled(true);
					}
					else {
						btnSpeicherNeuenAngestellten.setEnabled(false);
					}
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {

			    String tex = txtTelefon.getText() ;
			     int len = tex.length() ;

			     if (len > 0)  // falls die Eingabe leer ist
			     {
			       char zeichen = tex.charAt(len-1) ;
			       if (! ( (zeichen >= '0') && (zeichen <= '9')))
			       {			         
			         // String berichtigen !!!!
			         txtTelefon.setText(tex.substring(0, len-1));
			       }
			     }
			     
					/*
					 * If else zur Überprüfung, ob etwas eingegeben wurde. 
					 * Vorher wird der Hinzufügen-Butten false gesetzt. 
					 * Es müssen alle Felder befüllt sein
					 */
			     String vorname = txtVorname.getText();
			     String name = txtName.getText();
			     String telefon = txtTelefon.getText();
			     
			     
					if(vorname.length() > 0 && name.length() > 0 && telefon.length() > 1) {
						btnSpeicherNeuenAngestellten.setEnabled(true);
					}
					else {
						btnSpeicherNeuenAngestellten.setEnabled(false);
					}
			     
				}
			
			@Override
			public void keyPressed(KeyEvent e) {
						
			    String tex = txtTelefon.getText() ;
			     int len = tex.length() ;

			     if (len > 0)  // falls die Eingabe leer ist
			     {
			       char zeichen = tex.charAt(len-1) ;
			       if (! ( (zeichen >= '0') && (zeichen <= '9')))
			       {			         
			         // String berichtigen !!!!
			         txtTelefon.setText(tex.substring(0, len-1));
			       }
			     }
					/*
					 * If else zur Überprüfung, ob etwas eingegeben wurde. 
					 * Vorher wird der Hinzufügen-Butten false gesetzt. 
					 * Es müssen alle Felder befüllt sein
					 */
			     String vorname = txtVorname.getText();
			     String name = txtName.getText();
			     String telefon = txtTelefon.getText();
			     
			     
					if(vorname.length() > 0 && name.length() > 0 && telefon.length() > 1) {
						btnSpeicherNeuenAngestellten.setEnabled(true);
					}
					else {
						btnSpeicherNeuenAngestellten.setEnabled(false);
					}
			}
		});
		
		txtVorname.addKeyListener(new KeyListener(){

	@Override
			public void keyTyped(KeyEvent e) {
		
		/*
		 * If else zur Überprüfung, ob etwas eingegeben wurde. 
		 * Vorher wird der Hinzufügen-Butten false gesetzt. 
		 * Es müssen alle Felder befüllt sein
		 */
     String vorname = txtVorname.getText();
     String name = txtName.getText();
     String telefon = txtTelefon.getText();
     
     
		if(vorname.length() > 0 && name.length() > 0 && telefon.length() > 1) {
			btnSpeicherNeuenAngestellten.setEnabled(true);
		}
		else {
			btnSpeicherNeuenAngestellten.setEnabled(false);		
		}
		
	}

	@Override
			public void keyPressed(KeyEvent e) {
		
		/*
		 * If else zur Überprüfung, ob etwas eingegeben wurde. 
		 * Vorher wird der Hinzufügen-Butten false gesetzt. 
		 * Es müssen alle Felder befüllt sein
		 */
     String vorname = txtVorname.getText();
     String name = txtName.getText();
     String telefon = txtTelefon.getText();
     
     
		if(vorname.length() > 0 && name.length() > 0 && telefon.length() > 1) {
			btnSpeicherNeuenAngestellten.setEnabled(true);
		}
		else {
			btnSpeicherNeuenAngestellten.setEnabled(false);	
		}
		
	}

	@Override
			public void keyReleased(KeyEvent e) {
		
		/*
		 * If else zur Überprüfung, ob etwas eingegeben wurde. 
		 * Vorher wird der Hinzufügen-Butten false gesetzt. 
		 * Es müssen alle Felder befüllt sein
		 */
     String vorname = txtVorname.getText();
     String name = txtName.getText();
     String telefon = txtTelefon.getText();
     
     
		if(vorname.length() > 0 && name.length() > 0 && telefon.length() > 1) {
			btnSpeicherNeuenAngestellten.setEnabled(true);
		}
		else {
			btnSpeicherNeuenAngestellten.setEnabled(false);
		}
		
	}

		});
		
		txtName.addKeyListener(new KeyListener(){

	@Override
			public void keyTyped(KeyEvent e) {
		
		/*
		 * If else zur Überprüfung, ob etwas eingegeben wurde. 
		 * Vorher wird der Hinzufügen-Butten false gesetzt. 
		 * Es müssen alle Felder befüllt sein
		 */
     String vorname = txtVorname.getText();
     String name = txtName.getText();
     String telefon = txtTelefon.getText();
     
     
		if(vorname.length() > 0 && name.length() > 0 && telefon.length() > 1) {
			btnSpeicherNeuenAngestellten.setEnabled(true);
		}
		else {
			btnSpeicherNeuenAngestellten.setEnabled(false);
		}
		
	}

	@Override
			public void keyPressed(KeyEvent e) {
		
		/*
		 * If else zur Überprüfung, ob etwas eingegeben wurde. 
		 * Vorher wird der Hinzufügen-Butten false gesetzt. 
		 * Es müssen alle Felder befüllt sein
		 */
     String vorname = txtVorname.getText();
     String name = txtName.getText();
     String telefon = txtTelefon.getText();
     
     
		if(vorname.length() > 0 && name.length() > 0 && telefon.length() > 1) {
			btnSpeicherNeuenAngestellten.setEnabled(true);
		}
		else {
			btnSpeicherNeuenAngestellten.setEnabled(false);
		}

	}
	
	@Override
			public void keyReleased(KeyEvent e) {
		
		/*
		 * If else zur Überprüfung, ob etwas eingegeben wurde. 
		 * Vorher wird der Hinzufügen-Butten false gesetzt. 
		 * Es müssen alle Felder befüllt sein
		 */
     String vorname = txtVorname.getText();
     String name = txtName.getText();
     String telefon = txtTelefon.getText();
     
     
		if(vorname.length() > 0 && name.length() > 0 && telefon.length() > 1) {
			btnSpeicherNeuenAngestellten.setEnabled(true);
		}
		else {
			btnSpeicherNeuenAngestellten.setEnabled(false);
		}

	}

		});
		
		contentAddAngestellter.setVisible(true);
		bearbeitenPane.setVisible(true);
	}
	
	//Methode um die Werte eines neuen Angestellten an den Controller zu übergeben um einen neuen Angestellten zu  erzeugen
	public void addNewAngestellter() {
		
		geburtsdatumNewAngestellter = ""+boxTag.getSelectedItem()+"."+boxMonat.getSelectedItem()+"."+boxJahr.getSelectedItem();
		
		String angestellter = lblAngestellter.getText();
		String angestelltenNr = txtAngestelltenNummer.getText();
		String vorname = txtVorname.getText();
		String nachname = txtName.getText();
		String geburtsdatum = geburtsdatumNewAngestellter;
		int geschlechtNr = boxGeschlecht.getSelectedIndex();
		String geschlecht;
		String telefon = txtTelefon.getText();
		String gehaltsgruppe = (String) boxGehaltsgruppe.getSelectedItem();
		String erfahrungsstufe = (String) boxErfahrungsstufe.getSelectedItem();
		
		//Aus dem Geschlecht eine Zahl im Stringformat generieren um diese ordnungsgemäß als Parameter zu übergeben
		if(geschlechtNr == 0) {
			geschlecht = "0";
		}
		else {
			geschlecht = "1";
		}
		
		controller.createNewAngestellten(angestellter, vorname, nachname, geburtsdatum, geschlecht, telefon, angestelltenNr, gehaltsgruppe, erfahrungsstufe);
		removeListener();
	}
	
	//K - Ruft ein "Sicher löschen?"-Fenster beim klicken von "Angestellten entfernen" auf
	public void showEntferneAngestelltenWindow() {
		
		contentEntferneAngestellten.setResizable(false);
		contentEntferneAngestellten.setTitle("Angestellten entfernen");		
		contentEntferneAngestellten.setSize(420, 120);
		
		JLabel entfernenBestätigen = new JLabel ("Möchten Sie den ausgewählten Angestellten wirklich entfernen?");
		contentEntferneAngestellten.add(entfernenBestätigen);
		entfernenBestätigen.setSize(400,50);
		entfernenBestätigen.setVisible(true);
		
		contentEntferneAngestellten.add(btnEntfernen);
		btnEntfernen.setBounds(0,55,210,30);
		btnEntfernen.addActionListener(this.controller);
		
		contentEntferneAngestellten.add(btnAbbrechen);
		btnAbbrechen.setBounds(210, 55, 210, 30);
		btnAbbrechen.addActionListener(this.controller);
		
		JLabel leer = new JLabel();
		contentEntferneAngestellten.add(leer);
		
		contentEntferneAngestellten.setVisible(true);
			
	}
	
	//Methode um einen angestellten zu bearbeiten
	
	public void showAngestelltenBearbeiten() {
		
		//Erzeugt das Bearbeitungsfenster
				contentAddAngestellter.setResizable(false);
				contentAddAngestellter.setTitle("Angestellten bearbeiten");		
				contentAddAngestellter.setBounds(100, 100, 350, 280);
				
				JPanel bearbeitenPane = new JPanel();
				bearbeitenPane.setLayout(new BorderLayout(0,0));
				contentAddAngestellter.setContentPane(bearbeitenPane);
					
				txtAngestelltenNummer.setEditable(false); //Verhindert das Bearbeiten des Feldes AngestelltenNr. Die Nummer wird automatisch generiert.	
					
				//Buttons Speichern und Abbrechen ActionListener
				btnBestätigen.addActionListener(this.controller);
				btnAbbrechen.addActionListener(this.controller);
					
				//Boxen Gehalt und Erfahrung initialisieren
				boxGehaltsgruppe.removeAllItems();
				boxErfahrungsstufe.removeAllItems();
				boxGehaltsgruppe.addItem("1");
				boxGehaltsgruppe.addItem("2");
				boxGehaltsgruppe.addItem("3");
				boxGehaltsgruppe.addItem("4");
				boxGehaltsgruppe.addItem("5");
				boxGehaltsgruppe.addItem("6");
				boxErfahrungsstufe.addItem("1");	
				boxErfahrungsstufe.addItem("2");
				boxErfahrungsstufe.addItem("3");
				boxErfahrungsstufe.addItem("4");
				boxErfahrungsstufe.addItem("5");
				boxErfahrungsstufe.addItem("6");
				
				//Textfelder, Buttons und Labels dem Panel hinzufügen und positionieren
				
				//Labels
				lblVorname.setBounds(20, 30, 70, 15);
				lblName.setBounds(20, 52, 70, 15);
				lblGeburtstag.setBounds(20, 74, 70, 15);
				lblGeschlecht.setBounds(20,96,70,15);
				lblTelefon.setBounds(20, 118, 70, 15);
				lblAngestelltenNr.setBounds(20, 140, 100, 15);	
				lblGehaltsgruppe.setBounds(20, 162, 100, 15);
				lblErfahrungsstufe.setBounds(20, 184, 100, 15);
				bearbeitenPane.add(lblVorname);
				bearbeitenPane.add(lblName);
				bearbeitenPane.add(lblGeburtstag);
				bearbeitenPane.add(lblTelefon);
				bearbeitenPane.add(lblAngestelltenNr);
				bearbeitenPane.add(lblGeschlecht);
				bearbeitenPane.add(lblGehaltsgruppe);
				bearbeitenPane.add(lblErfahrungsstufe);
					
				//Textfelder/Boxen Bounds
				txtVorname.setBounds(120, 30, 200, 19);
				txtName.setBounds(120, 52, 200, 19);
				txtTelefon.setBounds(120, 118, 200, 19);
				txtAngestelltenNummer.setBounds(120, 140, 200, 19);
				txtGeburtsdatum.setBounds(120, 74, 200, 19);
				txtGeschlecht.setBounds(120, 96, 200, 19);
				boxGehaltsgruppe.setBounds(120, 162, 40, 18);
				boxErfahrungsstufe.setBounds(120, 184, 40, 18);
				
				//Geburtstag und Geschlecht sollen nicht editierbar sein, da solche Änderungen häufiger ungewollt als gewollt passieren...
				txtGeburtsdatum.setEditable(false);
				txtGeschlecht.setEditable(false);
				
				//Textfelder/Boxen zum Bearbeiten hinzufügen
				bearbeitenPane.add(txtVorname);
				bearbeitenPane.add(txtName);
				bearbeitenPane.add(txtTelefon);
				bearbeitenPane.add(txtAngestelltenNummer);
				bearbeitenPane.add(txtGeburtsdatum);
				bearbeitenPane.add(txtGeschlecht);
				bearbeitenPane.add(boxGehaltsgruppe);
				bearbeitenPane.add(boxErfahrungsstufe);
				
				//Buttons
				btnBestätigen.setBounds(220, 210, 100, 20);
				btnAbbrechen.setBounds(118, 210, 100, 20);
				bearbeitenPane.add(btnBestätigen);
				bearbeitenPane.add(btnAbbrechen);
				
				//Wert "angestellter" als Überprüfung
				lblAngestellter.setVisible(false);
				bearbeitenPane.add(lblAngestellter);
				
				//Textfields mit gespeicherten Werten befüllen
				txtVorname.setText(lblVornameWert.getText());
				txtName.setText(lblNameWert.getText());
				txtTelefon.setText(lblTelefonWert.getText());
				txtAngestelltenNummer.setText(lblAngestellteNrWert.getText());
				txtGeburtsdatum.setText(lblGeburtsdatumWert.getText());
				txtGeschlecht.setText(lblGeschlechtWert.getText());
				boxGehaltsgruppe.setSelectedItem(lblGehaltsgruppeWert.getText());
				boxErfahrungsstufe.setSelectedItem(lblErfahrungsstufeWert.getText());
				
				/*
				 *KeyListener für txtTelefon zur Eingabeüberprüfung aller Felder hinzufügen 
				 */
				txtTelefon.addKeyListener(new KeyListener() {
					
					@Override
					public void keyTyped(KeyEvent e) {

					    String tex = txtTelefon.getText() ;
					     int len = tex.length() ;

					     if (len > 0)  // falls die Eingabe leer ist
					     {
					       char zeichen = tex.charAt(len-1) ;
					       if (! ( (zeichen >= '0') && (zeichen <= '9')))
					       {			         
					         // String berichtigen !!!!
					         txtTelefon.setText(tex.substring(0, len-1));
					       }
					     }
					     
							/*
							 * If else zur Überprüfung, ob etwas eingegeben wurde. 
							 * Vorher wird der Hinzufügen-Butten false gesetzt. 
							 * Es müssen alle Felder befüllt sein
							 */
					     String vorname = txtVorname.getText();
					     String name = txtName.getText();
					     String telefon = txtTelefon.getText();
					
					     
							if(vorname.length() > 0 && name.length() > 0 && telefon.length() > 1){								
								btnBestätigen.setEnabled(true);
							}
							else {
								btnBestätigen.setEnabled(false);
							}
						
					}
					
					@Override
					public void keyReleased(KeyEvent e) {

					    String tex = txtTelefon.getText() ;
					     int len = tex.length() ;

					     if (len > 0)  // falls die Eingabe leer ist
					     {
					       char zeichen = tex.charAt(len-1) ;
					       if (! ( (zeichen >= '0') && (zeichen <= '9')))
					       {			         
					         // String berichtigen !!!!
					         txtTelefon.setText(tex.substring(0, len-1));
					       }
					     }
					     
							/*
							 * If else zur Überprüfung, ob etwas eingegeben wurde. 
							 * Vorher wird der Hinzufügen-Butten false gesetzt. 
							 * Es müssen alle Felder befüllt sein
							 */
					     String vorname = txtVorname.getText();
					     String name = txtName.getText();
					     String telefon = ""+txtTelefon.getText();
					     
					     
							if(vorname.length() > 0 && name.length() > 0 && telefon.length() > 1) {
								btnBestätigen.setEnabled(true);
							}
							else {
								btnBestätigen.setEnabled(false);
							}
					     
						}
					
					@Override
					public void keyPressed(KeyEvent e) {
								
					    String tex = txtTelefon.getText() ;
					     int len = tex.length() ;

					     if (len > 0)  // falls die Eingabe leer ist
					     {
					       char zeichen = tex.charAt(len-1) ;
					       if (! ( (zeichen >= '0') && (zeichen <= '9')))
					       {			         
					         // String berichtigen !!!!
					         txtTelefon.setText(tex.substring(0, len-1));
					       }
					     }
					     
							/*
							 * If else zur Überprüfung, ob etwas eingegeben wurde. 
							 * Vorher wird der Hinzufügen-Butten false gesetzt. 
							 * Es müssen alle Felder befüllt sein
							 */
					     String vorname = txtVorname.getText();
					     String name = txtName.getText();
					     String telefon = ""+txtTelefon.getText();
					     
					     
							if(vorname.length() > 0 && name.length() > 0 && telefon.length() > 1){								
								btnBestätigen.setEnabled(true);
							}
							else {
								btnBestätigen.setEnabled(false);
							}						
					}
				});
				
				txtVorname.addKeyListener(new KeyListener(){

			@Override
					public void keyTyped(KeyEvent e) {
				
				/*
				 * If else zur Überprüfung, ob etwas eingegeben wurde. 
				 * Vorher wird der Hinzufügen-Butten false gesetzt. 
				 * Es müssen alle Felder befüllt sein
				 */
		     String vorname = txtVorname.getText();
		     String name = txtName.getText();
		     String telefon = txtTelefon.getText();
		     
		     
				if(vorname.length() > 0 && name.length() > 0 && telefon.length() > 1) {
					btnBestätigen.setEnabled(true);
				}
				else {
					btnBestätigen.setEnabled(false);		
				}
				
			}

			@Override
					public void keyPressed(KeyEvent e) {
				
				/*
				 * If else zur Überprüfung, ob etwas eingegeben wurde. 
				 * Vorher wird der Hinzufügen-Butten false gesetzt. 
				 * Es müssen alle Felder befüllt sein
				 */
		     String vorname = txtVorname.getText();
		     String name = txtName.getText();
		     String telefon = txtTelefon.getText();
		     
		     
				if(vorname.length() > 0 && name.length() > 0 && telefon.length() > 1) {
					btnBestätigen.setEnabled(true);
				}
				else {
					btnBestätigen.setEnabled(false);	
				}
				
			}

			@Override
					public void keyReleased(KeyEvent e) {
				
				/*
				 * If else zur Überprüfung, ob etwas eingegeben wurde. 
				 * Vorher wird der Hinzufügen-Butten false gesetzt. 
				 * Es müssen alle Felder befüllt sein
				 */
		     String vorname = txtVorname.getText();
		     String name = txtName.getText();
		     String telefon = txtTelefon.getText();
		     
		     
				if(vorname.length() > 0 && name.length() > 0 && telefon.length() > 1) {
					btnBestätigen.setEnabled(true);
				}
				else {
					btnBestätigen.setEnabled(false);
				}
				
			}

				});
				
				txtName.addKeyListener(new KeyListener(){

			@Override
					public void keyTyped(KeyEvent e) {
				
				/*
				 * If else zur Überprüfung, ob etwas eingegeben wurde. 
				 * Vorher wird der Hinzufügen-Butten false gesetzt. 
				 * Es müssen alle Felder befüllt sein
				 */
		     String vorname = txtVorname.getText();
		     String name = txtName.getText();
		     String telefon = txtTelefon.getText();
		     
		     
				if(vorname.length() > 0 && name.length() > 0 && telefon.length() > 1) {
					btnBestätigen.setEnabled(true);
				}
				else {
					btnBestätigen.setEnabled(false);
				}
				
			}

			@Override
					public void keyPressed(KeyEvent e) {
				
				/*
				 * If else zur Überprüfung, ob etwas eingegeben wurde. 
				 * Vorher wird der Hinzufügen-Butten false gesetzt. 
				 * Es müssen alle Felder befüllt sein
				 */
		     String vorname = txtVorname.getText();
		     String name = txtName.getText();
		     String telefon = txtTelefon.getText();
		     
		     
				if(vorname.length() > 0 && name.length() > 0 && telefon.length() > 1) {
					btnBestätigen.setEnabled(true);
				}
				else {
					btnBestätigen.setEnabled(false);
				}

			}
			
			@Override
					public void keyReleased(KeyEvent e) {
				
				/*
				 * If else zur Überprüfung, ob etwas eingegeben wurde. 
				 * Vorher wird der Hinzufügen-Butten false gesetzt. 
				 * Es müssen alle Felder befüllt sein
				 */
		     String vorname = txtVorname.getText();
		     String name = txtName.getText();
		     String telefon = txtTelefon.getText();
		     
		     
				if(vorname.length() > 0 && name.length() > 0 && telefon.length() > 1) {
					btnBestätigen.setEnabled(true);
				}
				else {
					btnBestätigen.setEnabled(false);
				}

			}

				});
						
				contentAddAngestellter.setVisible(true);
				bearbeitenPane.setVisible(true);
			}
		
	//Feedback zu "isGeil"
	public void showIsGeil() {
		
		contentAddAngestellter.dispose();

		JDialog isGeil = new JDialog(this, true);
		isGeil.setResizable(false);
 		isGeil.setTitle("Good Job!");
 		isGeil.setBounds(100,100,335, 305);

 		JLabel bestChoice = new JLabel();
 		bestChoice.setIcon(new ImageIcon(FirmaView.class.getResource("/images/bestChoice.jpg")));
 		isGeil.add(bestChoice);
 		bestChoice.setVisible(true);
 		bestChoice.setBounds(0,-10,335,300);

		isGeil.setVisible(true);

}
	
	public void showIsGeil2() {
		
		contentAddAngestellter.dispose();

		JDialog isGeil = new JDialog(this, true);
		isGeil.setResizable(false);
 		isGeil.setTitle("Good Job!");
 		isGeil.setBounds(100,100,335, 305);

 		JLabel bestChoice = new JLabel();
 		bestChoice.setIcon(new ImageIcon(FirmaView.class.getResource("/images/hackerman.jpg")));
 		isGeil.add(bestChoice);
 		bestChoice.setVisible(true);
 		bestChoice.setBounds(0,-10,335,300);

		isGeil.setVisible(true);
		
	}
	

	/**
	 * Liefert den derzeit in der Views selektierten und dargestellten Angestellten respektive sein
	 * Datenobjekt
	 * 
	 * @return Angestelltendaten des selektierten Angestellten
	 */
	public AngestellterModel getGewaehlterAngestellter() {
		return this.gewaehlterAngestellter;
	}


	/**
	 * Liefert die Angestellten-Tabelle der View
	 * 
	 * @return Angestellten-Tabelle
	 */
	public JList<AngestellterModel> getAngestellteTabelle() {
		return this.listAngestellteListe;

	}

	

	/**
	 * Diese Methode setzt alle Anzeigefelder der Angestelltendaten auf "leer" zurück.
	 * 
	 * Sie setzt sozusagen die View in den Ursprungszustand zurück
	 */
	private void resetAngestellter() {
		this.lblAngestellteNrWert.setText("");
		this.lblNameWert.setText("");
		this.lblVornameWert.setText("");
		this.lblGeschlechtWert.setText("");
		this.lblGeburtsdatumWert.setText("");
		this.lblGehaltsgruppeWert.setText("");
		this.lblErfahrungsstufeWert.setText("");
		this.lblTelefonWert.setText("");
		this.lblGehaltsgruppeWert.setText("");
		this.lblErfahrungsstufeWert.setText("");
		this.lblGehaltWert.setText("");

		//Schaltflächen zurücksetzen
		this.btnAngestellterBearbeiten.setEnabled(false);
	}


	/**
	 * Diese Methode entfernt alle Angestellten aus der Angestelltentabelle. Dies ist z. B.
	 * notwendig, wenn eine neue Datei geöffnet wird, um alle zuvor dargestellten Daten
	 * zu entfernen.
	 */
	public void resetAngestellte() {
		this.listAngestellteListe.removeAll();
		resetAngestellter();
	}


	/**
	 * Setzt den Controller zur Delegation der Aufgaben. Er muss vom Typ FirmaController sein
	 * 
	 * @param controller Controller
	 */
	@Override
	public void setController(AbstractController controller) {
		this.controller = (FirmaController) controller;
	}


	/**
	 * Setzt das Model als Datenbasis. Es muss vom Typ FirmaModel sein
	 * 
	 * @param model FirmaModel
	 */
	@Override
	public void setModel(AbstractModel model) {
		this.model = (FirmaModel) model;
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
		if (daten instanceof FirmaDataEvent) {

			/*
			 * FirmaDataEvent. Es liegen veränderte FirmaModel-Informationen vor.
			 */
			FirmaDataEvent pde = (FirmaDataEvent) daten;
			setTitle(pde.getData().getName());

			if (pde.getData().isDirty()) {
				this.btnDateiSpeichern.setEnabled(true);
			} else {
				this.btnDateiSpeichern.setEnabled(false);
			}

		}

		else if (daten instanceof FirmaAngestellterEvent) {

			/*
			 * FirmaAngestellterEvent. Es liegen veränderte AngestellterModel-Informationen vor.
			 * Das Event kann unterschiedliche Formen des EventType annehmen. Im folgenden
			 * Fall werden ADD (Hinzufügen) und REMOVE (Entfernen) unterstützt
			 */
			FirmaAngestellterEvent ppe = (FirmaAngestellterEvent) daten;
			if (ppe.getType() == EventType.ADD) {
				//Angestellter in die Angestellten-Liste (die auf der linken Seite angezeigt wird) aufnehmen			 
				
				mitarbeiter.removeAllElements();
				
				//Überprüft ob es sich um einen gelöschten Eintrag handelt und übergibt das Objekt an die Mitarbeiterliste
				for(int i=0; i<model.getAngestellteListe().size();i++) {
					
					AngestellterModel angestellterPrüfen = model.getAngestellteListe().get(i);
					int indexnummer = mitarbeiter.getSize();
					
					if(angestellterPrüfen.getTelefon().length()<=1) {
						int telefon = Integer.parseInt(angestellterPrüfen.getTelefon());
					
						if(telefon == 0) {
							System.out.println("Es handelt sich bei diesem Objekt um einen gelöschten Mitarbeiter");
						}
						else {
							mitarbeiter.add(indexnummer, model.getAngestellteListe().get(i));
							
							//Systemausgabe
							System.out.println("FIRMAVIEW ADD = " +angestellterPrüfen.getNr());
							System.out.println("FIRMAVIEW ADD = " +angestellterPrüfen.getVorname());
							System.out.println("FIRMAVIEW ADD = " +angestellterPrüfen.getNachname());
						}
					}
					else {
						mitarbeiter.add(indexnummer, model.getAngestellteListe().get(i));
						
						//Systemausgabe
						System.out.println("FIRMAVIEW ADD = " +angestellterPrüfen.getNr());
						System.out.println("FIRMAVIEW ADD = " +angestellterPrüfen.getVorname());
						System.out.println("FIRMAVIEW ADD = " +angestellterPrüfen.getNachname());
					}
				}
				
			}
			else if (ppe.getType() == EventType.REMOVE) {
				//Angestellter aus der Angestellten-Liste entfernen
				
				mitarbeiter.removeAllElements();
				resetAngestellter();

				
				//Überprüft ob es sich um einen gelöschten Eintrag handelt und übergibt das Objekt an die Mitarbeiterliste
				for(int i=0; i<model.getAngestellteListe().size();i++) {
					
					AngestellterModel angestellterPrüfen = model.getAngestellteListe().get(i);
					int indexnummer = mitarbeiter.getSize();
					
					if(angestellterPrüfen.getTelefon().length()<=1) {
						int telefon = Integer.parseInt(angestellterPrüfen.getTelefon());
					
						if(telefon == 0) {
							System.out.println("Es handelt sich bei diesem Objekt um einen gelöschten Mitarbeiter");
						}
						else {
							mitarbeiter.add(indexnummer, model.getAngestellteListe().get(i));
							
							//Systemausgabe
							System.out.println("FIRMAVIEW ADD = " +angestellterPrüfen.getNr());
							System.out.println("FIRMAVIEW ADD = " +angestellterPrüfen.getVorname());
							System.out.println("FIRMAVIEW ADD = " +angestellterPrüfen.getNachname());
						}
					}
					else {
						mitarbeiter.add(indexnummer, model.getAngestellteListe().get(i));
						
						//Systemausgabe
						System.out.println("FIRMAVIEW ADD = " +angestellterPrüfen.getNr());
						System.out.println("FIRMAVIEW ADD = " +angestellterPrüfen.getVorname());
						System.out.println("FIRMAVIEW ADD = " +angestellterPrüfen.getNachname());
					}
				}

			}
		}

		else if (daten instanceof AngestellterDataEvent) {

			/*
			 * AngestellterDataEvent. Es liegen veränderte AngestellterModel-Informationen für den derzeit 
			 * dargestellten Angestellten vor. Es werden alle Felder der View mit den AngestellterModel-
			 * Daten neu befüllt
			 */
			AngestellterDataEvent pde = (AngestellterDataEvent) daten;
			if (this.gewaehlterAngestellter.getNr() == pde.getData().getNr()) {
				this.lblAngestellteNrWert.setText(""+pde.getData().getNr());
				this.lblNameWert.setText(pde.getData().getNachname());
				this.lblVornameWert.setText(pde.getData().getVorname());
				this.lblGeschlechtWert.setText((pde.getData().getGeschlecht() == 0) ? "Männlich" : "Weiblich");
				this.lblGeburtsdatumWert.setText(pde.getData().getGeburtsdatum());
				this.lblTelefonWert.setText(pde.getData().getTelefon());
				this.lblGehaltsgruppeWert.setText(""+pde.getData().getGehaltsgruppe());
				this.lblErfahrungsstufeWert.setText(""+pde.getData().getErfahrungsstufe());
				this.lblGehaltWert.setText(""+controller.gehaltLaden(gewaehlterAngestellter));			}
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
					FirmaView.this.createContent();
					FirmaView.this.setVisible(true);
				} catch (Exception e) {
					return; //Keine EDT-Fehler tracken
				}
			}
		});
	}


	public void removeListener() {
		btnSpeicherNeuenAngestellten.removeActionListener(controller);
		btnAbbrechen.removeActionListener(controller);
	}
	
	public JButton  getBtnDateiLaden() {
		return btnDateiLaden;
	}


	public JButton getBtnNeuerAngestellter() {
		return btnNeuerAngestellter;
	}

	
	public JButton getBtnEntferneAngestellten() {
		return btnEntferneAngestellten;
	}
	

	public JButton getBtnSpeichern() {
		return btnDateiSpeichern;
	}


	public JButton getBtnAngestellterBearbeiten() {
		return btnAngestellterBearbeiten;
	}
	
	public JButton getBtnSpeicherNeuenAngestellten() {
		return btnSpeicherNeuenAngestellten;
	}
	
	public JButton getBtnAbbrechen() {
		return btnAbbrechen;
	}
	
	//Entfernen-Button im Dialog "Angestellten entfernen"
		public JButton getBtnEntfernen() {
		return btnEntfernen;
	}
		
	public JButton getBtnBestätigen() {
		return btnBestätigen;
	}
}