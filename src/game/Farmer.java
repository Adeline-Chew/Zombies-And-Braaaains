package game;

import edu.monash.fit2099.engine.*;

/**
 * A Farmer.
 *
 * This farmer has same characteristics as human, but also
 * has the abilities to sow a crop, fertilise a crop and harvest a crop.
 *
 * @author Adeline Chew Yao Yi and Tey Kai Ying
 *
 */

public class Farmer extends Human {

    private Behaviour[] behaviours = {
            new FertiliseBehaviour(),
            new HarvestBehaviour(),
            new SowBehaviour(),
            };

    /**
     * The constructor creates default Farmer
     *
     * @param name the Farmer's display name
     */
    public Farmer(String name){
        super(name, 'F', 50);
    }

    /**
     * Firstly heck if the Farmer stands on a unripe crop. If not, check if the Farmer stands on an ripen crop.
     * If not both, the Farmer will sow a crop while standing on a dirt depends on the probability.
     * If no crops or dirt are close enough it will do normal human's behaviours.
     *
     * @param actions list of possible Actions
     * @param lastAction previous game.Action, if it was a multiturn action
     * @param map the map where the current Farmer is
     * @param display the Display where the Farmer's utterances will be displayed
     */
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {

        for (Behaviour behaviour : behaviours) {
            Action action = behaviour.getAction(this, map);
            if (action != null)
                return action;
        }
        return super.playTurn(actions, lastAction, map, display);
    }
}
