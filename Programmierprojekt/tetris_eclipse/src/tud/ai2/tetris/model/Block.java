package tud.ai2.tetris.model;

import java.awt.Color;

/**
 * Bloecke sind die Bestandteile der Matrix und
 * kennzeichnen eine Belegung an der Stelle an der 
 * sie existieren. Bloecke haben einen Stein-Index
 * und eine Farbe.
 * 
 * @author Sebastian Kasten (Melkom)
 * @author Robert Cieslinski
 * @version 2019-09-20
 */
public class Block {
    /** Stein-Index dieses Blocks */
    private final int steinIdx;
    /** Farbe dieses Blocks */
    private Color farbe;
    
    /**
     * Gibt den Stein-Index dieses Blocks zurueck.
     * Bloecke mit dem selben Stein-Index gelten
     * als zusammenhaengend und koennen einen
     * Stein bilden, wenn sie aus der Matrix herausgetrennt
     * werden.
     * 
     * @return Stein-Index dieses Blocks
     */
    public int gibSteinIdx() {
        return this.steinIdx;
    }
    
    /**
     * Gibt die Farbe dieses Blocks zurueck.
     * 
     * @return Farbe dieses Blocks
     */
    public Color gibFarbe() {
        return this.farbe;
    }

    /**
     * Aendert die Farbe des Blocks.
     *
     * @param c Die neue Farbe
     */
    public void setFarbe(Color c){
        this.farbe=c;
    }
    
    /**
     * Erstellt einen neuen Block mit gegebenem Stein-Index
     * und Farbe.
     * 
     * @param steinIdx Stein-Index des Blocks. Bloecke, die den
     * selben Stein manifestieren, haben den selben Stein-Index.
     * @param farbe Farbe des Blocks
     */
    public Block(int steinIdx, Color farbe) {
        this.steinIdx = steinIdx;
        this.farbe = farbe;
    }


    /** {@inheritDoc }
     * Zwei Bloecke sind gleich, wenn sie in Farbe und SteinIndex uebereinstimmen.
     */
    public boolean equals(Object o){
        if (!(o instanceof Block))
            return false;
        Block b = (Block)o;
        return b.gibFarbe().equals(farbe) && b.gibSteinIdx()==steinIdx;
    }
}
