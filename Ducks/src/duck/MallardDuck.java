package duck;

import behavior.FlyWithWings;
import behavior.Quack;

/**
 * Created by Nehrist on 13.09.2014.
 */
public class MallardDuck extends Duck {

    public MallardDuck () {
        quackBehavior = new Quack();
        flyBehavior = new FlyWithWings();
    }

    public void display() {
        System.out.println("I`m a real Mallard duck");
    }
}
