package game;

import edu.monash.fit2099.engine.*;

public class ChooseTargetAction extends Action {
    Actor target;
    RangedWeapon sniper;
    AimAction aim;
    Menu menu = new Menu();
    Display display;

    public ChooseTargetAction(Actor actor, RangedWeapon weapon, Display display){
        this.target = actor;
        this.sniper = weapon;
        this.aim = new AimAction(target);
        this.display = display;
    }

    /**
     * Perform the Action.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Actions actions = new Actions();
        actions.add(aim);
        actions.add(new RangedAttackAction(sniper, target));

        return menu.showMenu(actor, actions, display).execute(actor, map);
    }

    /**
     * Returns a descriptive string
     *
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " shoots " + target;
    }
}
