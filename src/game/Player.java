package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;

/**
 * Class representing the Player.
 */
public class Player extends Human {

	private Menu menu = new Menu();
	private static int concentration;
	private static ChooseTargetAction chooseTargetAction;

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) throws Exception {
		super(name, displayChar, hitPoints);
		if(hitPoints < 0){
			throw new Exception("Player hitpoints must be positive");
		}
	}

	/**
	 * Select and return an action for current turn.
	 * Player can harvest if there is ripen crop nearby. If not, Player can eat
	 * if damaged. Player can also craft a new weapon if it is holding craftable item.
	 * Player can use the ranged weapon to attack if there is one in the inventory.
	 * Player can wander around too.
	 *
	 * @param actions collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action
	 * @param map the map containing the Actor
	 * @param display the I/O object to which messages may be written
	 * @return the Action to be performed
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {

		// for Player to quit game
		actions.add(new QuitGameAction());

		// this make sure Player only do harvest action in a valid situation
		Exit here = new Exit("Stay" , map.locationOf(this), "z");
		if(map.locationOf(this).getGround().hasCapability(Crop.CropCapability.RIPEN)){
			actions.add(new HarvestAction(here, "Player's food"));
		}

		for(Item item: this.getInventory()){
			if(item.hasCapability(ItemCapability.EDIBLE) && this.hitPoints < this.maxHitPoints)
				actions.add(new EatAction((Food)item));			// Player eats only when it has lower hit points

			if(item.hasCapability(ItemCapability.CRAFTABLE))
				actions.add(new CraftAction(item));				// Player crafts only when it has craftable item

			if(item.hasCapability(ItemCapability.RANGED_WEAPON)) {
				actions.add(new ChooseWeaponAction(display, (RangedWeapon) item));     // for Player to use shotgun or sniper
			}
		}

		// Allows player to continue aim at the target
		if(concentration > 0){
			if(chooseTargetAction.lastActionIsAim(lastAction))
				actions.add(chooseTargetAction);
			else
				concentration = 0;	// Reset the concentration
		}

		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();

		return menu.showMenu(this, actions, display);
	}

	/**
	 * Get the chooseTargetAction executed last turn as class variable.
	 */
	public static void concentrateAction(ChooseTargetAction action, int focusValue){
		concentration = focusValue;
		chooseTargetAction = action;
	}
	/**
	 * Get the weapon for Player to use. Weapon will be chosen with highest damage.
	 * If the Player is not carrying any weapon, it will return Player's natural fighting equipment.
	 *
	 * @return the Player's weapon
	 */
	@Override
	public Weapon getWeapon(){
		boolean hit = Math.random() <= 0.75 ;
		Weapon chosenWeapon = super.getWeapon();
		int damage = 0;
		ArrayList<Weapon> weapons = new ArrayList<>();

		// make a list of weapons available
		for(Item item: this.inventory){
			if(item.asWeapon() != null){
				weapons.add(item.asWeapon());
			}
		}

		// choose the weapon with greatest damage
		for(Weapon weapon : weapons){
			if(weapon.damage() > damage){
				chosenWeapon = weapon;
				damage = weapon.damage();
			}
		}

		// Player has 75% chance to hit the target with weapon
		if(hit){
			return chosenWeapon;
		}
		return null;
	}

	/**
	 * Do some damage to the current Actor.
	 * <p>
	 * If the Actor's hitpoints go down to zero, it will be knocked out.
	 *
	 * @param points number of hitpoints to deduct.
	 */
	@Override
	public void hurt(int points) {
		super.hurt(points);
		concentration = 0;
	}

	/**
	 * Add points to the current Actor's hitpoint total.
	 * <p>
	 * This cannot take the hitpoints over the Actor's maximum. If there is an
	 * overflow, hitpoints are silently capped at the maximum.
	 * <p>
	 * Does not check for consciousness: unconscious Actors can still be healed
	 * if the game client allows.
	 *
	 * @param points number of hitpoints to add.
	 */
	@Override
	public void heal(int points) {
		Display display = new Display();
		super.heal(points);
		display.println("Current hit points: " + hitPoints);
	}
}
