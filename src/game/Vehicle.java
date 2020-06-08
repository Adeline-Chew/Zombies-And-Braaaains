package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        this.allowableActions.add(new MoveActorAction(moveActor(map), "toward another map..."));
    }

    /**
     * Get the available adjacent location of the vehicle to move the actor.
     * @param map GameMap
     * @return Location without an Actor standing.
     */
    private Location moveActor(GameMap map){
        Location vehicleCo = map.at(40, 11);

        List<Exit> adjacent = new ArrayList<>(vehicleCo.getExits());
        Collections.shuffle(adjacent);

        for(Exit e : adjacent){
            if(!e.getDestination().containsAnActor())
                return e.getDestination();
        }
        return vehicleCo;
    }

}
