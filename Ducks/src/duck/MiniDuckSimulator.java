package duck;

import behavior.FlyRocketPowered;
import decoy.Decoy;
import decoy.ElectoDecoy;
import decoy.ManualDecoy;

/**
 * Created by Nehrist on 14.09.2014.
 */
public class MiniDuckSimulator {
    public static void main(String[] args) {

        Duck mallard = new MallardDuck();
        mallard.performFly();
        mallard.performQuack();

        Duck rubber = new RubberDuck();
        rubber.performFly();
        rubber.performQuack();

        Duck model = new ModelDuck();
        model.performFly();
        model.setFlyBehavior(new FlyRocketPowered());
        model.performFly();

        Decoy manualDecoy = new ManualDecoy();
        manualDecoy.performQuack();

        Decoy electricDecoy = new ElectoDecoy();
        electricDecoy.performQuack();
    }
}
