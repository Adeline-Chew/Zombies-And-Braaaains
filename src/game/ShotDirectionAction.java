package game;

import edu.monash.fit2099.engine.*;

public class ShotDirectionAction extends Action  {
    protected String direction;

    public ShotDirectionAction(String direction){
        this.direction = direction;
    }

    // getter for direction
    public String getDirection() {
        return direction;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        return menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " fires " + direction;
    }
}
