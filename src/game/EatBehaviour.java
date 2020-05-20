package game;

import edu.monash.fit2099.engine.*;



public class EatBehaviour implements Behaviour {

    @Override
    public Action getAction(Actor actor, GameMap map) {
        if(actor.hasCapability(ZombieCapability.DAMAGED)){
            for(Item item : actor.getInventory()) {
                if (item.hasCapability(ItemCapability.EDIBLE)) {
                    actor.removeCapability(ZombieCapability.DAMAGED);
                    return new EatAction((Food) item);
                }
            }
        }
        return null;
    }

}
