package game;

import edu.monash.fit2099.engine.*;


public class PickUpBehaviour implements Behaviour {

    @Override
    public Action getAction(Actor actor, GameMap map){
        Location here = map.locationOf(actor);

        // Pick up if the actor has intrinsic weapon only
        if(actor.getWeapon() instanceof WeaponItem){
            return null;
        }

        for(Item item: here.getItems()){
            if(item instanceof WeaponItem && actor.getDisplayChar() == 'Z'){
                return item.getPickUpAction();
            }
            if(item.getDisplayChar() == 'E' && actor.getDisplayChar() == 'H'){
                return item.getPickUpAction();
            }
        }
        return null;
    }
}
