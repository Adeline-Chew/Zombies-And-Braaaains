package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;

public class ChooseWeaponAction extends Action {
    RangedWeapon weapon;
    Display display;

    public ChooseWeaponAction(Display display, RangedWeapon weapon){
        this.weapon = weapon;
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
        Action action = weapon.subMenu(actor, map, display);
        return action.execute(actor, map);
    }

    /**
     * Returns a descriptive string
     *
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " use " + weapon.getName();
    }

    public RangedWeapon getWeapon(){return weapon;}

}
