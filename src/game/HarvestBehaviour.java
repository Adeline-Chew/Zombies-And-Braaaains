package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class that allows Farmer/Player to harvest a ripen crop nearby
 *
 * @author Adeline Chew Yao Yi and Tey Kai Ying
 */

public class HarvestBehaviour implements Behaviour {


    /**
     * If location or adjacent locations of an Actor has a ripen crop, the Actor will harvest the crop
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

        // this exit is the location of the actor
        Exit here = new Exit("Stay" , map.locationOf(actor), "z");

        // Food with different names to make the game more fun!
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
        List<Exit> exits = new ArrayList<>(map.locationOf(actor).getExits());
        exits.add(here);
        Collections.shuffle(exits);

        for(Exit e : exits){
            if(e.getDestination().getGround().hasCapability(Crop.CropCapability.RIPEN)){
                return new HarvestAction(e, foodName);
            }
        }

        return null;
    }



}
