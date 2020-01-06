package tud.ai2.tetris.model;

import tud.ai2.tetris.util.Const;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Die Matrix ist ein Raster, das aus Bloecken und
 * Leerstellen besteht. Eine neu erstellte Matrix ist immer
 * komplett leer und kann durch Ablegen von Steinen gefuellt
 * werden.
 *
 * @author Sebastian Kasten (Melkom)
 * @author Robert Cieslinski
 * @author <Ihr/e Name/n>
 * @version <Ihr Datum>
 */
public class Matrix {
    /** Datenstruktur, die die Bloecke enthaelt. Die Liste
     * enthaelt nur so viele Eintraege wie momentan noetig.*/
    private final List<Block[]> belegung = new ArrayList<>();
    /** Anzahl der Bloecke pro Zeile */
    private final int breite;
    /** Maximale Anzahl der Zeilen */
    private final int hoehe;
    /** Anzahl der bisher geloeschten vollen Zeilen */
    private int volleZeilen = 0;

    /**
     * Erstellt eine neue Matrix mit gegebenen Breite
     * und Hoehe.
     *
     * @param breite Breite der Matrix
     * @param hoehe Hoehe der Matrix
     */
    public Matrix(int breite, int hoehe) {
        this.breite = breite;
        this.hoehe = hoehe;
    }

    /**
     * Gibt die Breite, also die Anzahl der Bloecke
     * pro Zeile zurueck.
     *
     * @return Breite der Matrix
     */
    public int gibBreite() {
        return this.breite;
    }

    /**
     * Gibt die Hoehe, also die Anzahl der Zeilen
     * zurueck.
     *
     * @return Hoehe der Matrix
     */
    public int gibHoehe() {
        return this.hoehe;
    }

    /**
     * Gibt die Anzahl der bisher geloeschten vollen Zeilen zurueck.
     *
     * @return Anzahl der bisher geloeschten vollen Zeilen
     */
    public int gibVolleZeilen() {
        return volleZeilen;
    }

    /**
     * Erweitert die Matrix nach oben mit einer neuen Zeile,
     * sollte noch Platz sein.
     * */
    private void neueZeile() {
        if(this.belegung.size()<this.hoehe) {
            Block[] bloecke = new Block[this.breite];
            this.belegung.add(bloecke);
        }
    }

    /**
     * Versetzt die Matrix in ihren Ausgangszustand.
     */
    public void reset(){
        belegung.clear();
        volleZeilen = 0;
    }

    /**
     *  Setzt den Block in Spalte x und Zeile y auf den uebergebenen
     *  Wert block. Falls block null ist, wird eine Leerstelle erzeugt.
     *
     * @param x gewünschte X-Koordinate
     * @param y gewünschte Y-Koordinate
     * @param block der zu setzende Block
     */
    private void setzeBlock(int x, int y, Block block) {
        if(x<0 || x >= this.breite || y<0 || y >= this.hoehe)
            return;

        // ggf. Matrix nach oben "wachsen" lassen
        while(y>=this.belegung.size())
            neueZeile();

        this.belegung.get(y)[x] = block;
    }

    /**
     *  Loescht den Block an gegebener Stelle.
     *
     * @param x X-Koordinate des zu löschenden Blocks
     * @param y Y-Koordinate des zu löschenden Blocks
     */
    private void loescheBlock(int x, int y) {
        setzeBlock(x,y,null);
    }

    // TODO Aufgabe 5a)
    private void floodfill(int x, int y, int steinIdx, List<Integer> xs, List<Integer> ys) {
        // IHRE IMPLEMENTIERUNG
    }

    // TODO Aufgabe 5b)
    private int[] boundingBox(List<Integer> xs, List<Integer> ys) {
        // IHRE IMPLEMENTIERUNG
        return null;
    }

    // TODO Aufgabe 5c)
    private Stein erstelleStein(List<Integer> xs, List<Integer> ys, Color farbe) {
        // IHRE IMPLEMENTIERUNG
        return new Stein(this, Const.I);
    }

    /** Ermittelt den Stein, der mit dem Block in Spalte x und
     * Zeile y zusammenhaengt und loescht ihn aus der Matrix.
     * Gibt ihn dann als Stein-Objekt zurueck. Gibt null zurueck,
     * wenn sich an der Stelle eine Leerstelle befindet.
     *
     * @param x X-Koordinate des Steins
     * @param y Y-Koordinate des Steins
     */
    private Stein nimmStein(int x, int y) {
        // Leerstelle, dann gibt es auch keinen Stein
        if(gibBlock(x,y)==null) return null;

        // Farbe uebernehmen, Liste der Positionen initialisieren
        final Color farbe = gibBlock(x,y).gibFarbe();
        final List<Integer> xs = new ArrayList<>();
        final List<Integer> ys = new ArrayList<>();

        // Floodfill, um zusammenhaengende Bloecke zu finden
        floodfill(x,y,gibBlock(x,y).gibSteinIdx(),xs,ys);
        return erstelleStein(xs, ys, farbe);
    }


    // TODO Aufgabe 4a)
    private void faerbeVolleZeilen() {
        // IHRE IMPLEMENTIERUNG
    }

    // TODO Aufgabe 4b)
    private boolean loescheSchwarzeZeilen(){
        // IHRE IMPLEMENTIERUNG
        return false;
    }

    /**
     * Erkennt schwarze Zeilen, loescht diese und initiiert eventuelle
     * Steinbewegungen (erweiterte Physik).
     *
     * @return true <=> mindestens eine Zeile entfernt;
     *         false sonst
     */
    public boolean aufraeumen() {
        boolean aenderung = loescheSchwarzeZeilen();

        // Wenn eine Zeile entfernt wurde...
        if(aenderung) {
            // ...extrahiere betroffene Steine und
            // lasse diese neu auf die Matrix fallen
            for (int i = 0; i < this.belegung.size(); i++)
                for (int j = 0; j < gibBreite(); j++) {
                    Stein stein = nimmStein(j, i);
                    if (stein != null)
                        manifestieren(stein);
                }
            // ggf. erneut Zeilen schwarz färben
            faerbeVolleZeilen();
        }
        return aenderung;
    }

    /**
     * Gibt den Block in Spalte x und Zeile y Zeile zurueck.
     *
     * @param x Spalte, also Position innerhalb der Zeile
     * @param y Index der Zeile
     * @return Block an der Stelle oder <code>null</code>
     * wenn diese Stelle leer ist
     */
    public Block gibBlock(int x, int y) {
        if(y<this.belegung.size() && y>=0 && x>=0 && x<gibBreite())
            return this.belegung.get(y)[x];
        return null;
    }

    // TODO Aufgabe 3a)
    private int gibHoehe(int x, int y) {
        return -42;
    }


    // TODO Aufgabe 3b)
    public int aufsetzpunkt(Stein stein) {
        // IHRE IMPLEMENTIERUNG
        return -42;
    }

    // TODO Aufgabe 3d)
    private boolean manifestieren(Stein stein) {
        // IHRE IMPLEMENTIERUNG
        return stein.gibUnten()<=stein.gibLinks();
    }

    /**
     * Legt den angegebenen Stein auf die Matrix ab und
     * manifestiert diesen somit zu Bloecken auf der
     * Matrix.
     *
     * @param stein Stein, der auf die Matrix faellt
     * @return true <=> Stein erfolgreich abgelegt;
     *         false sonst
     */
    public boolean ablegen(Stein stein) {
        if(!manifestieren(stein)) return false;
        faerbeVolleZeilen();
        return true;
    }

}
