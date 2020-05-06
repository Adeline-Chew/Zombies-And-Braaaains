package game.Behaviours;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

public class HarvestBehaviour extends Action implements Behaviour {


    @Override
    public Action getAction(Actor actor, GameMap map) {
        int x = map.locationOf(actor).x();
        int y = map.locationOf(actor).y();

        if(map.locationOf(actor).getDisplayChar() == 'C'){

        }

        return null;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        return actor.toString() + "harvests a crop";
    }

    @Override
    public String menuDescription(Actor actor) {
        return "";
    }
}
