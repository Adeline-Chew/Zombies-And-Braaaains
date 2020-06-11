package game;

import edu.monash.fit2099.engine.*;

public class ChooseTargetAction extends Action {
    Actor target;
    SniperRifle sniper;
    AimAction aim;
    Menu menu = new Menu();
    Display display;

    public ChooseTargetAction(Actor actor, SniperRifle weapon, Display display){
        this.target = actor;
        this.sniper = weapon;
        this.aim = new AimAction(target);
        this.display = display;
    }

    /**
     * Perform the Action.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Actions actions = new Actions();
        String result;
        if(this.aim.getConcentration() >= 0 && this.aim.getConcentration() < 2)
            actions.add(aim);
        actions.add(new RangedAttackAction(sniper, target, getShootProbability(), sniper.getSniperDamage().get(getShootProbability())));
        Action action = menu.showMenu(actor, actions, display);
        result = action.execute(actor, map);
        if(action instanceof RangedAttackAction) {
            aim.resetConcentration();
            sniper.shoot();
        }
        Player.concentrateAction(this, aim.getConcentration());

        return result;
    }

    private double getShootProbability(){
        double prob = 1;
        int concentration = this.aim.getConcentration();
        if(concentration == 0)
            prob = 0.75;
        else if(concentration == 1)
            prob = 0.90;
        return prob;
    }

    public boolean lastActionIsAim(Action lastAction){
        boolean retVal = false;
        if(lastAction instanceof ChooseWeaponAction && ((ChooseWeaponAction) lastAction).getWeapon().equals(sniper)){
            retVal = true;
        }
        return retVal || lastAction instanceof ChooseTargetAction;
    }
    /**
     * Returns a descriptive string
     *
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " shoots " + target;
    }
}
