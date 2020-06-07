package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

public class RangedAttackAction extends Action {
    private final RangedWeapon weapon;
    private final Actor target;
    private final double hitProb;
    private final int damage;

    public RangedAttackAction(RangedWeapon rangedWeapon, Actor target, double hitProbability, int damage){
        this.weapon = rangedWeapon;
        this.target = target;
        this.hitProb = hitProbability;
        this.damage = damage;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        String result = actor + " miss " + target;
        if(Math.random() <= hitProb) {
            target.damage(damage, map);
            weapon.shoot();
            result = actor + " use " + weapon.getName() + " fires " + target + " for " + damage + " damage.";

            if(!target.isConscious())
                result += "\n" + target + " is killed.";
        }
        return result;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " fires " + target;
    }

}
