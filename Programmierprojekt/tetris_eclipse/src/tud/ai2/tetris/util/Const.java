package tud.ai2.tetris.util;

import tud.ai2.tetris.model.Form;

import java.awt.*;

/**
 * Die Klasse Const enthaelt alle fuer das Tetris-Spiel relevanten Konstanten.
 *
 * @author Sebastian Kasten (Melkom)
 * @author Christian Heinig (Owyn)
 * @author Robert Cieslinski
 * @version 2019-09-20
 */
public abstract class Const {
    // Wert-Konstanten
    /** Der anfängliche Taktzyklus eines Einzelschrittes in Millisekunden */
    public final static int TAKTZYKLUS_START = 800;

    /** Der Taktzyklus beim schnellen Fallen in Millisekunden */
    public final static int TAKTZYKLUS_FAST = TAKTZYKLUS_START /6;

    /** Der Taktzyklus nach dem Vervollstaendigen einer oder mehrerer Zeilen */
    public final static int TAKTZYKLUS_CLEAR = 555;

    /** Der Taktzyklus zwischen Auftreffen und Manifestieren */
    public final static int TAKTZYKLUS_SLIDE = 500;

    /** Die Anzahl der zu loesenden Zeilen für ein Level Up */
    public final static int LEVEL_SCHRITT = 10;

    /** Maximale Breite der Matrix eines Tetris-Spiels */
    public final static int BREITE = 10;

    /** Maximale Hoehe der Matrix eines Tetris-Spiels */
    public final static int HOEHE = 20;


    // Mögliche Tetrominos
    /**
     * Form eines I-Tetrominos: vier Bloecke
     * nebeneinander; Farbe: Cyan.
     */
    public final static Form I = new Form(
            new boolean[][] {
                    new boolean[] { true, true, true, true } },
            Color.CYAN);

    /**
     * Form eines J-Tetrominos: drei Bloecke
     * nebeneinander in der oberen Zeile, darunter
     * ein einzelner Block am rechten Rand; Farbe:
     * Blau.
     */
    public final static Form J = new Form(
            new boolean[][] {
                    new boolean[] { false, false, true },
                    new boolean[] { true, true, true }},
            Color.BLUE);

    /**
     * Form eines L-Tetrominos: drei Bloecke
     * nebeneinander in der oberen Zeile, darunter
     * ein einzelner Block am linken Rand; Farbe:
     * Orange.
     */
    public final static Form L = new Form(
            new boolean[][] {
                    new boolean[] { true, false, false },
                    new boolean[] { true, true, true }},
            Color.ORANGE);

    /**
     * Form eines O-Tetrominos: vier im Quadrat
     * angeordnete Bloecke; Farbe: Gelb.
     */
    public final static Form O = new Form(
            new boolean[][] {
                    new boolean[] { true, true },
                    new boolean[] { true, true }},
            Color.YELLOW);

    /**
     * Form eines S-Tetrominos: zwei Bloecke
     * nebeneinander in der oberen Zeile, darunter,
     * um eins nach links verschoben, zwei weitere
     * Bloecke nebeneinander; Farbe: Gruen.
     */
    public final static Form S = new Form(
            new boolean[][] {
                    new boolean[] { true, true, false },
                    new boolean[] { false, true, true }},
            Color.GREEN);

    /**
     * Form eines T-Tetrominos: drei Bloecke
     * nebeneinander in der oberen Zeile, darunter
     * ein einzelner Block in der Mitte; Farbe:
     * Magenta.
     */
    public final static Form T = new Form(
            new boolean[][] {
                    new boolean[] { false, true, false },
                    new boolean[] { true, true, true },},
            Color.MAGENTA);

    /**
     * Form eines Z-Tetrominos: zwei Bloecke
     * nebeneinander in der oberen Zeile, darunter,
     * um eins nach rechts verschoben, zwei weitere
     * Bloecke nebeneinander; Farbe: Rot.
     */
    public final static Form Z = new Form(
            new boolean[][] {
                    new boolean[] { false, true, true },
                    new boolean[] { true, true, false }},
            Color.RED);

}
