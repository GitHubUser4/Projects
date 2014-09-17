package duck;

import behavior.FlyNoWay;
import behavior.MuteQuack;
import behavior.Quack;

/**
 * Created by Nehrist on 14.09.2014.
 */
public class ModelDuck extends Duck {
    public ModelDuck() {
        flyBehavior = new FlyNoWay();
        quackBehavior = new Quack();
    }

    public void display() {
        System.out.println("Я утка-приманка");
    }
}
