package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * Class that allows Sniper to aim at a target.
 */

public class AimAction extends Action {
    private Actor target;
    private int concentration;

    /**
     * Constructor
     * @param actor Target to be aimed.
     */
    public AimAction(Actor actor){
        this.target = actor;
    }

    /**
     * Perform the Action.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        concentration++;
        if(concentration > 2)
            resetConcentration();
        return menuDescription(actor);
    }

    /**
     *
     * @return the value of the concentration.
     */
    public int getConcentration() {
        return concentration;
    }

    /**
     * Reset concentration to zero.
     */
    public void resetConcentration(){
        concentration = 0;
    }


    /**
     * Returns a descriptive string
     *
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " spend a round aiming at " + target;
    }
}
