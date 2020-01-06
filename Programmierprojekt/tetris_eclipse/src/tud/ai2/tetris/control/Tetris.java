package tud.ai2.tetris.control;

import tud.ai2.tetris.model.Form;
import tud.ai2.tetris.model.Matrix;
import tud.ai2.tetris.model.Stein;
import tud.ai2.tetris.util.Const;
import tud.ai2.tetris.util.FormFactory;
import tud.ai2.tetris.util.IterableFormFactory;
import tud.ai2.tetris.util.ZufallsFormFactory;
import tud.ai2.tetris.view.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Die Klasse Tetris enthaelt alle fuer den Spielablauf
 * und die Steuerung benoetigten Methoden. Bei jedem
 * Taktzyklus wird ein SpielZyklus ausgefuehrt.
 *
 * Ein SpielZyklus entfernt alle schwarze Zeilen oder laesst den
 * gerade aktiven Stein einen Block herunterfallen. Erreicht dieser
 * das obere Ende der Ablagematrix, so wird er der Matrix hinzugefuegt.
 *
 * Tetris behandelt ausserdem Actionevents mit dem Commando-String
 * "links", "rechts", "drehen", "ablegen", "schneller" und "start",
 * um das aktive Spiel zu manipulieren.
 *
 * @author Sebastian Kasten (Melkom)
 * @author Christian Heinig (Owyn)
 * @author Robert Cieslinski
 * @version 2019-09-20
 */
public class Tetris implements ActionListener {
    /** Die GUI von Tetris */
    private final MainFrame mainFrame;
    /** aktueller Stein (fallend) */
    private Stein aktuell;
    /** Naechster Stein (fuer Vorschau) */
    private Stein naechster;
    /** Matrix dieses Spiels */
    private final Matrix matrix;
    /** Taktzyklus eines Einzelschrittes in Millisekunden */
    private int taktzyklus = Const.TAKTZYKLUS_START;
    /** Scheduler um Einzelschritte einzuplanen */
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    /** Zukunft des schedulers */
    private ScheduledFuture<?> future;
    /** SpielZyklus-Instanz fuer regelmaessige Aktionen */
    private final SpielZyklus zyklus;
    /** FormFactory, die die naechsten Formen steuert */
    private final FormFactory formfactory;


    /**
     * Gibt den aktuellen Stein zurueck, der gerade
     * herunterfaellt und sich der Matrix entgegenbewegt.
     *
     * @return aktueller Stein
     */
    public Stein gibAktuellerStein() {
        return this.aktuell;
    }

    /**
     * Gibt den Stein zurueck, der zum neuen aktuellen Stein
     * wird, nachdem der aktuelle Stein auf die Matrix faellt.
     *
     * @return naechster Stein
     */
    public Stein gibNaechsterStein() {
        return this.naechster;
    }

    /**
     * Gibt die Matrix dieses Spiels zurueck.
     *
     * @return die Matrix
     */
    public Matrix gibMatrix() {
        return this.matrix;
    }

    /**
     * Gibt das MainFrame dieses Spiels zurück.
     *
     * @return das MainFrame
     */
    MainFrame gibMainFrame() {
        return mainFrame;
    }

    /**
     * Steuert die Geschwindigkeit des Spiels anhand des aktuellen Levels.
     */
    private void levelUp(){
        int neuTakt;
        int level = matrix.gibVolleZeilen()/Const.LEVEL_SCHRITT;

        // Fuer                        0 <= level < 10      , 10 <= level
        neuTakt = Math.max(Const.TAKTZYKLUS_START - 70*level, 110-3*level);
        neuTakt = Math.max(neuTakt, 20);

        taktzyklus = neuTakt;
    }

    /**
     * Plant einen neuen Schritt des SpielZykluses und berechnet
     * eine passende Verzoegerung anhand der Eingabedaten
     *
     * @param oldZeilen Anzahl der geloesten Zeilen zu Beginn des letzten Schritts
     */
    void naechsterSchritt(int oldZeilen){
        levelUp();
        long takt = taktzyklus;

        // leichte Verzögerung, wenn Zeile(n) gerade schwarz wurde(n)
        if (oldZeilen!=matrix.gibVolleZeilen())
            takt = Const.TAKTZYKLUS_CLEAR;

        // Verzögerung beim Aufsetzen
        else if (aktuell!=null && matrix.aufsetzpunkt(aktuell)>=aktuell.gibUnten() && !future.isDone())
            takt = Const.TAKTZYKLUS_SLIDE;

        schedule(takt);
    }

    /**
     * Plant einen neuen Schritt des SpielZykluses mit Verzoegerung t
     *
     * @param t gewuenschte Verzoegerung
     */
    private void schedule(long t){
        if (future!=null)
            future.cancel(true);
        future = scheduler.schedule(zyklus, t, TimeUnit.MILLISECONDS);
    }

    /**
     * Legt den aktuellen Stein auf die Matrix ab
     * und wechselt zum naechsten Stein.
     *
     * @return true, falls auf der Matrix noch Platz
     * ist; false, falls nicht.
     */
    boolean ablegen() {
        boolean moeglich = this.matrix.ablegen(this.aktuell);

        this.aktuell = this.naechster;
        this.naechster = new Stein(this.matrix, this.formfactory.gibForm());

        return moeglich;
    }

    /**
     * Legt den aktuellen Stein als Reaktion auf eine User-Aktion ab
     */
    private void ablegenButton(){
        int oldZ = matrix.gibVolleZeilen();
        ablegen();
        naechsterSchritt(oldZ);
    }

    /**
     * Beschleunigt das Spiel als Reaktion auf eine User-Aktion
     */
    private void schnellerButton(){
        if (future.getDelay(TimeUnit.MILLISECONDS)>Const.TAKTZYKLUS_FAST) {
            schedule(Const.TAKTZYKLUS_FAST);
        }
    }

    /**
     * Behandelt die ActionEvents mit den Command-Strings
     * "links", "rechts", "drehen", "schneller", "ablegen" und "start".
     */
    public void actionPerformed(ActionEvent event) {
        actionPerformed(event.getActionCommand());
    }

    /**
     * Behandelt die ActionEvents mit den Command-Strings
     * "links", "rechts", "drehen", "ablegen" und "start".
     */
    public synchronized void actionPerformed(String action) {
        if (action.equals("start"))
            starteGravitation();
        // Erlaube nur Inputs, wenn das Spiel aktuell lauft
        if (future==null || future.isDone() )
            return;
        switch (action) {
            case ("links"):
                this.aktuell.verschiebeNachLinks();
                break;
            case("rechts"):
                this.aktuell.verschiebeNachRechts();
                break;
            case("drehen"):
                this.aktuell.dreheNachLinks();
                break;
            case("ablegen"):
                this.ablegenButton();
                break;
            case("schneller"):
                schnellerButton();
                break;
            default:
        }

        this.mainFrame.repaint();
    }

    /**
     * Startet diese Tetris-Instanz (neu).
     */
    private void starteGravitation() {
        // Setzte Iterierung zurueck
        if (formfactory instanceof IterableFormFactory)
            ((IterableFormFactory) formfactory).reset();
        // Setzte Matrix zurueck
        matrix.reset();
        // Setzte Werte zurueck
        taktzyklus = Const.TAKTZYKLUS_START;
        aktuell = new Stein(this.matrix, this.formfactory.gibForm());
        naechster = new Stein(this.matrix, this.formfactory.gibForm());
        // Plane sofort einen neuen SpielZyklus
        schedule(0);
    }

    /**
     * Erstellt eine neue Tetris-Instanz mit Standardbreite und -hoehe.
     */
    public Tetris() {
        this(Const.BREITE, Const.HOEHE);
    }

    /**
     * Erstellt eine neue Tetris-Instanz mit gegebener
     * Breite und Hoehe.
     *
     * @param breite Breite der Spielmatrix
     * @param hoehe Hoehe der Spielmatrix
     */
    public Tetris(int breite, int hoehe) {
        this(breite, hoehe, ZufallsFormFactory.INSTANZ);
    }

    /**
     * Erstellt eine neue Tetris-Instanz mit gegebener
     * Breite, Hoehe und FormFactory.
     *
     * @param breite Breite der Spielmatrix
     * @param hoehe Hoehe der Spielmatrix
     * @param formfactory Fabrik zur Erzeugung der naechsten Form
     */
    public Tetris(int breite, int hoehe, FormFactory formfactory) {
        breite = Math.min(breite, Const.BREITE);
        hoehe = Math.min(hoehe, Const.HOEHE);

        this.matrix = new Matrix(breite, hoehe);
        this.mainFrame = new MainFrame(this);
        this.formfactory = formfactory;
        mainFrame.setVisible(true);

        zyklus = new SpielZyklus(this);
    }

    /**
     * Test-Konstruktor:
     * Erstellt eine neue Tetris-Instanz mit gegebener Breite, Hoehe und
     * IterableFormFactory über ein bestimmtes Iterable.
     * Das MainFrame bleibt unsichtbar.
     *
     * @param breite Breite der Spielmatrix
     * @param hoehe Hoehe der Spielmatrix
     * @param iterable Das für IterableFormFactory benötigte Iterable
     */
    public Tetris(int breite, int hoehe, Iterable<Form> iterable) {
        breite = Math.min(breite, Const.BREITE);
        hoehe = Math.min(hoehe, Const.HOEHE);

        this.matrix = new Matrix(breite, hoehe);
        this.mainFrame = new MainFrame(this);
        this.formfactory = new IterableFormFactory(iterable);

        zyklus = new SpielZyklus(this);
    }

}
