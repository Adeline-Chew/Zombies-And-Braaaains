package game;

import edu.monash.fit2099.engine.*;

public class AimAction extends Action {
    Actor target;
    int concentration;

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
        return menuDescription(actor);
    }

    public int getConcentration() {
        return concentration;
    }

    /**
     * Returns a descriptive string
     *
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Spend a round aiming at " + target;
    }
}
