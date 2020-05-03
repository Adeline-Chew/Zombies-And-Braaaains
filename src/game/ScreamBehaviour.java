package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;


public class ScreamBehaviour extends Action implements Behaviour{

    @Override
    public Action getAction(Actor actor, GameMap map){
        double probability = Math.random();
        if(probability <= 0.1){
            execute(actor, map);
            return this;
        }
        return null;
    }

    @Override
    public String execute(Actor actor, GameMap map){
        return menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor){
        double probability = Math.random();
        String retStr = actor.toString() + " says: ";
        if(probability <= 0.25){
            retStr += "Braaaaaaaaains";
        }
        else if(probability > 0.25 && probability <= 0.5){
            retStr += "Errrrrrr...";
        }
        else if(probability > 0.5 && probability <= 0.75){
            retStr += "BLEEAGHAAAAAH";
        }
        else{
            retStr += "Hungry...";
        }
        return retStr;
    }

}
