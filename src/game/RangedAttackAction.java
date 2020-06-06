package game;

import edu.monash.fit2099.engine.*;

public class RangedAttackAction extends Action {
    private Menu menu = new Menu();
    private RangedWeapon weapon;
    private Actor target;

    public RangedAttackAction(RangedWeapon rangedWeapon, Actor target){
        this.weapon = rangedWeapon;
        this.target = target;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        String result;
        target.damage(weapon.damage(), map);
        result = actor + " use " + weapon.getName() + " fires " + target + " for " + weapon.damage() + " damage.";
        return result;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " fires " + weapon.getName();
    }

}
