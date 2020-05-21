package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A class that allows a Farmer to sow a crop on a dirt
 *
 * @author Adeline Chew Yao Yi and Tey Kai Ying
 *
 */

public class SowBehaviour extends Action implements Behaviour {
    private Random rand = new Random();

    /**
     * Describe the sow action made by the actor
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a String of the action suitable for feedback in the UI.
     */

    @Override
    public String execute(Actor actor, GameMap map) {
        return actor.toString() + " plants a crop";
    }

    /**
     * Returns a description of this action suitable to display in the menu.
     * In this case, an empty string is returned as it is acted by Farmer
     *
     * @param actor The actor performing the action.
     * @return an empty String
     */
    @Override
    public String menuDescription(Actor actor) {
        return "";
    }

    /**
     * If adjacent locations of a Farmer is dirt, it has 33% chance to sow a crop
     * If not, return null
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return an Action, or null if no dirt present
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        // 1/3 chance to sow a crop
        boolean sowACrop = rand.nextInt(3) == 0;
        Crop seed = new Crop();

        // Is there dirt next to Actor?
        List<Exit> exits = new ArrayList<>(map.locationOf(actor).getExits());
        Collections.shuffle(exits);

        for(Exit e : exits){
            if(e.getDestination().getGround().hasCapability(Dirt.DirtCapability.SOIL) && sowACrop){
                e.getDestination().getGround().removeCapability(Dirt.DirtCapability.SOIL);
                e.getDestination().setGround(seed);
                return this;
            }
        }

        return null;
    }





}
