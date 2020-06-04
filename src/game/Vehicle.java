package game;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.MoveActorAction;

/**
 * An item for Player to move between town map and compound map
 *
 */

public class Vehicle extends Item {

    /**
     * Constructor. The vehicle is not portable.
     *
     * @param map destination map
     */

    public Vehicle(GameMap map){
        super("Tesla", 'V', false);
        this.allowableActions.add(new MoveActorAction(map.at(39, 11), "toward another map..."));
    }

}
