package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;

/**
 * Action to allow the Player/Farmer to harvest a crop.
 *
 * @author Adeline Chew Yao Yi and Tey Kai Ying
 */

public class HarvestAction extends Action {
    private Exit ripenCropLocation;
    private String nameOfFood;

    /**
     * Constructor.
     *
     * @param e the location of ripen crop
     * @param foodName a string, name of the food
     */
    public HarvestAction(Exit e, String foodName){
        ripenCropLocation = e;
        nameOfFood = foodName;
    }

    /**
     * Describe the harvest action made by the actor
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a String of the action suitable for feedback in the UI.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        ripenCropLocation.getDestination().setGround(new Dirt());         // the ground was crop, now set back to dirt
        ripenCropLocation.getDestination().addItem(new Food(nameOfFood));
        return actor.toString() + " harvests a crop";
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
        return "Player harvests a crop";
    }

}
