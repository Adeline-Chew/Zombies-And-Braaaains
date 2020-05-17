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
	private Random rand = new Random();
	private int numberOfArms, numberOfLegs, turn;
	private Behaviour[] behaviours = {
			new AttackBehaviour(ZombieCapability.ALIVE),
			new PickUpBehaviour(),
			new HuntBehaviour(Human.class, 10),
			new ScreamBehaviour(),
			new WanderBehaviour()
	};

	public Zombie(String name) {
		super(name, 'Z', 100, ZombieCapability.UNDEAD);
		numberOfArms = 2;
		numberOfLegs = 2;
	}

	public int getNumberOfArms() {
		return numberOfArms;
	}

	public int getNumberOfLegs() {
		return numberOfLegs;
	}

	public int getTurn(){
		return turn;
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
		for (Item item : inventory) {
			if (item.asWeapon() != null)
				weapon = item.asWeapon();
		}

		if(weapon.verb().equals("bites")){
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

		if(this.numberOfLegs == 1){
			turn++;		// move every second turn
		}
		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);
			if (action != null)
				return action;
		}
		return new DoNothingAction();	
	}

	public String lostParts(String name, GameMap map){
		String result = "";
		boolean arm = rand.nextBoolean();
		Location here = map.locationOf(this);

		if(arm && numberOfArms > 2){
			numberOfArms--;
			ZombieLimbs zombieArm = new ZombieLimbs();
			here.addItem(zombieArm);
			result = name + " lost a limb";
		}

		else if(numberOfLegs > 2){
			numberOfLegs--;
			ZombieLimbs zombieLeg = new ZombieLimbs();
			here.addItem(zombieLeg);
			result = name + " lost a leg.";
		}
	}

}
