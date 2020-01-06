package tud.ai2.tetris.util;

import tud.ai2.tetris.model.Form;

import java.util.Random;

/**
 * ZufallsFormFactory ist eine Implementierung der
 * abstrakten FormFactory, die zufaellige Formen
 * zurueckliefert.
 *
 * @author Sebastian Kasten (Melkom)
 * @author Robert Cieslinski
 * @version 2019-09-20
 */
public class ZufallsFormFactory implements FormFactory {
    public static ZufallsFormFactory INSTANZ = new ZufallsFormFactory();
    private static int lastPiece = -1;
    private final Random r;

    /** {@inheritDoc} */
    public Form gibForm() {
        Form form;
        int zufall = r.nextInt(8);

        if (zufall==7 || lastPiece==zufall)
            zufall = r.nextInt(7);

        switch(zufall) {
            case 0: form = Const.I; break;
            case 1: form = Const.J; break;
            case 2: form = Const.L; break;
            case 3: form = Const.O; break;
            case 4: form = Const.S; break;
            case 5: form = Const.T; break;
            default: form = Const.Z; break;
        }
        lastPiece = zufall;
        return form;
    }

    // Nicht verwenden. Es gibt nur eine Instanz.
    private ZufallsFormFactory() {
        r = new Random();
    }
}
