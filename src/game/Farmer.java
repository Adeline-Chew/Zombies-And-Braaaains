package game;

import java.util.Random;

import edu.monash.fit2099.engine.*;

public class Farmer extends Human {

    private Behaviour[] behaviours = {new SowBehaviour(), new WanderBehaviour()};

    public Farmer(String name){
        super(name, 'F', 50);
    }

    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        for (Behaviour behaviour : behaviours) {
            Action action = behaviour.getAction(this, map);
            if (action != null)
                return action;
        }
        return new DoNothingAction();
    }
}
