package game;

import edu.monash.fit2099.engine.*;

import java.util.HashMap;

public class ShotDirectionAction extends Action  {
    protected String direction;
    protected RangedWeapon weapon;

    public ShotDirectionAction(RangedWeapon weapon, String direction){
        this.direction = direction;
        this.weapon = weapon;
    }

    // getter for direction
    public String getDirection() {
        return direction;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        Location here = map.locationOf(actor);
        NumberRange xs, ys;
        Actions actions = new Actions();
        String result = "";

        xs = new NumberRange(here.x() - 1, 3);
        ys = new NumberRange(here.y(), 3);

        for(int x : xs){
            for(int y : ys){
                if(map.at(x,y).containsAnActor()){
                    actions.add(new RangedAttackAction(weapon, map.at(x, y).getActor(), 0.75, weapon.damage()));
                }
            }
        }

        for(Action action: actions){
            result += "\n" + action.execute(actor, map) ;
        }

        return menuDescription(actor) + result;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " fires " + direction;
    }
}
