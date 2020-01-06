package tud.ai2.tetris.control;

import javax.swing.*;

/** Die Klasse SpielZyklus kapselt den Einzelschritt von Tetris in
 * einem Runnable, der von einem Scheduler ausgefuehrt werden kann.
 *
 * @author Sebastian Kasten (Melkom)
 * @author Christian Heinig (Owyn)
 * @author Robert Cieslinski
 * @version 2019-09-20
 */
public class SpielZyklus implements Runnable {
    /** Das zu steuernde Tetris-Spiel */
    private Tetris tetris;

    /**
     * Konstruktor der Klasse
     *
     * @param t das zu steuernde Tetris-Spiel
     */
    public SpielZyklus(Tetris t){
        tetris = t;
    }

    /**
     * In einem Schritt fällt der aktuelle Stein einen Block.
     * Beim Auftreffen wird auf ein Game Over geprüft.
     *
     * @return true <=> Schritt erfolgreich ausgeführt;
     *         false sonst
     */
    private boolean schritt() {
        // Versuche den Stein einen Block fallen zu lassen
        if(!tetris.gibAktuellerStein().fallen()) {
            // Beim Auftreffen ueberpruefen, ob die Matrix bereits belegt ist
            boolean moeglich = tetris.ablegen();
            if(!moeglich) {
                // Wenn ja -> Game over!
                JOptionPane.showMessageDialog(tetris.gibMainFrame(),"Game over!");
                return false;
            }
        }
        // Spiel nicht vorbei, es kann weitergehen
        return true;
    }

    /** {@inheritDoc }
     * In einer Iteration wird zuerst versucht, schwarze Zeilen zu entfernen.
     * Sollte es keine davon geben, so wird ein regulärer Schritt ausgeführt.
     */
    public void run() {
        int oldZeilen = tetris.gibMatrix().gibVolleZeilen();

        // Laufe nur weiter, wenn im aktuellen Schritt etwas getan wurde
        if(tetris.gibMatrix().aufraeumen() || schritt()) {
            tetris.gibMainFrame().repaint();
            tetris.naechsterSchritt(oldZeilen);
        }
    }
}
