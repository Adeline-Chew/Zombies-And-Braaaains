package game;

import edu.monash.fit2099.engine.*;

/**
 * Action to allow the Player to quit the game.
 *
 * @author Adeline Chew Yao Yi and Tey Kai Ying
 */
public class QuitGameAction extends Action {

    /**
     * Remove player from the map
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a String of the action suitable for feedback in the UI.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        map.removeActor(actor);
        return "Player quits the game.";
    }

    /**
     * Returns a description of this action suitable to display in the menu.
     *
     * @param actor The actor performing the action.
     * @return a string description about quit action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " quit the game";
    }
}
