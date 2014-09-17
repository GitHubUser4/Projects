package decoy;

import behavior.IQuackBehavior;

/**
 * Created by Nehrist on 14.09.2014.
 */
public abstract class Decoy {
    IQuackBehavior quackBehavior;

    public Decoy() {

    }

    public void performQuack() {
        quackBehavior.quack();
    }

}
