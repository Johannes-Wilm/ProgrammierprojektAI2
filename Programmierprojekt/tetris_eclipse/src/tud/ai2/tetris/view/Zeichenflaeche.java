package tud.ai2.tetris.view;

import tud.ai2.tetris.model.Block;
import tud.ai2.tetris.model.Stein;
import tud.ai2.tetris.control.Tetris;

import javax.swing.*;
import java.awt.*;

/**
 * Die Zeichenflaeche ist ein Fensterbereich (JPanel), in
 * dem sowohl die Matrix mit den darin enthaltenen Bloecken
 * als auch der aktuelle fallende Stein dargestellt wird.
 * 
 * @author Sebastian Kasten (Melkom)
 * @version 2013-06-03
 */
public class Zeichenflaeche extends JPanel {
    /** Spiel-Instanz */
    private final Tetris tetris;
    
    /**
     * Erstellt eine neue Zeichenflaeche fuer die uebergebene
     * Spiel-Instanz.
     * 
     * @param tetris Tetris-Instanz, die dargestellt werden soll
     */
    public Zeichenflaeche(Tetris tetris) {
        this.tetris = tetris;
    }
    
    /**
     * Zeichnet einen Stein mit gegebenen Blockgroessen auf eine
     * grafische Oberflaeche.
     * 
     * @param stein zu zeichnender Stein
     * @param g grafische Oberflaeche
     * @param blockbreite Breite eines Blocks in Pixeln
     * @param blockhoehe Hoehe eines Blocks in Pixeln
     */
    public static void zeichneStein(Stein stein, Graphics g, int blockbreite, int blockhoehe) {
        for(int i=0; i<stein.gibForm().gibHoehe(); i++)
            for(int j=0; j<stein.gibForm().gibBreite(); j++) {
                if(stein.gibForm().istBelegt(j, i)) {
                    // Zeichne nur, wenn die Form an dieser
                    // Stelle belegt ist
                    g.setColor(stein.gibForm().gibFarbe());
                    g.fillRect(j*blockbreite,
                         (stein.gibForm().gibHoehe()-i-1)*blockhoehe, blockbreite, blockhoehe);
                }
            }
    }
    
    /** {@inheritDoc} */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Breite eines Blocks, ergibt sich aus der Breite der Zeichenflaeche
        // und der Anzahl der Bloecke in einer Zeile        
        final int blockBreite = getWidth() / this.tetris.gibMatrix().gibBreite();
        // Hoehe eines Blocks, ergibt sich aus der Hoehe der Zeichenflaeche
        // und der Anzahl der Zeilen
        final int blockHoehe = getHeight() / this.tetris.gibMatrix().gibHoehe();
        
        // Zeichne das Raster
        g.setColor(new Color(200,200,200)); // hellgrau
        for(int i=0; i<=this.tetris.gibMatrix().gibHoehe(); i++) {
            g.drawLine(0, getHeight()-i*blockHoehe, this.tetris.gibMatrix().gibBreite()*blockBreite, getHeight()-i*blockHoehe);
        }
        for(int j=0; j<=this.tetris.gibMatrix().gibBreite(); j++) {
            g.drawLine(j*blockBreite, getHeight(), j*blockBreite, getHeight()-this.tetris.gibMatrix().gibHoehe()*blockHoehe);
        }
        
        // Zeichne die Matrix
        for(int i=0; i<this.tetris.gibMatrix().gibHoehe(); i++)
            for(int j=0; j<this.tetris.gibMatrix().gibBreite(); j++) {
                Block block = this.tetris.gibMatrix().gibBlock(j,i);
                if(block!=null) {
                    g.setColor(block.gibFarbe());
                    g.fillRect(j*blockBreite, getHeight()-(i+1)*blockHoehe, blockBreite, blockHoehe);
                    
                    g.setColor(Color.GRAY);
                    if(j==0 || this.tetris.gibMatrix().gibBlock(j-1,i)==null || this.tetris.gibMatrix().gibBlock(j-1,i).gibSteinIdx()!=block.gibSteinIdx())
                        g.drawLine(j*blockBreite, getHeight()-(i+1)*blockHoehe, j*blockBreite, getHeight()-i*blockHoehe);
                    if(j==this.tetris.gibMatrix().gibBreite()-1 || this.tetris.gibMatrix().gibBlock(j+1,i)==null || this.tetris.gibMatrix().gibBlock(j+1,i).gibSteinIdx()!=block.gibSteinIdx())
                        g.drawLine((j+1)*blockBreite, getHeight()-(i+1)*blockHoehe, (j+1)*blockBreite, getHeight()-i*blockHoehe);
                        
                    if(i==0 || this.tetris.gibMatrix().gibBlock(j,i-1)==null || this.tetris.gibMatrix().gibBlock(j,i-1).gibSteinIdx()!=block.gibSteinIdx())
                        g.drawLine(j*blockBreite, getHeight()-i*blockHoehe, (j+1)*blockBreite, getHeight()-i*blockHoehe);   
                    if(i==this.tetris.gibMatrix().gibHoehe() || this.tetris.gibMatrix().gibBlock(j,i+1)==null || this.tetris.gibMatrix().gibBlock(j,i+1).gibSteinIdx()!=block.gibSteinIdx())
                        g.drawLine(j*blockBreite, getHeight()-(i+1)*blockHoehe, (j+1)*blockBreite, getHeight()-(i+1)*blockHoehe);   
                }
            }
        
        // Zeichne des Stein
        Stein stein = this.tetris.gibAktuellerStein();
        if(stein!=null) {
            Graphics steinGrafik = g.create(stein.gibLinks()*blockBreite, 
                getHeight()-(stein.gibUnten()+stein.gibForm().gibHoehe())*blockHoehe, stein.gibForm().gibBreite()*blockBreite, stein.gibForm().gibHoehe()*blockHoehe);
            zeichneStein(stein, steinGrafik, blockBreite, blockHoehe);
        }
    }
}
