package tud.ai2.tetris.model;

/**
 * Ein Stein ist die fallende, nicht in der
 * Matrix manifestierte Form eines Tetrominos.
 * Sie definiert sich durch eine Position im Raum
 * im Bezug auf die Matrix, sowie ihre Form, die
 * bestimmt, welche Bloecke der Matrix eingenommen
 * werden, wenn sich der Stein auf der Matrix manifestiert.
 * 
 * Ein Stein kann sich im Raum nach rechts, links und unten
 * bewegen und kann ausserdem nach links gedreht werden.
 * Die Matrix und ihre Bloecke begrenzen den Bewegungsraum
 * des Steins.
 * 
 * @author Sebastian Kasten (Melkom)
 * @author Robert Cieslinski
 * @author <Ihr/e Name/n>
 * @version <Ihr Datum>
 */
public class Stein {
    /** Matrix auf der dieser Stein schwebt */
    private final Matrix matrix;
    /** Form dieses Steins */
    private Form form;
    /** Unterer Rand (besetzt) */
    private int unten;
    /** Linker Rand (besetzt) */
    private int links;
    /** Eindeutiger Index des Steins */
    private final int steinIndex;
    /** Statischer Steinzähler */
    private static int count = 0;
    
    /**
     * Erzeugt einen neuen Stein auf der Matrix an
     * der gegebenen Position mit der gegebenen Form.
     * 
     * @param matrix Matrix auf der dieser Stein schwebt
     * @param form Form dieses Steins
     * @param unten unterer Rand dieses Steins
     * @param links linker Rand dieses Steins
     */
    public Stein(Matrix matrix, Form form, int unten, int links) {
        this.steinIndex = count++;
        this.matrix = matrix;
        this.form = form;
        this.unten = unten;
        this.links = links;
    } 
    
    /**
     * Erzeugt einen neuen Stein in der Mitte am
     * oberen Rand der Matrix.
     * 
     * @param matrix Matrix auf der dieser Stein schwebt
     */
    public Stein(Matrix matrix, Form form) {
        this(matrix, form, matrix.gibHoehe(),
            matrix.gibBreite() / 2 - form.gibBreite() / 2);
    }

    /**
     * Gibt den SteinIndex des Steins zurück.
     *
     * @return Index des Steins
     */
    public int gibSteinIndex() {
        return steinIndex;
    }

    /**
     * Gibt den unteren Rand dieses Steins im Bezug auf die Matrix
     * zurueck, also den Index der untersten/kleinsten Zeile
     * der Matrix, die vom Stein belegt wuerde.
     * 
     * @return unterer Rand des Steins
     */
    public int gibUnten() {
        return this.unten;
    }
    
    /**
     * Gibt den linken Rand dieses Steins im Bezug auf die
     * Matrix zurueck, also den Index der kleinsten Spalte
     * der Matrix, die vom Stein belegt wuerde.
     * 
     * @return linker Rand des Steins
     */
    public int gibLinks() {
        return this.links;
    }
    
    /**
     * Gibt die Form dieses Steins zurueck.
     * 
     * @return die Form des Steins
     */
    public Form gibForm() {
        return this.form;
    }

    /**
     * Berechnet, ob ein Stein mit uebergebener Form an uebergebener
     * Position (:= Ecke unten links) mit "festen" Elementen aus der
     * Matrix kollidieren wuerde.
     *
     * @param x X-Koordinate der gewuenschten Position
     * @param y Y-Koordinate der gewuenschten Position
     * @param form zu betrachtende Form
     * @return true <=> keine Kollision;
     *         false sonst
     */
    private boolean keineKollision(int x, int y, Form form) {
        for (int xi = 0; xi < form.gibBreite(); xi++) {
            for (int yi = 0; yi < form.gibHoehe(); yi++) {
                if (form.istBelegt(xi, yi)) {
                    if (matrix.gibBlock(x + xi, y + yi) != null) return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Verschiebt den Stein, wenn moeglich, um
     * einen Block nach links.
     */
    public void verschiebeNachLinks() {
        //if(this.links>0 && this.matrix.gibBlock(this.links-1, this.unten)==null)
        if(this.links>0 && keineKollision(this.links - 1, this.unten, this.form))
            this.links--;
    }
    
    /**
     * Verschiebt den Stein, wenn moeglich, um
     * einen Block nach rechts.
     */
    public void verschiebeNachRechts() {
        //if(this.links + this.form.gibBreite() < this.matrix.gibBreite() && this.matrix.gibBlock(this.links+1, this.unten)==null)
        if(this.links + this.form.gibBreite() < this.matrix.gibBreite() && keineKollision(this.links + 1, this.unten, this.form))
            this.links++;
    }
    
    /**
     * Dreht den Stein um 90 Grad nach links und verschiebt
     * ihn an den bisherigen linken und unteren Rand.
     */
    public void dreheNachLinks() {
        if(this.links + this.form.gibHoehe() <= this.matrix.gibBreite() && keineKollision(this.links, this.unten, this.form.dreheNachLinks()))
            this.form = this.form.dreheNachLinks();
    }
    
    // TODO Aufgabe 3c)
    public boolean fallen() {
        // IHRE IMPLEMENTIERUNG
        return false;
    }
    
}
