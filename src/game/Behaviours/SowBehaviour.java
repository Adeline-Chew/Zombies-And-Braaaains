package game.Behaviours;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import game.Crop;

import java.util.Random;

public class SowBehaviour extends Action implements Behaviour {
    private Random rand = new Random();
    private int randomInteger = rand.nextInt(10);


    @Override
    public String execute(Actor actor, GameMap map) {
        return actor.toString() + " plants a crop";
    }

    @Override
    public Action getAction(Actor actor, GameMap map) {
        boolean sowACrop = rand.nextInt(3) == 0;
        Crop seed = new Crop("Food", true, randomInteger);
        Crop crop;

        int x = map.locationOf(actor).x();
        int y = map.locationOf(actor).y();

        // fertilise
        for(Item item : map.locationOf(actor).getItems()){
            if(item.getDisplayChar() == 'c'){
                crop = (Crop) item;
                crop.speedGrowth();
                return this;
            }
        }

        // right patch
        if(map.at(x+1, y).getDisplayChar() == '.'){
            if(sowACrop && map.at(x+1, y).getItems().isEmpty()) {
                map.at(x + 1, y).addItem(seed);
                return this;
            }
        }

        //left patch
        if(map.at(x-1, y).getDisplayChar() == '.'){
            if(sowACrop && map.at(x+1, y).getItems().isEmpty()) {
                map.at(x - 1, y).addItem(seed);
                return this;
            }
        }

        return null;
    }


    @Override
    public String menuDescription(Actor actor) {
        return "";
    }


}
