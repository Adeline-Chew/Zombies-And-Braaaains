package game;

import edu.monash.fit2099.engine.*;

import static java.lang.Math.abs;

/**
 * Action for Player to shoot in a specific direction.
 *
 * @author Adeline Chew Yao Yi and Tey Kai Ying
 */

public class ShotDirectionAction extends Action {
    protected String direction;
    protected RangedWeapon weapon;

    /**
     * Constructor.
     *
     * @param weapon    weapon to perform the action
     * @param direction a string represents the shooting direction
     */
    public ShotDirectionAction(RangedWeapon weapon, String direction) {
        this.direction = direction;
        this.weapon = weapon;
    }

    /***
     * Perform the shooting in a specific direction. Shotgun sends a
     * 90 cone of pellets out that can hit more than one target. Its range is 3 squares.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Location here = map.locationOf(actor);
        Actions actions = new Actions();
        String result = "";


        if (direction.equals("North-East") || direction.equals("South-East") || direction.equals("South-West") || direction.equals("North-West")) {

            NumberRange xs = setXRange(here, direction);
            NumberRange ys = setYRange(here, direction);


            for (int x : xs) {
                for (int y : ys) {
                    if (x != here.x() || y != here.y()) {
                        if (map.at(x, y).containsAnActor()) {
                            actions.add(new RangedAttackAction(weapon, map.at(x, y).getActor(), 0.75, weapon.damage()));
                        }
                    }
                }
            }
        }

        else {

            NumberRange ranges = setRange(here, direction);

            if (direction.equals("East") || direction.equals("West")){
                xShoot(actions, map, here, ranges);
            }
            else if (direction.equals("South") || direction.equals("North")){
                yShoot(actions, map, here, ranges);
            }

        }


        for(Action action:actions){
            result += "\n" + action.execute(actor, map);
        }

        weapon.shoot();

        return menuDescription(actor) +result;
    }

    /**
     * Describe the action to be displayed in the menu.
     *
     * @param actor The actor performing the action.
     * @return a string describing the action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " fires " + direction;
    }

    /**
     * Set the x range about the diagonal location of the actor.
     *
     * @param here location of the actor
     * @param direct string about the direction
     * @return a NumberRange
     */
    private NumberRange setXRange(Location here, String direct){
        NumberRange xs = null;
        if(direct.contains("East") ){
            xs = new NumberRange(here.x(), 4);
        }
        else if(direct.contains("West")){
            xs = new NumberRange(here.x()-3, 4);
        }
        return xs;
    }

    /**
     * Set the y range about the diagonal location of the actor.
     *
     * @param here location of the actor
     * @param direct string about the direction
     * @return a NumberRange
     */
    private NumberRange setYRange(Location here, String direct){
        NumberRange ys = null;
        if(direct.contains("North")){
            ys = new NumberRange(here.y()-3, 4);
        }
        else if(direct.contains("South")){
            ys = new NumberRange(here.y(), 4);
        }
        return ys;
    }

    /**
     * Set a NumberRange about the location of the actor.
     *
     * @param here location of the actor
     * @param direct string about the direction
     * @return a NumberRange
     */
    private NumberRange setRange(Location here, String direct) {
        NumberRange range = null;

        if(direct.equals("West")){
            range = new NumberRange(here.x()-3, 3);
        }
        else if(direct.equals("East")){
            range = new NumberRange(here.x()+1, 3);
        }
        else if(direct.equals("South")){
            range = new NumberRange(here.y()+1, 3);
        }
        else if(direct.equals("North")){
            range = new NumberRange(here.y()-3, 3);
        }
        return range;
    }

    private void xShoot(Actions actions, GameMap map, Location here, NumberRange ranges){
        for(int x : ranges){
            int diff = abs(here.x() - x);
            for(int i = 0; i <= diff; i++){
                if(map.at(x, (here.y() + diff) - i).containsAnActor() && diff != i){
                    actions.add(new RangedAttackAction(weapon, map.at(x, (here.y() + diff) - i).getActor(), 0.75, weapon.damage()));
                }
                if(map.at(x, (here.y() - diff) + i).containsAnActor()){
                    actions.add(new RangedAttackAction(weapon, map.at(x, (here.y() - diff) + i).getActor(), 0.75, weapon.damage()));
                }
            }
        }
    }

    private void yShoot(Actions actions, GameMap map, Location here, NumberRange ranges){
        for(int y : ranges){
            int diff = abs(here.y() - y);
            for(int i = 0; i <= diff; i++){
                if(map.at((here.x() + diff) - i, y).containsAnActor() &&  diff - i != 0){
                    actions.add(new RangedAttackAction(weapon, map.at((here.x() + diff) - i, y).getActor(), 0.75, weapon.damage()));
                }
                if(map.at((here.x() - diff) + i, y).containsAnActor()) {
                    actions.add(new RangedAttackAction(weapon, map.at((here.x() - diff) + i, y).getActor(), 0.75, weapon.damage()));

                }
            }
        }
    }

}
