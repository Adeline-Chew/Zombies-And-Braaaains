package game;

import edu.monash.fit2099.engine.*;

/**
 * A class represents a character in this game.
 */

public class MamboMarie extends ZombieActor {
    private Behaviour[] behaviours = {
            new ChantBehaviour(),
            new ScreamBehaviour(),
            new WanderBehaviour()
    };

    /**
     * Constructor.
     */
    public MamboMarie(){
        super("Mambo Marie", 'M', 200, ZombieCapability.UNDEAD);
    }

    /**
     * Mambo Marie will make a chant every 10 turns to summon Zombie, otherwise,
     * she will randomly say some words or wander around.
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return
     */
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display){
        for (Behaviour behaviour : behaviours) {
            Action action = behaviour.getAction(this, map);
            if (action != null)
                return action;
        }
        return new DoNothingAction();
    }
    
}
