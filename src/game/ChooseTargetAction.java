package game;

import edu.monash.fit2099.engine.*;

/**
 * An action class allows Player to choose and lock at a target within the range.
 */
public class ChooseTargetAction extends Action {
    private Actor target;
    private SniperRifle sniper;
    private AimAction aim;
    private Menu menu = new Menu();
    private Display display;

    /**
     * Constructor.
     * @param actor Target.
     * @param weapon Ranged weapon to be used.
     * @param display Display.
     */
    public ChooseTargetAction(Actor actor, SniperRifle weapon, Display display){
        this.target = actor;
        this.sniper = weapon;
        this.aim = new AimAction(target);
        this.display = display;
    }

    /**
     * Add AimAction into Actions if the concentration is less than 2, and add RangedAttackAction into Actions.
     * Use showMenu to present a sub menu allows player to choose which action to execute.
     * Reset the concentration if ranged attack is executed.
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

    /**
     * Internal method to get the probability based on the concentration.
     * @return Probability to attack the target.
     */
    private double getShootProbability(){
        double prob = 1;
        int concentration = this.aim.getConcentration();
        if(concentration == 0)
            prob = 0.75;
        else if(concentration == 1)
            prob = 0.90;
        return prob;
    }

    /**
     * Return true if the lastAction is instance of ChooseWeaponAction or this class.
     * @param lastAction Player's action taken in last turn.
     */
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
