package tud.ai2.tetris.util;

import tud.ai2.tetris.model.Form;

/**
 * FormFactory ist eine abstrakte Fabrik, die Formen liefert.
 *
 * @author Sebastian Kasten (Melkom)
 * @author Robert Cieslinski
 * @version 2013-06-03
 */
public interface FormFactory {
    /**
     * Gibt eine neue Form zurueck.
     * @return eine neue Form
     */
    Form gibForm();
}
