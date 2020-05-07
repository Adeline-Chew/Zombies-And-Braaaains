package game.Behaviours;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.Dirt;
import game.Food;

public class HarvestBehaviour extends Action implements Behaviour {


    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location location = map.locationOf(actor);
        int x = location.x();
        int y = location.y();

        if(location.getDisplayChar() == 'C' || map.at(x+1,y).getDisplayChar() == 'C' || map.at(x-1,y).getDisplayChar() == 'C'){
            Dirt dirt = new Dirt();
            location.setGround(dirt);
            location.addItem(new Food("Yumm", true));
            return this;
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
