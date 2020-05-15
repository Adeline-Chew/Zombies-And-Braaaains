package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

public class FertiliseBehaviour extends Action implements Behaviour {


    @Override
    public String execute(Actor actor, GameMap map) {
        return actor.toString() + " fertilises a crop";
    }

    @Override
    public String menuDescription(Actor actor) {
        return "";
    }

    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location location = map.locationOf(actor);

        if(location.getDisplayChar() == 'c'){
            map.locationOf(actor).getGround().speedGrowth();
            return this;
        }
        return null;
    }
}
