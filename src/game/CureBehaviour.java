package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CureBehaviour implements Behaviour {

    @Override
    public Action getAction(Actor actor, GameMap map) {
        List<Exit> exits = new ArrayList<>(map.locationOf(actor).getExits());
        Collections.shuffle(exits);

        for(Exit e : exits){
            if(e.getDestination().getActor().hasCapability(ZombieCapability.ALIVE)){
                return new InjectVaccineAction(e.getDestination().getActor());
            }
        }

        return null;
    }
}
