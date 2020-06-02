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
        return action.menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " use shotgun";
    }
}
