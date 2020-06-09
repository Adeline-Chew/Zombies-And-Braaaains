package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;


/**
 * Action for Doctor inject vaccine into Humans.
 *
 * @author Adeline Chew Yao Yi and Tey Kai Ying
 */

public class InjectVaccineAction extends Action {
    private Actor target;

    /**
     * Constructor.
     *
     * @param actor target to be injected vaccine
     */

    public InjectVaccineAction(Actor actor){
        target = actor;
    }

    /**
     * Perform the Inject Action. Injected human has a new capability.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        target.addCapability(ZombieCapability.IMMUNE);
        return target + " get vaccine injection";
    }

    /**
     * Describe the action to be displayed in the menu
     *
     * @param actor The actor performing the action.
     * @return a an empty string as it is not needed to be shown
     */
    @Override
    public String menuDescription(Actor actor) {
        return "";
    }
}
