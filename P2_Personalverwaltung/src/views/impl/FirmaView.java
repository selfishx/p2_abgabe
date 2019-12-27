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
 * Die FirmaView stellt alle Informationen der Software geb�ndelt dar. Sie ist die 
 * Hauptdarstellungsfl�che der Anwendung, auf der alle Komponenten positioniert sind. Die 
 * FirmaView enth�lt eine ToolBar, eine SplitPane sowie innerhalb der SplitPane eine Tabelle 
 * (links) und eine JPane mit weiteren Elementen zur Darstellung von Inhalten (rechts). Die 
 * FirmaView meldet sich als Beobachter bei dem FirmaModel sowie beim aktuell angezeigten 
 * AngestellterModel an (bzw ab, sobald der Kunde gewechselt wird)
 * 
 * @author  Jens Sterk
 * @version 1.0
 *
 */
public class FirmaView extends JFrame implements Observer, InterfaceView {


	//Wert zur Serialisierung von SWING-Objketen. Hier nur, um Compiler-WARNING zu unterdr�cken!
	private static final long serialVersionUID = 1L;


	//Verweis zum FirmaModel, um auf dessen Daten zuzugreifen
	private FirmaModel model;


	//Verweis zum FirmaController, um Aufgaben an ihn zu delegieren
	private FirmaController controller;


	//Verweis auf den derzeit ausgew�hlten und dargestellten Angestellten
	private AngestellterModel gewaehlterAngestellter;

	public JDialog contentAddAngestellter = new JDialog(this, true);//Dialog f�r Angestellter hinzuf�gen
	public JDialog contentEntferneAngestellten = new JDialog();//Dialog f�r Angestellten entfernen
	
	
	private JLabel lblAngestellteNrWert;	  //Feld zur Anzeige der Angestellten-Nr
	private JLabel lblNameWert;			  //Feld zur Anzeige des Angestellten-Namen
	private JLabel lblVornameWert;		  //Feld zur Anzeige des Angestellten-Vornamen
	private JLabel lblGeschlechtWert;	  //Feld zur Anzeige des Angestellten-Geschlechts
	private JLabel lblGeburtsdatumWert;	  //Feld zur Anzeige des Angestellten-Geburtsdatum
	private JLabel lblStrasseWert;		  //Feld zur Anzeige der Angestellten-Stra�e
	private JLabel lblPlzWert;			  //Feld zur Anzeige der Angestellten-PLZ
	private JLabel lblOrtWert;			  //Feld zur Anzeige der Angestellten-Stadt
	private JLabel lblTelefonWert;		  //Feld zur Anzeige der Angestellten-Telefonnummer
	
	//AnzeigeLabels f�r Mitarbeiter hinzuf�gen deklarieren und initialisieren
	private JLabel lblName = new JLabel("Name: ");
	private JLabel lblVorname = new JLabel("Vorname: ");
	private JLabel lblGeburtstag = new JLabel("Geburtstag: ");
	private JLabel lblGeschlecht = new JLabel("Geschlecht: ");
	private JLabel lblAngestelltenNr = new JLabel("AngestelltenNr.: "); //Wird automatisch ermittelt
	private JLabel lblTelefon = new JLabel("Telefon: ");
	private JLabel lblAngestellter = new JLabel("angestellter");//Gibt den Wert "angestellter" zur �berpr�fung weiter
	
	//TextFelder f�r Mitarbeiter hinzuf�gen deklarieren
	private JTextField txtName = new JTextField();
	private JTextField txtVorname = new JTextField();
	public  JTextField txtTelefon = new JTextField();
	private JTextField txtAngestelltenNummer = new JTextField();
	
	private String geburtsdatumNewAngestellter; //String Geburtsdatum zur Umwandlung aus der ComboBox
	
	//ComboBoxesf�r Mitarbeiter hinzuf�gen deklarieren
	private JComboBox<String> boxTag = new JComboBox<>();
	private JComboBox<String> boxMonat = new JComboBox<>();
	private JComboBox<String> boxJahr = new JComboBox<>();
	private JComboBox<String> boxGeschlecht = new JComboBox<>();

	private JList<AngestellterModel> listAngestellteListe;	  	//Liste aller Angestellten der Firma
	
	private JButton btnAngestellterBearbeiten; 				//Schaltfl�che "Angestellter bearbeiten"
	private JButton btnDateiSpeichern;	  				//Schaltfl�che "Datei speichern"
	private JButton btnDateiLaden;		  				//Schaltfl�che "Datei laden"
	private JButton btnNeuerAngestellter;	  				//Schaltfl�che "Neuer Angestellter"
	private JButton btnEntferneAngestellten;			//Schaltfl�che "Angestellten entfernen"
	private JButton btnSpeicherNeuenAngestellten = new JButton("Hinzuf�gen");//Neuen mitarbeiter hinzuf�gen im Dialog neuer Angestellter
	private JButton btnAbbrechen = new JButton("Abbrechen");//Aktion Mitarbeiter hinzuf�gen abbrechen im Dialog neuer Angestellter
	private JButton btnEntfernen = new JButton("Entfernen");//Aktion Mitarbeiter entfernen best�tigen im Dialog Angestellten entfernen
	/*
	 * Liste aller Angestellten, die bei einem EVENT update bef�llt wird
	 * Die Liste wird der JList listAngestellteListe �bergeben
	 */
	private DefaultListModel<AngestellterModel> mitarbeiter = new DefaultListModel<>(); 
	

	/**
	 * Constructor.
	 * Erzeugt die FirmaView und setzt das Model. Dem Model wird als Beobachter die View 
	 * hinzugef�gt, d.h. bei �nderungen am Model wird die View unverz�glich informiert.
	 * 
	 * @param firma FirmaModel
	 */
	public FirmaView(FirmaModel firma) {
		setResizable(false);
		this.model = firma;

		//Die View als Beobachter beim FirmaModel-Objekt anmelden.
		//�nderungen im Model werden direkt der View mitgeteilt.
		//Daf�r steht die Methode update zur Verf�gung.
		this.model.addObserver(this);
	}


	/**
	 * Komponenten des Hauptfensters erzeugen und hinzuf�gen
	 */
	private void createContent() {

		/*
		 * Setzen des Fenster-Titels, Aktivieren des roten X (oben rechts) zum Fenster schlie�en
		 * Gr��e des Fensters und Startpunkt (X,Y) auf dem Bildschirm definieren
		 */
		setTitle(this.model.getName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 913, 551);

		/*
		 * Haupt-Zeichenfl�che (JPane-Komponente) des Fensters, auf dem alle anderen Komponenten 
		 * hinzugef�gt werden.
		 * 
		 * Setzen eines leeren Rahmens (EmptyBorder) mit 5px Abstand zu allen Seiten, Hinzuf�gen 
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
		btnEntferneAngestellten.setEnabled(true);
		
		//Weiterleiten der Aktion beim Mausklick an den Controller
		btnEntferneAngestellten.addActionListener(this.controller);
		
		

		//"Speichern"-Button
		this.btnDateiSpeichern = new JButton("Speichern");
		this.btnDateiSpeichern.setIcon(new ImageIcon(FirmaView.class.getResource("/images/datei_speichern.png")));
		toolBar.add(this.btnDateiSpeichern);
		this.btnDateiSpeichern.setEnabled(false);

		//Schaltfl�che "Angestellter bearbeiten"
		this.btnAngestellterBearbeiten = new JButton("Angestellter bearbeiten");
		btnAngestellterBearbeiten.setBounds(496, 38, 121, 23);
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
		 * SelectionListener der Angestellte-Tabelle. Dieser Listener reagiert auf Ver�nderungen der 
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
				//"Termin Bearbeiten"-Button aktiviert und die Variable gewaehlterTermin mit dem 
				//selektierten Index belegt.
				if (FirmaView.this.listAngestellteListe.getSelectedIndex() > -1 && 
						!event.getValueIsAdjusting()) {

					//Wurde bereits zuvor ein Angestellter ausgew�hlt, so muss der Beobachterstatus
					//der View von diesem Angestellten entfernt werden.
					//Danach Reset der Variable gewaehlterAngestellter inkl. GUI-Bereinigung
					if (FirmaView.this.gewaehlterAngestellter != null) {
						FirmaView.this.gewaehlterAngestellter.deleteObserver(FirmaView.this);
						FirmaView.this.gewaehlterAngestellter = null;
					}
					FirmaView.this.resetAngestellter();

					//Anhand des selektierten Eintrages wird die erste (0-basiert!) Spalte 
					//(AngestellteNr) ausgelesen
					//Eine Schleife z�hlt die gel�schten Mitarbeiter und gibt den Wert an den SelectedIndex weiter.
					
					int gel�schte = 1;
					
					for(int i = 0; i <= FirmaView.this.listAngestellteListe.getSelectedIndex();i++) {
						
						AngestellterModel angestellterPr�fen = model.getAngestellteListe().get(i);
						int telefon = Integer.parseInt(angestellterPr�fen.getTelefon());
						
						if(telefon == 0) {
							gel�schte = gel�schte + 1;
						}
					}
						int angestellteNr = FirmaView.this.listAngestellteListe.getSelectedIndex() +gel�schte;
					

					//Mittels des FirmaModel wird das passende AngestellterModel-Objekt
					//anhand der AngestellteNr geliefert.
					AngestellterModel angestellter = FirmaView.this.model.getAngestellter(angestellteNr);

					//Bef�llung der Felder mit den Werten des AngestellterModel-Objektes
					FirmaView.this.lblAngestellteNrWert.setText(""+angestellter.getNr());
					FirmaView.this.lblNameWert.setText(angestellter.getNachname());
					FirmaView.this.lblVornameWert.setText(angestellter.getVorname());
					FirmaView.this.lblGeschlechtWert.setText((angestellter.getGeschlecht() == 0) ? "M�nnlich" : "Weiblich");
					FirmaView.this.lblGeburtsdatumWert.setText(angestellter.getGeburtsdatum());
					FirmaView.this.lblTelefonWert.setText(angestellter.getTelefon());

					//Buttons aktivieren
					FirmaView.this.btnAngestellterBearbeiten.setEnabled(true);

					//Variable gewaehlterAngestellter belegen
					FirmaView.this.gewaehlterAngestellter = angestellter;

					//Die View als Beobachter beim AngestellterModel-Objekt anmelden.
					//�nderungen im Model werden direkt der View mitgeteilt.
					//Daf�r steht die Methode update zur Verf�gung.
					FirmaView.this.gewaehlterAngestellter.addObserver(FirmaView.this);
				}
			}
		});
		

		//Die Informationen liegwn innerhalb einer JScrollPane-Komponente, damit, wenn mehr 
		//Eintr�ge als darstellbare Fl�che vorhanden ist, Bildlaufleisten angezeigt werden.
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
		paAngestellter.add(lblOrtWert);
		paAngestellter.add(lblPlzWert);
		paAngestellter.add(lblStrasseWert);
		paAngestellter.add(sepTrenner1);
	}
	
	/*
		 * Ruft ein Fenster f�r "neuer Mitarbeiter" auf
		 */
	
	public void showAddMitarbeiterWindow() {
		//Erzeugt das Bearbeitungsfenster
		contentAddAngestellter.setResizable(false);
		contentAddAngestellter.setTitle("Mitarbeiter hinzuf�gen");		
		contentAddAngestellter.setBounds(100, 100, 350, 250);
		
		JPanel bearbeitenPane = new JPanel();
		bearbeitenPane.setLayout(new BorderLayout(0,0));
		contentAddAngestellter.setContentPane(bearbeitenPane);
			
		txtAngestelltenNummer.setEditable(false); //Verhindert das Bearbeiten des Feldes AngestelltenNr. Die Nummer wird automatisch generiert.
			
		//Arrays f�r die Comboboxes deklarieren und initialisieren
		String[] tage = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14","15","16","17","18","19","20","21","23","24","25","26","27","28","29","30","31"};
		String[] monate = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		String[] jahr = new String[75];
			
			for(int i = 0; jahr.length > i ;i++) {
				int k = 1944+i;
				Integer.toString(k);
				jahr[i] = ""+k;
			}
		//Comboboxes f�r Geburtsdatum initialisieren
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
		boxGeschlecht.addItem("M�nnlich");
		boxGeschlecht.addItem("Weiblich");
			
		//Buttons Speichern und Abbrechen ActionListener
		btnSpeicherNeuenAngestellten.addActionListener(this.controller);
		btnAbbrechen.addActionListener(this.controller);
			
		//Textfelder, Buttons und Labels dem Panel hinzuf�gen und positionieren
		
		//Labels
		lblVorname.setBounds(20, 30, 70, 15);
		lblName.setBounds(20, 52, 70, 15);
		lblGeburtstag.setBounds(20, 74, 70, 15);
		lblGeschlecht.setBounds(20,96,70,15);
		lblTelefon.setBounds(20, 118, 70, 15);
		lblAngestelltenNr.setBounds(20, 140, 100, 15);	
		bearbeitenPane.add(lblVorname, BorderLayout.CENTER);
		bearbeitenPane.add(lblName, BorderLayout.CENTER);
		bearbeitenPane.add(lblGeburtstag, BorderLayout.CENTER);
		bearbeitenPane.add(lblTelefon, BorderLayout.CENTER);
		bearbeitenPane.add(lblAngestelltenNr, BorderLayout.CENTER);
		bearbeitenPane.add(lblGeschlecht);
			
		//Textfelder
		txtVorname.setBounds(120, 30, 200, 19);
		txtName.setBounds(120, 52, 200, 19);
		txtTelefon.setBounds(120, 118, 200, 19);
		txtAngestelltenNummer.setBounds(120, 140, 200, 19);
		
		bearbeitenPane.add(txtVorname, BorderLayout.CENTER);
		bearbeitenPane.add(txtName, BorderLayout.CENTER);
		bearbeitenPane.add(txtTelefon, BorderLayout.CENTER);
		bearbeitenPane.add(txtAngestelltenNummer, BorderLayout.CENTER);
			
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
		
		//Buttons
		btnSpeicherNeuenAngestellten.setBounds(220, 170, 100, 20);
		btnAbbrechen.setBounds(118, 170, 100, 20);
		bearbeitenPane.add(btnSpeicherNeuenAngestellten, BorderLayout.CENTER);
		bearbeitenPane.add(btnAbbrechen, BorderLayout.CENTER);
		btnSpeicherNeuenAngestellten.setEnabled(false);
		
		//Wert "angestellter" als �berpr�fung
		lblAngestellter.setVisible(false);
		bearbeitenPane.add(lblAngestellter);
		
		//Reset Felder "Angestellter hinzuf�gen"
		txtVorname.setText("");
		txtName.setText("");
		boxGeschlecht.setSelectedIndex(0);
		boxTag.setSelectedIndex(0);
		boxMonat.setSelectedIndex(0);
		boxJahr.setSelectedIndex(0);
		txtTelefon.setText("");
		txtAngestelltenNummer.setText(Integer.toString(model.getAngestellteListe().size()+1)); 	//generiert eine neue Nummer f�r den Mitarbeiter. Sie ist fortlaufend, basierend auf L�nge model.getAngestellteListe().size()
		
		/*
		 *KeyListener f�r txtTelefon zur Eingabe�berpr�fung aller Felder hinzuf�gen 
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
					 * If else zur �berpr�fung, ob etwas eingegeben wurde. 
					 * Vorher wird der Hinzuf�gen-Butten false gesetzt. 
					 * Es m�ssen alle Felder bef�llt sein
					 */
			     String vorname = txtVorname.getText();
			     String name = txtName.getText();
			     String telefon = txtTelefon.getText();
			     
			     
					if(vorname.length() > 0 && name.length() > 0 && telefon.length() > 0) {
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
					 * If else zur �berpr�fung, ob etwas eingegeben wurde. 
					 * Vorher wird der Hinzuf�gen-Butten false gesetzt. 
					 * Es m�ssen alle Felder bef�llt sein
					 */
			     String vorname = txtVorname.getText();
			     String name = txtName.getText();
			     String telefon = txtTelefon.getText();
			     
			     
					if(vorname.length() > 0 && name.length() > 0 && telefon.length() > 0) {
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
			}
		});
		
		txtVorname.addKeyListener(new KeyListener(){

	@Override
	public void keyTyped(KeyEvent e) {
		
		/*
		 * If else zur �berpr�fung, ob etwas eingegeben wurde. 
		 * Vorher wird der Hinzuf�gen-Butten false gesetzt. 
		 * Es m�ssen alle Felder bef�llt sein
		 */
     String vorname = txtVorname.getText();
     String name = txtName.getText();
     String telefon = txtTelefon.getText();
     
     
		if(vorname.length() > 0 && name.length() > 0 && telefon.length() > 0) {
			btnSpeicherNeuenAngestellten.setEnabled(true);
		}
		else {
			btnSpeicherNeuenAngestellten.setEnabled(false);		
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		/*
		 * If else zur �berpr�fung, ob etwas eingegeben wurde. 
		 * Vorher wird der Hinzuf�gen-Butten false gesetzt. 
		 * Es m�ssen alle Felder bef�llt sein
		 */
     String vorname = txtVorname.getText();
     String name = txtName.getText();
     String telefon = txtTelefon.getText();
     
     
		if(vorname.length() > 0 && name.length() > 0 && telefon.length() > 0) {
			btnSpeicherNeuenAngestellten.setEnabled(true);
		}
		else {
			btnSpeicherNeuenAngestellten.setEnabled(false);	
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		/*
		 * If else zur �berpr�fung, ob etwas eingegeben wurde. 
		 * Vorher wird der Hinzuf�gen-Butten false gesetzt. 
		 * Es m�ssen alle Felder bef�llt sein
		 */
     String vorname = txtVorname.getText();
     String name = txtName.getText();
     String telefon = txtTelefon.getText();
     
     
		if(vorname.length() > 0 && name.length() > 0 && telefon.length() > 0) {
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
		 * If else zur �berpr�fung, ob etwas eingegeben wurde. 
		 * Vorher wird der Hinzuf�gen-Butten false gesetzt. 
		 * Es m�ssen alle Felder bef�llt sein
		 */
     String vorname = txtVorname.getText();
     String name = txtName.getText();
     String telefon = txtTelefon.getText();
     
     
		if(vorname.length() > 0 && name.length() > 0 && telefon.length() > 0) {
			btnSpeicherNeuenAngestellten.setEnabled(true);
		}
		else {
			btnSpeicherNeuenAngestellten.setEnabled(false);
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		/*
		 * If else zur �berpr�fung, ob etwas eingegeben wurde. 
		 * Vorher wird der Hinzuf�gen-Butten false gesetzt. 
		 * Es m�ssen alle Felder bef�llt sein
		 */
     String vorname = txtVorname.getText();
     String name = txtName.getText();
     String telefon = txtTelefon.getText();
     
     
		if(vorname.length() > 0 && name.length() > 0 && telefon.length() > 0) {
			btnSpeicherNeuenAngestellten.setEnabled(true);
		}
		else {
			btnSpeicherNeuenAngestellten.setEnabled(false);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		/*
		 * If else zur �berpr�fung, ob etwas eingegeben wurde. 
		 * Vorher wird der Hinzuf�gen-Butten false gesetzt. 
		 * Es m�ssen alle Felder bef�llt sein
		 */
     String vorname = txtVorname.getText();
     String name = txtName.getText();
     String telefon = txtTelefon.getText();
     
     
		if(vorname.length() > 0 && name.length() > 0 && telefon.length() > 0) {
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
	
	//Methode um die Werte eines neuen Angestellten an den Controller zu �bergeben um einen neuen Angestellten zu  erzeugen
	public void addNewAngestellter() {
		
		geburtsdatumNewAngestellter = ""+boxTag.getSelectedItem()+"."+boxMonat.getSelectedItem()+"."+boxJahr.getSelectedItem();
		
		String angestellter = lblAngestellter.getText();
		String angestelltenNr = txtAngestelltenNummer.getText();
		String vorname = txtVorname.getText();
		String nachname = txtName.getText();
		String geburtsdatum = geburtsdatumNewAngestellter;
		String geschlecht = ""+boxGeschlecht.getSelectedItem();
		String telefon = txtTelefon.getText();
		
		//Aus dem Geschlecht eine Zahl im Stringformat generieren um diese ordnungsgem�� als Parameter zu �bergeben
		if(geschlecht == "M�nnlich") {
			geschlecht = "0";
		}
		else {
			geschlecht = "1";
		}
		
		controller.createNewAngestellten(angestellter, vorname, nachname, geburtsdatum, geschlecht, telefon, angestelltenNr);
		removeListener();
	}
	
	//K - Ruft ein "Sicher l�schen?"-Fenster beim klicken von "Angestellten entfernen" auf
	public void showEntferneAngestelltenWindow() {
		
		contentEntferneAngestellten.setResizable(false);
		contentEntferneAngestellten.setTitle("Angestellten entfernen");		
		contentEntferneAngestellten.setSize(420, 120);
		contentEntferneAngestellten.setVisible(true);
		
		JLabel entfernenBest�tigen = new JLabel ("M�chten Sie den ausgew�hlten Angestellten wirklich entfernen?");
		contentEntferneAngestellten.add(entfernenBest�tigen);
		entfernenBest�tigen.setSize(400,50);
		entfernenBest�tigen.setVisible(true);
		
		contentEntferneAngestellten.add(btnEntfernen);
		btnEntfernen.setBounds(0,55,210,30);
		
		contentEntferneAngestellten.add(btnAbbrechen);
		btnAbbrechen.setBounds(210, 55, 210, 30);
		
		
		//TODO K-Actionlistener und Events hinzuf�gen
		
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
	 * Diese Methode setzt alle Anzeigefelder der Angestelltendaten auf "leer" zur�ck.
	 * 
	 * Sie setzt sozusagen die View in den Ursprungszustand zur�ck
	 */
	private void resetAngestellter() {
		this.lblAngestellteNrWert.setText("");
		this.lblNameWert.setText("");
		this.lblVornameWert.setText("");
		this.lblGeschlechtWert.setText("");
		this.lblGeburtsdatumWert.setText("");
		this.lblStrasseWert.setText("");
		this.lblPlzWert.setText("");
		this.lblOrtWert.setText("");
		this.lblTelefonWert.setText("");

		//Schaltfl�chen zur�cksetzen
		this.btnAngestellterBearbeiten.setEnabled(false);
	}


	/**
	 * Diese Methode entfernt alle Angestellten aus der Angestelltentabelle. Dies ist z. B.
	 * notwendig, wenn eine neue Datei ge�ffnet wird, um alle zuvor dargestellten Daten
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
	 * - beim Eintreten eines Ereignisses - diese Methode auf, um die View �ber �nderungen zu 
	 * informieren.
	 * 
	 * @param quelle Objekt, das beobachtet wird und das Ereignis meldet
	 * @param daten  Event-Informationen
	 */
	@Override
	public void update(Observable quelle, Object daten) {

		/*
		 * Pr�fung, ob die Variable daten eine bestimmte Objektstruktur aufweist. Es wird davon 
		 * ausgegangen, dass daten Event-Informationen beinhaltet und daher vom Typ eine konkrete 
		 * Implementierung von AbstractEvent ist.
		 */
		if (daten instanceof FirmaDataEvent) {

			/*
			 * FirmaDataEvent. Es liegen ver�nderte FirmaModel-Informationen vor.
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
			 * FirmaAngestellterEvent. Es liegen ver�nderte AngestellterModel-Informationen vor.
			 * Das Event kann unterschiedliche Formen des EventType annehmen. Im folgenden
			 * Fall werden ADD (Hinzuf�gen) und REMOVE (Entfernen) unterst�tzt
			 */
			FirmaAngestellterEvent ppe = (FirmaAngestellterEvent) daten;
			if (ppe.getType() == EventType.ADD) {
				//Angestellter in die Angestellten-Liste (die auf der linken Seite angezeigt wird) aufnehmen			 
				
				mitarbeiter.removeAllElements();
				
				//�berpr�ft ob es sich um einen gel�schten Eintrag handelt und �bergibt das Objekt an die Mitarbeiterliste
				for(int i=0; i<model.getAngestellteListe().size();i++) {
					
					AngestellterModel angestellterPr�fen = model.getAngestellteListe().get(i);
					int indexnummer = mitarbeiter.getSize();
					int telefon = Integer.parseInt(angestellterPr�fen.getTelefon());
					
					if(telefon == 0) {
						System.out.println("Es handelt sich bei diesem Objekt um einen gel�schten Mitarbeiter");
					}
					else {
						mitarbeiter.add(indexnummer, model.getAngestellteListe().get(i));
						
						//Systemausgabe
						System.out.println("FIRMAVIEW ADD = " +angestellterPr�fen.getNr());
						System.out.println("FIRMAVIEW ADD = " +angestellterPr�fen.getVorname());
						System.out.println("FIRMAVIEW ADD = " +angestellterPr�fen.getNachname());
					}
				}
				
			}
			else if (ppe.getType() == EventType.REMOVE) {
				//Angestellter aus der Angestellten-Liste entfernen
				
				mitarbeiter.removeAllElements();
				
				//�berpr�ft ob es sich um einen gel�schten Eintrag handelt und �bergibt das Objekt an die Mitarbeiterliste
				for(int i=0; i<model.getAngestellteListe().size();i++) {
					
					AngestellterModel angestellterPr�fen = model.getAngestellteListe().get(i);
					int indexnummer = mitarbeiter.getSize();
					int telefon = Integer.parseInt(angestellterPr�fen.getTelefon());
					
					if(telefon == 0) {
						System.out.println("Es handelt sich bei diesem Objekt um einen gel�schten Mitarbeiter");
					}
					else {
						mitarbeiter.add(indexnummer, model.getAngestellteListe().get(i));
						
						//Systemausgabe
						System.out.println("FIRMAVIEW ADD = " +angestellterPr�fen.getNr());
						System.out.println("FIRMAVIEW ADD = " +angestellterPr�fen.getVorname());
						System.out.println("FIRMAVIEW ADD = " +angestellterPr�fen.getNachname());
					}
				}

			}
		}

		else if (daten instanceof AngestellterDataEvent) {

			/*
			 * AngestellterDataEvent. Es liegen ver�nderte AngestellterModel-Informationen f�r den derzeit 
			 * dargestellten Angestellten vor. Es werden alle Felder der View mit den AngestellterModel-
			 * Daten neu bef�llt
			 */
			AngestellterDataEvent pde = (AngestellterDataEvent) daten;
			if (this.gewaehlterAngestellter.getNr() == pde.getData().getNr()) {
				this.lblAngestellteNrWert.setText(""+pde.getData().getNr());
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
	 * und �ffnet dann das Programmfenster (JFrame) mittels der Swing EventQueue (Threading).
	 * 
	 * Somit wird die GUI im EDT (Event Dispatch Thread) ausgef�hrt.
	 */
	public void zeigeFenster() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			System.out.println("Fehler beim Setzen des Look And Feel f�r die Software. Abbruch");
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
}