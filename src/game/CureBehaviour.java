package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class that allows a Doctor to cure.
 *
 * @author Adeline Chew Yao Yi and Tey Kai Ying
 *
 */

public class CureBehaviour implements Behaviour {

    /**
     * If Doctor is surrounded with a Human, Doctor will present an inject action
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return an Action, or null if no Actor around
     */

    @Override
    public Action getAction(Actor actor, GameMap map) {
        List<Exit> exits = new ArrayList<>(map.locationOf(actor).getExits());
        Collections.shuffle(exits);

        for(Exit e : exits){
            if(e.getDestination().containsAnActor() && e.getDestination().getActor().hasCapability(ZombieCapability.ALIVE)){
                return new InjectVaccineAction(e.getDestination().getActor());
            }
        }
        return null;
    }
}
