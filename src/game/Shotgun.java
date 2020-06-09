package game;

import edu.monash.fit2099.engine.*;

/**
 * A ranged weapon.
 *
 */

public class Shotgun extends RangedWeapon {
    final String [] DIRECTIONS = {"North", "North-East", "East", "South-East", "South", "South-West", "West", "North-West"};

    /**
     * Constructor.
     */
    public Shotgun() {
        super("Shotgun", 'G', 20, "bangs");
    }

    /**
     * Display the menu of description of actions with 8 different directions
     *
     * @param actor actor performs the action
     * @param map the map where the current actor is
     * @param display the Display where the weapon's utterances will be displayed
     * @return an action selected from Player
     */
    @Override
    public Action subMenu(Actor actor, GameMap map, Display display) {
        Actions actions = new Actions();
        for(String direction : DIRECTIONS){
            actions.add(new ShotDirectionAction(this, direction));
        }
        return menu.showMenu(actor, actions, display);
    }

}
