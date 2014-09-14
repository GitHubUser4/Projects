package decoy;

import behavior.Quack;

/**
 * Created by Nehrist on 14.09.2014.
 */
public class ManualDecoy extends Decoy {

    public ManualDecoy() {
        quackBehavior = new Quack();
    }

}
