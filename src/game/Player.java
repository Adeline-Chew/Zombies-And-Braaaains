package game;

import edu.monash.fit2099.engine.*;

import java.util.Random;

/**
 * Class representing the Player.
 */
public class Player extends Human {

	private Menu menu = new Menu();
	private Random rand = new Random();

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
	}

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		for(Item item: this.getInventory()){
			if(item instanceof Food && this.hitPoints < 100){
				this.heal(((Food) item).getFoodValue());
				this.removeItemFromInventory(item);
			}
			if(item instanceof ZombieLimbs){
				actions.add(new CraftAction((ZombieLimbs) item));
			}

		}
		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();

		return menu.showMenu(this, actions, display);
	}

	@Override
	public Weapon getWeapon(){
		boolean hit = rand.nextBoolean();
		Weapon weapon = super.getWeapon();
		if(hit){
			return weapon;
		}
		return null;
	}

}
