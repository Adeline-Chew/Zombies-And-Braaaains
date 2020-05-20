package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * A class that allows a Farmer to fertilise a crop.
 *
 * @author Adeline Chew Yao Yi and Tey Kai Ying
 *
 */

public class FertiliseBehaviour extends Action implements Behaviour {

    /**
     * Describe the fertilise action made by the actor
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a String of the action suitable for feedback in the UI.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        return actor.toString() + " fertilises a crop";
    }

    /**
     * Returns a description of this action suitable to display in the menu.
     * In this case, an empty string is returned as it is acted by Farmer
     *
     * @param actor The actor performing the action.
     * @return an empty String.
     */

    @Override
    public String menuDescription(Actor actor) {
        return "";
    }

    /**
     * If Farmer stands on an unripe crop, the Farmer will increase turn of the crop.
     * If no crop, returns null
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return an Action, or null if no crop present
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location location = map.locationOf(actor);

        if(location.getGround().hasCapability(Crop.CropCapability.UNRIPE)){
            location.getGround().changeGroundStatus();
            return this;
        }
        return null;
    }
}
