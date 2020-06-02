package game;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.MoveActorAction;

public class Vehicle extends Item {

    public Vehicle(GameMap map){
        super("Tesla", 'V', false);
        this.allowableActions.add(new MoveActorAction(map.at(39, 11), "travelling..."));
    }

}
