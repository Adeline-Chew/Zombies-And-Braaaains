package game;

import edu.monash.fit2099.engine.*;

public class MamboMarie extends ZombieActor {
    private final Behaviour[] behaviours = {
            new ChantBehaviour(),
            new ScreamBehaviour(),
            new WanderBehaviour()
    };

    public MamboMarie(){
        super("Mambo Marie", 'M', 200, ZombieCapability.UNDEAD);
    }

    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display){
        for (Behaviour behaviour : behaviours) {
            Action action = behaviour.getAction(this, map);
            if (action != null)
                return action;
        }
        return new DoNothingAction();
    }



    @Override
    public String damage(int points, GameMap map) {
        return null;
    }
    
}
