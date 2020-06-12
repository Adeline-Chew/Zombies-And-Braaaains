package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * Action for Player to use guns for ranged attack.
 *
 * @author Adeline Chew Yao Yi and Tey Kai Ying
 *
 */

public class RangedAttackAction extends Action {
    private RangedWeapon weapon;
    private Actor target;
    private double hitProb;
    private int damage;

    /**
     * Constructor.
     *
     * @param rangedWeapon weapon to be used
     * @param target targeted actor
     * @param hitProbability probability of hitting
     * @param damage weapon's damage
     */
    public RangedAttackAction(RangedWeapon rangedWeapon, Actor target, double hitProbability, int damage){
        this.weapon = rangedWeapon;
        this.target = target;
        this.hitProb = hitProbability;
        this.damage = damage;
    }

    /**
     * Perform the Ranged Attack Action. Calculate the probability of hitting.
     * Also check if the target is conscious after being attacked.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result = actor + " miss " + target;
        if(Math.random() <= hitProb) {
            target.damage(damage, map);
            result = actor + " use " + weapon.getName() + " fires " + target + " for " + damage + " damage.";

            if (!target.isConscious()) {
                map.removeActor(target);
                result += "\n" + target + " is killed.";
            }
        }
        return result;
    }

    /**
     * Describe the action to be displayed in the menu
     *
     * @param actor The actor performing the action.
     * @return a an empty string as it is not needed to be shown
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " fires " + target;
    }

}
