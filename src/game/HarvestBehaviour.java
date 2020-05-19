package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class that allows Farmer to harvest a ripen crop nearby
 *
 * @author Adeline Chew Yao Yi and Tey Kai Ying
 */

public class HarvestBehaviour extends Action implements Behaviour {

    /**
     * Describe the harvest action made by the actor
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a String of the action suitable for feedback in the UI.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
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
        return "";
    }

    /**
     * If adjacent locations of a Farmer has a ripen crop, the Farmer will harvest the crop
     * The ripen crop will be dropped on the ground
     * If no crop, returns null
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return an Action, or null if no crop present
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        double probability = Math.random();
        String foodName;
        Exit here = new Exit("Stay" , map.locationOf(actor), "z");

        // Decide food name randomly to make game more fun
        if(probability <= 0.30){
            foodName = "Yummyy";
        }
        else if(probability > 0.30 && probability <= 0.60){
            foodName = "Fruity";
        }
        else{
            foodName = "SuperVege";
        }

        // Is there ripen crop next to Actor?
        List<Exit> exits = new ArrayList<Exit>(map.locationOf(actor).getExits());
        exits.add(here);
        Collections.shuffle(exits);

        for(Exit e : exits){
            if(e.getDestination().getGround().hasCapability(Crop.CropCapability.RIPEN)){
                e.getDestination().setGround(new Dirt());
                e.getDestination().addItem(new Food(foodName));
                return this;
            }
        }

        return null;
    }


}
