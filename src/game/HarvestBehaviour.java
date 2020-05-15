package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

public class HarvestBehaviour extends Action implements Behaviour {


    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location location = map.locationOf(actor);
        int x = location.x();
        int y = location.y();
        Location [] locations = {location, map.at(x+1, y), map.at(x-1,y), map.at(x, y+1), map.at(x, y-1)};

        for(Location here : locations){
            if(here.getGround().hasCapability(Crop.CropCapability.RIPEN)){
                here.setGround(new Dirt());
                here.addItem(new Food("Yumm", true));
                return this;
            }
        }

        return null;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        return actor.toString() + " harvests a crop";
    }

    @Override
    public String menuDescription(Actor actor) {
        return "";
    }
}
