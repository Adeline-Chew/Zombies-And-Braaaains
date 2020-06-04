package game;

import edu.monash.fit2099.engine.*;

public class ShotgunAction extends Action {
    private Menu menu = new Menu();
    private WeaponItem shotgun;
    private Display display;

    public ShotgunAction(WeaponItem gun, Display display){
        shotgun = gun;
        this.display = display;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        Actions actions = new Actions();
        actions.add(shotgun.getAllowableActions());
        Action action = menu.showMenu(actor, actions, display);
        rangedDamage(actor, map);
        return action.menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " use shotgun";
    }

    public void rangedDamage(Actor actor, GameMap map){
        int damage = shotgun.damage();
        Location here = map.locationOf(actor);
        NumberRange xs, ys;

        xs = new NumberRange(here.x() + 1, 3);
        ys = new NumberRange(here.y() + 1, 3);

        for(int x : xs){
            for(int y : ys){
                if(map.at(x,y).containsAnActor() && Math.random() <= 0.75){
                    map.at(x,y).getActor().hurt(damage);
                    display.println(map.at(x,y).getActor() + " got shot for " + shotgun.damage() + " damage.");
                }
            }
        }

    }
}
