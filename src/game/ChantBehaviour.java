package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

import java.util.Random;

public class ChantBehaviour extends Action implements Behaviour{
    private int turn;
    private Random rand = new Random();

    @Override
    public Action getAction(Actor actor, GameMap map){
        turn++;
        if(turn % 10 == 0){
            return this;
        }
        return null;
    }

    @Override
    public String execute(Actor actor, GameMap map){
        int x, y;
        for(int i = 0; i < 5; i++){
            Zombie zombie = new Zombie("M-Zombie" + (i + 1));
            do{
                x = rand.nextInt(map.getXRange().max());
                y = rand.nextInt(map.getYRange().max());
            }
            while(map.at(x, y).containsAnActor() || !map.at(x, y).canActorEnter(zombie));
            map.addActor(zombie, map.at(x, y));
        }
        return menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor){
        return "Mambo marie made a chant, five Zombies appear in game.";
    }
}
