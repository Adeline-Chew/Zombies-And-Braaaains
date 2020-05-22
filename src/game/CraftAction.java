package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * Action to allow the Player to craft a weapon.
 *
 * @author Adeline Chew Yao Yi and Tey Kai Ying
 */
public class CraftAction extends Action {
    private Item limb;

    /**
     * Constructor.
     *
     * @param item item to be crafted as weapon item
     */
    public CraftAction(Item item){
        try{
            if(item != null){
                limb = item;
            }
        } catch(NullPointerException e){
            System.out.println("Item to be crafted is null");
        }

    }

    /**
     * Craft a mace or club depends on the item's capability.
     * Then remove the original item from inventory after done crafting.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a suitable description about the craft action in the UI
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String result = "";

        actor.removeItemFromInventory(limb);

        if(limb.hasCapability(ZombieLimbs.Limb.ARM)){
            actor.addItemToInventory(new ZombieClub());
            result += "Club";
        }
        else if(limb.hasCapability(ZombieLimbs.Limb.LEG)){
            actor.addItemToInventory(new ZombieMace());
            result += "Mace";
        }

        return actor + " gets a " + result + " as new weapon!";
    }

    /**
     * Describe the action to be displayed in the menu
     *
     * @param actor The actor performing the action.
     * @return a string about the action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " crafts a new weapon";
    }
}
