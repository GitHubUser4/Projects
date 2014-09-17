package duck;

import behavior.FlyNoWay;
import behavior.FlyWithWings;
import behavior.MuteQuack;
import behavior.Squeak;

/**
 * Created by Nehrist on 14.09.2014.
 */
public class RubberDuck extends Duck {

    public RubberDuck() {
        quackBehavior = new Squeak();
        flyBehavior = new FlyNoWay();
    }

    public void display() {
        System.out.println("Я резиновая утка");
    }
}
