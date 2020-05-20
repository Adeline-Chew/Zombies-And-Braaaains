package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * Action to allow Actor to eat
 *
 * @author Adeline Chew Yao Yi and Tey Kai Ying
 */

public class EatAction extends Action {
    private Food food;

    /**
     * Constructor.
     * @param initFood food to be eaten
     */
    public EatAction(Food initFood){
        food = initFood;
    }

    /**
     * Heal the actor with food value and remove the food after eating.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a suitable description about the eat action in the UI
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        actor.heal(food.getFoodValue());
        actor.removeItemFromInventory(food);

        return actor.toString() + " heals by eating " + food.toString() + ": "+ food.getFoodValue() + " points";
    }

    /**
     * Describe the action to be displayed in the menu
     *
     * @param actor The actor performing the action.
     * @return a string about the action
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Player heals with food";
    }
}
