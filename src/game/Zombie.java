package game;

import edu.monash.fit2099.engine.*;

import java.util.Random;

/**
 * A Zombie.
 * 
 * This Zombie is pretty boring.  It needs to be made more interesting.
 * 
 * @author ram
 *
 */
public class Zombie extends ZombieActor {
	private final Random rand = new Random();
	private int numberOfArms, numberOfLegs, turn;

	private final Behaviour[] behaviours = {new AttackBehaviour(ZombieCapability.ALIVE),
			new PickUpBehaviour(),
			new ScreamBehaviour(),
			new HuntBehaviour(Human.class, 10),
			new WanderBehaviour(),
	};

	private Location currentLocation;

	public Zombie(String name) {
		super(name, 'Z', 100, ZombieCapability.UNDEAD);
		numberOfArms = 2;
		numberOfLegs = 2;
		addCapability(ZombieCapability.WALK);
		addCapability(ZombieCapability.HOLD);
	}

	@Override
	// 50% probability of using bite attack
	public IntrinsicWeapon getIntrinsicWeapon(){
		double probability = Math.random(), hitRate = Math.random();
		int damage = 10;
		boolean hit = rand.nextBoolean();
		IntrinsicWeapon intrinsicWeapon;
		if(probability >= 0.5){
			intrinsicWeapon = new IntrinsicWeapon(10, "punches");
			if((this.numberOfArms == 1 && !hit) || this.numberOfArms == 0){
				return null;
			}
		}
		else{
			damage =  damage + (int) (10 * (1 - hitRate));
			intrinsicWeapon = new IntrinsicWeapon(damage, "bites");
		}
		return intrinsicWeapon;
	}

	@Override
	public Weapon getWeapon() {
		Weapon weapon = getIntrinsicWeapon();
		double probability = Math.random();
		boolean hit = rand.nextBoolean();

		if(this.hasCapability(ZombieCapability.HOLD)) {
			for (Item item : inventory) {
				if (item.asWeapon() != null)
					weapon = item.asWeapon();
			}
		}

		if(weapon != null && weapon.verb().equals("bites")){
			hit = probability <= 0.4;
			if(hit)
				this.heal(5);
		}

		if(hit){
			return weapon;
		}
		return null;
	}

	/**
	 * If a Zombie can attack, it will.  If not, it will chase any human within 10 spaces.  
	 * If no humans are close enough it will wander randomly.
	 * 
	 * @param actions list of possible Actions
	 * @param lastAction previous game.Action, if it was a multiturn action
	 * @param map the map where the current Zombie is
	 * @param display the Display where the Zombie's utterances will be displayed
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {

		if(numberOfLegs == 1){
			turn++;		// move every second turn
			if(turn % 2 == 0){
				removeCapability(ZombieCapability.WALK);
			}
			else{
				addCapability(ZombieCapability.WALK);
			}
		}

		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);
			currentLocation = map.locationOf(this);
			if (action != null)
				return action;
		}
		return new DoNothingAction();	
	}

	@Override
	public String damage(int points, GameMap map){
		Location here = map.locationOf(this);
		String result;
		int probability = rand.nextInt(4);
		boolean arm = rand.nextBoolean(), knockOff = probability == 0 || probability == 1;
		result = super.damage(points, map);

		if(knockOff && arm && numberOfArms > 0){
			numberOfArms--;
			dropWeapon();
			if(numberOfArms == 0)
				this.removeCapability(ZombieCapability.HOLD);
			ZombieLimbs zombieArm = new ZombieLimbs();
			here.addItem(zombieArm);
			result += this.name + " lost an arm.";
		}

		else if(knockOff && numberOfLegs > 0) {
			numberOfLegs--;
			if (numberOfLegs == 0)
				this.removeCapability(ZombieCapability.WALK);
			ZombieLimbs zombieLeg = new ZombieLimbs();
			here.addItem(zombieLeg);
			result += this.name + " lost a leg.";
		}
		return result;
	}

	/**
	 * Drop any weapon the Zombie is holding.
	 * When Zombie lose an arm, it has 50% chance of dropping the weapon it is holding,
	 * when Zombie lose both arms, it will definitely drops any weapon it is holding.
	 */
	private void dropWeapon(){
		boolean drop = rand.nextBoolean() && this.numberOfArms == 1;

		if((drop || this.numberOfArms == 0) && !this.getInventory().isEmpty()){
			this.removeItemFromInventory((WeaponItem) this.getWeapon());
		}
	}
}
