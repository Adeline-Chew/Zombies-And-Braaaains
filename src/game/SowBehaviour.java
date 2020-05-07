package game;

import edu.monash.fit2099.engine.*;

import java.util.Random;

public class SowBehaviour extends Action implements Behaviour {
    private Random rand = new Random();

    @Override
    public String execute(Actor actor, GameMap map) {
        return actor.toString() + " plants a crop";
    }

    @Override
    public Action getAction(Actor actor, GameMap map) {
        boolean sowACrop = rand.nextInt(3) == 0;
        Crop seed = new Crop();
        Crop crop;
        Location location = map.locationOf(actor);
        int x = location.x();
        int y = location.y();

        // fertilise
        if(location.getDisplayChar() == 'c'){
            crop = (Crop) map.locationOf(actor).getGround();
            crop.speedGrowth();
            return this;
        }

        // right patch
        if(map.at(x+1, y).getDisplayChar() == '.' && sowACrop){
            map.at(x + 1, y).setGround(seed);
            return this;
        }

        //left patch
        if(map.at(x-1, y).getDisplayChar() == '.' && sowACrop){
            map.at(x - 1, y).setGround(seed);
            return this;
        }

        return null;
    }


    @Override
    public String menuDescription(Actor actor) {
        return "";
    }


}
