package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

public class AimAction extends Action {
    private final Actor target;
    private int concentration;

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

    public int getConcentration() {
        return concentration;
    }

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
