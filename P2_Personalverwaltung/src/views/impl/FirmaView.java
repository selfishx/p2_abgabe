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
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
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


	private JLabel lblAngestellteNrWert;	  //Feld zur Anzeige der Angestellten-Nr
	private JLabel lblNameWert;			  //Feld zur Anzeige des Angestellten-Namen
	private JLabel lblVornameWert;		  //Feld zur Anzeige des Angestellten-Vornamen
	private JLabel lblGeschlechtWert;	  //Feld zur Anzeige des Angestellten-Geschlechts
	private JLabel lblGeburtsdatumWert;	  //Feld zur Anzeige des Angestellten-Geburtsdatum
	private JLabel lblStrasseWert;		  //Feld zur Anzeige der Angestellten-Straße
	private JLabel lblPlzWert;			  //Feld zur Anzeige der Angestellten-PLZ
	private JLabel lblOrtWert;			  //Feld zur Anzeige der Angestellten-Stadt
	private JLabel lblTelefonWert;		  //Feld zur Anzeige der Angestellten-Telefonnummer

	private JList<AngestellterModel> listAngestellteListe;	  	//Liste aller Angestellten der Firma
	private JButton btnAngestellterBearbeiten; 				//Schaltfläche "Angestellter bearbeiten"
	private JButton btnDateiSpeichern;	  				//Schaltfläche "Datei speichern"
	private JButton btnDateiLaden;		  				//Schaltfläche "Datei laden"
	private JButton btnNeuerAngestellter;	  				//Schaltfläche "Neuer Angestellter"
	
	private DefaultListModel<AngestellterModel> mitarbeiter = new DefaultListModel<>();
	

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

		//"Speichern"-Button
		this.btnDateiSpeichern = new JButton("Speichern");
		this.btnDateiSpeichern.setIcon(new ImageIcon(FirmaView.class.getResource("/images/datei_speichern.png")));
		toolBar.add(this.btnDateiSpeichern);
		this.btnDateiSpeichern.setEnabled(false);

		//Schaltfläche "Angestellter bearbeiten"
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

		//Liste "Angestellte" erstellen
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
				//"Termin Bearbeiten"-Button aktiviert und die Variable gewaehlterTermin mit dem 
				//selektierten Index belegt.
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

					//Anhand des selektierten Eintrages wird die erste (0-basiert!) Spalte 
					//(AngestellteNr) ausgelesen
					int angestellteNr = FirmaView.this.listAngestellteListe.getSelectedIndex() + 1;

					//Mittels des FirmaModel wird das passende AngestellterModel-Objekt
					//anhand der AngestellteNr geliefert.
					AngestellterModel angestellter = FirmaView.this.model.getAngestellter(angestellteNr);

					//Befüllung der Felder mit den Werten des AngestellterModel-Objektes
					FirmaView.this.lblAngestellteNrWert.setText(""+angestellter.getNr());
					FirmaView.this.lblNameWert.setText(angestellter.getNachname());
					FirmaView.this.lblVornameWert.setText(angestellter.getVorname());
					FirmaView.this.lblGeschlechtWert.setText((angestellter.getGeschlecht() == 0) ? "Männlich" : "Weiblich");
					FirmaView.this.lblGeburtsdatumWert.setText(angestellter.getGeburtsdatum());
					FirmaView.this.lblTelefonWert.setText(angestellter.getTelefon());

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
		this.lblStrasseWert.setText("");
		this.lblPlzWert.setText("");
		this.lblOrtWert.setText("");
		this.lblTelefonWert.setText("");

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
				//Angestellter in die Angestellten-Liste aufnehmen			 
				
				mitarbeiter.removeAllElements();
				mitarbeiter.addAll(model.getAngestellteListe());

				System.out.println("FIRMAVIEW ADD = " + ppe.getData().getNr());
				System.out.println("FIRMAVIEW ADD = " +ppe.getData().getVorname());
				System.out.println("FIRMAVIEW ADD = " +ppe.getData().getNachname());
				
			}
			else if (ppe.getType() == EventType.REMOVE) {
				//Angestellter aus der Angestellten-Liste entfernen
				
				mitarbeiter.removeAllElements();
				mitarbeiter.addAll(model.getAngestellteListe());
				
				System.out.println("FIRMAVIEW REMOVE = " + ppe.getData().getNr());
				System.out.println("FIRMAVIEW REMOVE = " +ppe.getData().getVorname());
				System.out.println("FIRMAVIEW REMOVE = " +ppe.getData().getNachname());

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
					FirmaView.this.createContent();
					FirmaView.this.setVisible(true);
				} catch (Exception e) {
					return; //Keine EDT-Fehler tracken
				}
			}
		});
	}


	public JButton  getBtnDateiLaden() {
		return btnDateiLaden;
	}


	public JButton getBtnNeuerAngestellter() {
		return btnNeuerAngestellter;
	}


	public JButton getBtnSpeichern() {
		return btnDateiSpeichern;
	}


	public JButton getBtnAngestellterBearbeiten() {
		return btnAngestellterBearbeiten;
	}

}