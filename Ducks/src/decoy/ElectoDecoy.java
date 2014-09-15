package decoy;

import behavior.ElectroQuack;
import behavior.IQuackBehavior;
import behavior.Quack;

/**
 * Created by Nehrist on 14.09.2014.
 */
public class ElectoDecoy extends Decoy{

    public ElectoDecoy() {
        quackBehavior = new ElectroQuack();
    }
}
