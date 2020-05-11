package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

import java.util.Random;

public class CraftAction extends Action {
    private ZombieLimbs limb;

    private Random rand = new Random();

    public CraftAction(ZombieLimbs item){
        limb = item;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        String result;
        actor.removeItemFromInventory(limb);
        if(rand.nextBoolean()){
            actor.addItemToInventory(new ZombieClub());
            result = "Club";
        }
        else{
            actor.addItemToInventory(new ZombieMace());
            result = "Mace";
        }

        return actor + " gets a " + result + " as new weapon!";
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " crafts a new weapon";
    }
}
