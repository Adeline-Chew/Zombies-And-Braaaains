package game;

import edu.monash.fit2099.engine.*;

import static java.lang.Math.abs;

public class ShotDirectionAction extends Action  {
    protected String direction;
    protected RangedWeapon weapon;
    final String[] DIAGONALDIRECT = {"North-East", "South-East", "South-West", "North-West"};

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
        NumberRange xs, ys, ranges;
        Actions actions = new Actions();
        String result = "";

        for(String direct : DIAGONALDIRECT){
            if(direct.equals(direction)){

                xs = setXRange(here, direction);
                ys = setYRange(here, direction);


                for(int x : xs){
                    for(int y : ys){
                        if(x != here.x() || y != here.y()){
                            if(map.at(x,y).containsAnActor()){
                                actions.add(new RangedAttackAction(weapon, map.at(x, y).getActor(), 0.75, weapon.damage()));
                            }
                        }
                    }
                }
            }

            else{

                ranges = setRange(here, direction);

                if(direction.equals("East") || direction.equals("West")){
                    for(int x : ranges){
                        int diff = abs(here.x() - x);
                        for(int i = 0; i <= diff; i++){
                            if(map.at(x, here.y() + diff - i).containsAnActor()){
                                actions.add(new RangedAttackAction(weapon, map.at(x, here.y() + diff - i).getActor(), 0.75, weapon.damage()));
                            }
                            if(map.at(x, here.y() - diff + i).containsAnActor() && i != diff){
                                actions.add(new RangedAttackAction(weapon, map.at(x, here.y() - diff + i).getActor(), 0.75, weapon.damage()));
                            }
                        }
                    }
                }

                else if(direction.equals("South") ||  direction.equals("North")){
                    for(int y : ranges){
                        int diff = abs(here.y() - y);
                        for(int i = 0; i <= diff; i++){
                            if(map.at(here.x() + diff - i, y).containsAnActor()){
                                actions.add(new RangedAttackAction(weapon, map.at(here.x() + diff - i, y).getActor(), 0.75, weapon.damage()));
                            }
                            if(map.at(here.x() - diff + i, y).containsAnActor() && i != diff){
                                actions.add(new RangedAttackAction(weapon, map.at(here.x() - diff + i, y).getActor(), 0.75, weapon.damage()));
                            }
                        }
                    }
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

}
