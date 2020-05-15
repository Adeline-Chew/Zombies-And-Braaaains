package game;

import edu.monash.fit2099.engine.*;

public class EatBehaviour extends Action implements Behaviour {
    @Override
    public Action getAction(Actor actor, GameMap map) {
        for(Item item : actor.getInventory()){
            if(item.getDisplayChar() == 'E'){
                actor.heal(((Food) item).getFoodValue());
                actor.removeItemFromInventory(item);
                return this;
            }

        }
        return null;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        return actor.toString() + " eats and heals";
    }

    @Override
    public String menuDescription(Actor actor) {
        return "";
    }
}
