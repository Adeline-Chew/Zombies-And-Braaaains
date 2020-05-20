package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * Allows an Actor to say something.
 *
 * @author Adeline Chew Yao Yi & Tey Kai Ying
 *
 */

public class ScreamBehaviour extends Action implements Behaviour{

    /**
     * The actor has 10% of chance to say some words.
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return scream action, or null if failed.
     */
    @Override
    public Action getAction(Actor actor, GameMap map){
        double probability = Math.random();
        if(probability <= 0.1) {
            return this;
        }
        return null;
    }

    @Override
    public String execute(Actor actor, GameMap map){
        return menuDescription(actor);
    }

    /**
     * Each word has 25% probability to be chosen.
     *
     * @param actor The actor performing the action.
     * @return Message to display.
     */
    @Override
    public String menuDescription(Actor actor){
        double probability = Math.random();
        String retStr = actor.toString() + " says: ";
        if(probability <= 0.25){
            retStr += "Braaaaaaaaains";
        }
        else if(probability > 0.25 && probability <= 0.5){
            retStr += "Errrrrrr...";
        }
        else if(probability > 0.5 && probability <= 0.75){
            retStr += "BLEEAGHAAAAAH";
        }
        else{
            retStr += "Hungry...";
        }
        return retStr;
    }

}
