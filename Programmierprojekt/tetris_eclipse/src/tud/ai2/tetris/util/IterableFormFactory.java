package tud.ai2.tetris.util;

import tud.ai2.tetris.model.Form;

import java.util.Iterator;

/**
 * IterableFormFactory ist eine Implementierung der
 * abstrakten FormFactory, die neue Formen aus einem
 * Iterable laedt. Wenn der Iterable durchlaufen ist,
 * wird von vorne begonnen.
 *
 * @author Sebastian Kasten (Melkom)
 * @version 2013-06-03
 */
public class IterableFormFactory implements FormFactory {
    /** Form-Vorrat */
    private final Iterable<Form> formen;
    /** Iterator auf dem Form-Vorrat */
    private Iterator<Form> iterator;

    /**
     * Erstellt eine neue IterableFormFactory mit dem
     * uebergebenen Vorrat an Formen.
     *
     * @param formen Vorrat an Formen
     */
    public IterableFormFactory(final Iterable<Form> formen) {
        this.formen = formen;
    }

    /** {@inheritDoc} */
    public Form gibForm() {
        if(this.iterator==null || !this.iterator.hasNext())
            this.iterator = formen.iterator();

        return this.iterator.next();
    }

    /**
     * Laesst den Iterator von vorne beginnen.
     *
     * @author Robert Cieslinski
     */
    public void reset(){
        iterator = formen.iterator();
    }

}
