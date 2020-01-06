package tud.ai2.tetris.view;


import tud.ai2.tetris.util.Const;
import tud.ai2.tetris.control.Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Das Hauptfenster der Tetris-Anwendung.
 * 
 * @author Sebastian Kasten (Melkom)
 * @author Christian Heinig (Owyn)
 * @author Robert Cieslinski
 * @author <Ihr/e Name/n>
 * @version <Ihr Datum>
 */
public class MainFrame extends JFrame {
    /** Zeichenflaeche: Hier wird die Matrix und der fallende Stein gezeichnet */
    private final Zeichenflaeche zeichenFlaeche;
    /** Zugehoeriges Tetris-Spiel */
    private final Tetris tetris;
    
    /** Stellt die Anzahl der vollstaendigen Zeilen und den aktuellen Level dar */
    private final JLabel status;
    
    /** Button, um den fallenden Stein nach links zu verschieben */
    private final JButton nachLinks;
    /** Button, um den fallenden Stein nach rechts zu verschieben */
    private final JButton nachRechts;
    /** Button, um den fallenden Stein nach links zu drehen */
    private final JButton drehen;
    /** Button, um den fallenden direkt abzulegen */
    private final JButton ablegen;
    /** Button, um den fallenden Stein schneller abzulegen */
    private final JButton schneller;
    /** Button, um das Spiel (neu) zu starten */
    private final JButton start;
    
    /** Ordnet die Bestandteile der GUI im Fenster an */
    private void layouten() {

        setSize(550,600);

        setTitle("Tetris");
        Container pane = getContentPane();

        pane.setLayout(new BorderLayout());
        pane.add(this.zeichenFlaeche, BorderLayout.CENTER);
        JPanel guiRechts = new JPanel();
        guiRechts.setLayout(new BorderLayout());
        guiRechts.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        
        JPanel guiRechtsOben = new JPanel();
        guiRechtsOben.setLayout(new BoxLayout(guiRechtsOben, BoxLayout.PAGE_AXIS));
        guiRechtsOben.add(status);
        guiRechtsOben.add(Box.createRigidArea(new Dimension(0,10)));
        guiRechtsOben.add(new JLabel("Naechster Stein:"));
        guiRechtsOben.add(new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if(MainFrame.this.tetris.gibNaechsterStein()!=null)
                    Zeichenflaeche.zeichneStein(MainFrame.this.tetris.gibNaechsterStein(), g, getWidth()/6, getWidth()/6);
                g.setColor(Color.GRAY);
                g.drawRect(0, 0, (getWidth()-1)*4/6, (getWidth()-1)*3/6);
            }
        });
        guiRechts.add(guiRechtsOben, BorderLayout.CENTER);
        
        
        //TODO Aufgabe 1
        //BEGINN IHRER IMPLEMENTIERUNG

        //ENDE IHRER IMPLEMENTIERUNG

        pane.add(guiRechts, BorderLayout.EAST);
    }

    /**
     * Aktualisiert die Anzahl der vollstaendigen Zeilen und zeichnet neu.
     */
    public void repaint() {
        int z = this.tetris.gibMatrix().gibVolleZeilen();
        status.setText("Zeilen: " + z + "   Level: " + z/ Const.LEVEL_SCHRITT);
        super.repaint();
    }
    
    /**
     * Erstellt ein neues Hauptfenster fuer das angegebene
     * Tetris-Spiel.
     */
    public MainFrame(final Tetris tetris) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.zeichenFlaeche = new Zeichenflaeche(tetris);
        this.status = new JLabel("Zeilen: 0   Level: 0");
        this.tetris = tetris;

        this.nachLinks = new JButton("\u2190 Nach Links");
        this.nachLinks.setActionCommand("links");
        this.nachLinks.addActionListener(tetris);

        this.nachRechts = new JButton("Nach Rechts \u2192");
        this.nachRechts.setActionCommand("rechts");
        this.nachRechts.addActionListener(tetris);

        this.ablegen = new JButton("Ablegen \u2191");
        this.ablegen.setActionCommand("ablegen");
        this.ablegen.addActionListener(tetris);

        this.schneller = new JButton("Schneller \u2193");
        this.schneller.setActionCommand("schneller");
        this.schneller.addActionListener(tetris);

        this.drehen = new JButton("Drehen");
        this.drehen.setActionCommand("drehen");
        this.drehen.addActionListener(tetris);

        this.start = new JButton("START");
        this.start.setActionCommand("start");
        this.start.addActionListener(tetris);
        
        layouten();

        // Tastatureingaben bearbeiten
        JComponent comp = (JComponent)getContentPane();
        InputMap im = comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW); 
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "links");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "rechts");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "drehen");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "ablegen");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "schneller");

        ActionMap am = comp.getActionMap();
        am.put("links", new ButtonAction("links"));
        am.put("rechts", new ButtonAction("rechts"));
        am.put("drehen", new ButtonAction("drehen"));
        am.put("ablegen", new ButtonAction("ablegen"));
        am.put("schneller", new ButtonAction("schneller"));
    }
    
    /**
     * Dient zum Bearbeiten der Tastatureingaben.
     */
    class ButtonAction extends AbstractAction {
        String s;
        ButtonAction(String s) {
            this.s = s;
        }
        public void actionPerformed(ActionEvent e) {
            MainFrame.this.tetris.actionPerformed(s);
        }
    }
}
