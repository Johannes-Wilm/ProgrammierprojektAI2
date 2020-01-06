package tud.ai2.tetris.demo;

import tud.ai2.tetris.control.Tetris;
import tud.ai2.tetris.model.Form;
import tud.ai2.tetris.util.Const;
import tud.ai2.tetris.util.IterableFormFactory;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    private Tetris tetris;

    /**
     * Konstruktor der Demo. Gibt bestimmte Formen fuer das Spiel vor.
     */
    private Demo() {
        List<Form> formen = new ArrayList<>();
        formen.add(Const.L);
        formen.add(Const.T);
        formen.add(Const.S);
        formen.add(Const.Z);
        formen.add(Const.L);

        tetris = new Tetris(6,10, new IterableFormFactory(formen));
        tetris.actionPerformed("start");
    }

    /** Stellt einen Ausgangszustand her */
    private void start() {
        tetris.actionPerformed("drehen");
        tetris.actionPerformed("links");
        tetris.actionPerformed("links");
        tetris.actionPerformed("ablegen");

        tetris.actionPerformed("drehen");
        tetris.actionPerformed("drehen");
        tetris.actionPerformed("drehen");
        tetris.actionPerformed("links");
        tetris.actionPerformed("ablegen");

        tetris.actionPerformed("drehen");
        tetris.actionPerformed("drehen");
        tetris.actionPerformed("drehen");
        tetris.actionPerformed("links");
        tetris.actionPerformed("links");
        tetris.actionPerformed("ablegen");

        tetris.actionPerformed("drehen");
        tetris.actionPerformed("drehen");
        tetris.actionPerformed("drehen");
        tetris.actionPerformed("rechts");
        tetris.actionPerformed("rechts");
        tetris.actionPerformed("ablegen");
    }

    /** Loest im Ausgangszustand Clears aus */
    private void fuellen() {
        tetris.actionPerformed("drehen");
        tetris.actionPerformed("rechts");
        tetris.actionPerformed("ablegen");
    }

    public static void main(String[] args){
        Demo d = new Demo();
        d.start();
        d.fuellen();
    }

}

