package game;

import edu.monash.fit2099.engine.*;



public class EatBehaviour implements Behaviour {

    @Override
    public Action getAction(Actor actor, GameMap map) {
        for(Item item : actor.getInventory()){
            if(item.hasCapability(Food.FoodCapability.EDIBLE)){
                return new EatAction((Food)item);
            }

        }
        return null;
    }

}
