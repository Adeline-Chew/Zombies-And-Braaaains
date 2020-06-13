package game;

import edu.monash.fit2099.engine.*;

/**
 * A ranged weapon.
 *
 */

public class Shotgun extends RangedWeapon {
    private final String [] DIRECTIONS = {"North", "North-East", "East", "South-East", "South", "South-West", "West", "North-West"};

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
            if(direction.contains("North") && map.locationOf(actor).y() != map.getYRange().min()){
                actions.add(new ShotDirectionAction(this, direction));
            }
            else if(direction.contains("South") && map.locationOf(actor).y() != map.getYRange().max()){
                actions.add(new ShotDirectionAction(this, direction));
            }
            else if(direction.contains("East") && map.locationOf(actor).x() != map.getXRange().max()){
                actions.add(new ShotDirectionAction(this, direction));
            }
            else if(direction.contains("West") && map.locationOf(actor).x() != map.getXRange().min()){
                actions.add(new ShotDirectionAction(this, direction));
            }
        }

        return menu.showMenu(actor, actions, display);
    }

}
