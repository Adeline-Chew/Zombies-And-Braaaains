package game;

import edu.monash.fit2099.engine.*;

public class ChooseWeaponAction extends Action {
    private final RangedWeapon weapon;
    private final Display display;

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
        for (Item item : actor.getInventory()){
            if(item.hasCapability(ItemCapability.BULLET)) {
                weapon.loadAmmunition((AmmunitionBox) item);
                actor.removeItemFromInventory(item);
                break;
            }
        }
        if(weapon.getAmountOfBullet() > 0) {
            Action action = weapon.subMenu(actor, map, display);
            return action.execute(actor, map);
        }
        else{
            return weapon + " needs to load bullet to fire.";
        }
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
