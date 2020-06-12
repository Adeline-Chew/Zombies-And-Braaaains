package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

import java.util.Random;

/**
 * Class extends from Behaviour allows Actor to do chant action.
 */

public class ChantBehaviour extends Action implements Behaviour{
    private int turn;
    private Random rand = new Random();

    /**
     * Return this class as an Action every 10 turns.
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return
     */
    @Override
    public Action getAction(Actor actor, GameMap map){
        turn++;
        if(turn % 10 == 0){
            return this;
        }
        return null;
    }

    /**
     * Generates 5 new Zombies, the location will be checked before add the zombie.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return menuDescription indicates 5 Zombies are born.
     */
    @Override
    public String execute(Actor actor, GameMap map){
        int x, y;
        for(int i = 0; i < 5; i++){
            Zombie zombie = new Zombie("M-Zombie" + (i + 1));
            do{
                x = rand.nextInt(map.getXRange().max());
                y = rand.nextInt(map.getYRange().max());
            }
            while(map.at(x, y).containsAnActor() || !map.at(x, y).canActorEnter(zombie));
            map.addActor(zombie, map.at(x, y));
        }
        return menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor){
        return "Mambo marie made a chant, five Zombies appear in game.";
    }
}
