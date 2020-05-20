package game;

import edu.monash.fit2099.engine.*;


public class PickUpBehaviour implements Behaviour {

    @Override
    public Action getAction(Actor actor, GameMap map){
        Location here = map.locationOf(actor);

        for(Item item: here.getItems()){
            if(item.hasCapability(ItemCapability.AS_WEAPON)){
                return item.getPickUpAction();
            }
            if(item.hasCapability(ItemCapability.EDIBLE) && actor.hasCapability(ZombieCapability.ALIVE)){
                return item.getPickUpAction();
            }
        }
        return null;
    }
}
