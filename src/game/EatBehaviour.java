package game;

import edu.monash.fit2099.engine.*;

/**
 * A class that allows a Human or Farmer to eat a food.
 *
 * @author Adeline Chew Yao Yi and Tey Kai Ying
 *
 */

public class EatBehaviour implements Behaviour {

    /**
     * If an Actor has capability DAMAGED it will search inventory for food.
     * If there is an Item with capability EDIBLE, it will return an Eat action.
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return an Action, or null if Actor is healthy
     */

    @Override
    public Action getAction(Actor actor, GameMap map) {
        if(actor.hasCapability(ZombieCapability.DAMAGED)){                  // DAMAGED capability indicates the Actor needs heal
            for(Item item : actor.getInventory()) {
                if (item.hasCapability(ItemCapability.EDIBLE)) {            // EDIBLE capability indicates the item is food
                    actor.removeCapability(ZombieCapability.DAMAGED);
                    return new EatAction((Food) item);
                }
            }
        }
        return null;
    }

}
