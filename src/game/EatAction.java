package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

public class EatAction extends Action {
    private Food food;

    public EatAction(Food initFood){
        food = initFood;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        actor.heal(food.getFoodValue());
        actor.removeItemFromInventory(food);

        return actor.toString() + "heals by eating " + food.toString() + ": "+ food.getFoodValue() + " points";
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Heal with food";
    }
}
