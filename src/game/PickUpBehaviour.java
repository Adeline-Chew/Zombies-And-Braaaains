package game;

import edu.monash.fit2099.engine.*;

/**
 * Allows an Actor to pick up an item.
 *
 * @author Adeline Chew Yao Yi & Tey Kai Ying
 *
 */

public class PickUpBehaviour implements Behaviour {

    /**
     * Return a pickUpAction if there is an Item on the location where the actor standing.
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return pickUpAction, or null if there is no item on the ground.
     */
    @Override
    public Action getAction(Actor actor, GameMap map){
        Location here = map.locationOf(actor);

        if(actor.hasCapability(ZombieCapability.HOLD)) {
            for (Item item : here.getItems()) {
                if (item.hasCapability(ItemCapability.AS_WEAPON)) {
                    return item.getPickUpAction();
                }
                if (item.hasCapability(ItemCapability.EDIBLE) && actor.hasCapability(ZombieCapability.ALIVE)) {
                    return item.getPickUpAction();
                }
            }
        }
        return null;
    }
}
