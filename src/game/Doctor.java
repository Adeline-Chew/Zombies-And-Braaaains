package game;

import edu.monash.fit2099.engine.*;

import java.util.Random;

public class Doctor extends Human{

    private Behaviour[] behaviours = {
            new CureBehaviour()
    };

    public Doctor(String name) {
        super(name, 'D', 100);
    }

    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {

        // doctor can self heal 
        if(this.hitPoints < this.maxHitPoints && Math.random() < 0.5){
            int healPoint = new Random().nextInt(16) + 15;
            heal(healPoint);
        }

        for (Behaviour behaviour : behaviours) {
            Action action = behaviour.getAction(this, map);
            if (action != null)
                return action;
        }
        return super.playTurn(actions, lastAction, map, display);
    }
}
