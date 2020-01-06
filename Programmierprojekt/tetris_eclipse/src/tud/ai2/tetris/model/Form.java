package tud.ai2.tetris.model;

import java.awt.Color;
import java.util.Arrays;

/**
 * Die Form bestimmt zum einen die Farbe, zum anderen,
 * welche Bloecke im Falle der Manifestierung
 * eingenommen werden sollen.
 * 
 * Sie ist, einmal erstellt, unveraenderlich und
 * besitzt keine Position im Raum. Sie kann auch als
 * Bauplan fuer Steine gesehen werden.
 * 
 * @author Sebastian Kasten (Melkom)
 * @author Robert Cieslinski
 * @author <Ihr/e Name/n>
 * @version <Ihr Datum>
 */
public class Form {
    /** 2D-Array mit Eintraegen fuer belegt=true und frei=false */
    private final boolean[][] raster;
    /** Farbe der Form */
    private final Color farbe;
    /** Breite der Form */
    private final int breite;
    /** Hoehe der Form */
    private final int hoehe;
    
    /**
     * Erstellt eine neue Form mit dem gegebenen Belegungsraster
     * und Farbe. Das Raster muss sowohl in der ersten als auch
     * der letzten Zeile/Spalte mindestens einen true-Eintrag besitzen.
     * 
     * @param raster Belegungsraster in der Groesse der
     * Form, bei der ein Eintrag bestimmt, ob der entsprechende
     * Block belegt (<code>true</code>) oder frei (<code>false</code>)
     * manifestiert werden soll.
     * @param farbe Farbe dieser Form
     */
    public Form(boolean[][] raster, Color farbe) {
        this.raster = raster;
        this.farbe = farbe;

        this.hoehe = raster.length;
        int breite = 0;
        for (boolean[] booleans : raster)
            breite = Math.max(breite, booleans.length);
        this.breite = breite;

        for (int i=0; i<hoehe; i++){
            raster[i] = Arrays.copyOf(raster[i], breite);
        }
    }
    
    /**
     * Gibt die Farbe dieser Form zurueck.
     * 
     * @return Farbe der Form
     */
    public Color gibFarbe() {
        return this.farbe;
    }
    
    /**
     * Gibt die Breite des umgebenden Rechtecks dieser 
     * Form zurueck.
     * 
     * @return Breite der Form
     */
    public int gibBreite() {
        return this.breite;
    }
    
    /**
     * Gibt die Hoehe des umgebenden Rechtecks
     * dieser Form zurueck.
     * 
     * @return Hoehe der Form
     */
    public int gibHoehe() {
        return this.hoehe;
    }
    
    // TODO Aufgabe 2a)
    public boolean istBelegt(int spalte, int zeile) {
        // IHRE IMPLEMENTIERUNG
        return spalte<=zeile;
    }

    // TODO Aufgabe 2b)
    public Form dreheNachLinks() {
        // IHRE IMPLEMENTIERUNG
        return this;
    }
}
