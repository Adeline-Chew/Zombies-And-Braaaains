package game;

import edu.monash.fit2099.engine.*;

import java.util.Random;

/**
 * A Zombie.
 * 
 * This Zombie has the abilities to attack human, pick up weapon, scream, hunt for human, and wander around.
 * 
 * @author Adeline Chew Yao Yi, Tey Kai Ying
 *
 */
public class Zombie extends ZombieActor {
	private final Random rand = new Random();
	private int numberOfArms, numberOfLegs, turn;

	private final Behaviour[] behaviours = {new PickUpBehaviour(),
			new AttackBehaviour(ZombieCapability.ALIVE),
			new ScreamBehaviour(),
			new HuntBehaviour(Human.class, 10),
			new WanderBehaviour(),
	};

	/**
	 * Constructor. Creates a multi-behaviours Zombie.
	 * @param name Zombie's display name.
	 */
	public Zombie(String name) {
		super(name, 'Z', 100, ZombieCapability.UNDEAD);
		numberOfArms = 2;
		numberOfLegs = 2;
	}

	/**
	 * If a Zombie can attack, it will.
	 * If there is no target to attack and there is a weapon at Zombie's location, Zombie will pick it up.
	 * If not, it will chase any human within 10 spaces.
	 * If no humans are close enough it will wander randomly.
	 *
	 * If Zombie loses one leg and there is no target to attack or weapon to pick, Zombie will move every two turns.
	 * Zombie will lost walking ability if it lost both legs.
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
			if (action != null)
				return action;
		}
		return new DoNothingAction();
	}

	/**
	 * Zombie is able to bite and punch, when Zombie has both arms, the chance of choosing bite attack is 50%.
	 * If Zombie loses one arm, chance of biting is 75% and punching is 25%.
	 * If Zombie loses both arms, it can only bite.
	 * Bite attack have a lower chance of hitting but do more damage.
	 *
	 * @return Zombie's intrinsic weapon
	 */
	@Override
	public IntrinsicWeapon getIntrinsicWeapon(){
		// Lower hitRate, higher bite's damage
		double probability = Math.random(), hitRate = Math.random();
		int bitesDamage = 10 + (int) (10 * (1 - hitRate));
		boolean bite = rand.nextBoolean();
		IntrinsicWeapon punches = new IntrinsicWeapon(10, "punches"),
						bites = new IntrinsicWeapon(bitesDamage, "bites");

		if(numberOfArms == 2){
			// 50% probability of choosing bite and punch
			if(bite){
				return bites;
			}
			return punches;
		}

		else if(numberOfArms == 1){
			// 75% probability of choosing bite
			if(probability <= 0.75){
				return bites;
			}
			return punches;
		}

		else{
			return bites;
		}
	}

	/**
	 * Get the weapon for the Zombie to attack.
	 * If the Zombie is not carrying any weapon, it will return Zombie's natural fighting equipment.
	 *
	 * @return Zombie's weapon
	 */
	@Override
	public Weapon getWeapon() {
		Weapon weapon = getIntrinsicWeapon();
		double probability = Math.random();	// Probability of hitting
		boolean hit = rand.nextBoolean();

		if(this.hasCapability(ZombieCapability.HOLD)) {
			for (Item item : getInventory()) {
				if (item.asWeapon() != null)
					// Cast the Item to Weapon
					weapon = item.asWeapon();
			}
		}

		if(weapon != null && weapon.verb().equals("bites")){
			// Probability of biting is lower (40%)
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
	 * Do some damage to Zombie. When Zombie gets hurt, attacker has 25% of chance to knock off Zombie's limb.
	 * The limb will drop at Zombie's adjacent location.
	 * Remove Zombie's hold capability if it loses both arms.
	 * Remove Zombie's walk capability if it loses both legs.
	 *
	 * @param points number of hit points to deduct
	 * @param map Game map of this game
	 * @return Display the message if Zombie lost a limb.
	 */
	@Override
	public String damage(int points, GameMap map){
		Location adjacent = getRandomAdjacent(map);
		String result;
		int probability = rand.nextInt(4);	// 25% probability
		boolean arm = rand.nextBoolean(), knockOff = (probability == 0 || probability == 1);

		result = super.damage(points, map);

		if(knockOff && arm && numberOfArms > 0){
			numberOfArms--;
			dropWeapon();
			if(numberOfArms == 0)
				this.removeCapability(ZombieCapability.HOLD);	// Zombie cannot hold weapon anymore
			ZombieLimbs zombieArm = new ZombieLimbs(ZombieLimbs.Limb.ARM);
			adjacent.addItem(zombieArm);
			result += "\n" + this.name + " lost an arm.";
		}

		else if(knockOff && numberOfLegs > 0) {
			numberOfLegs--;
			if (numberOfLegs == 0)
				this.removeCapability(ZombieCapability.WALK);	// Zombie cannot walk anymore
			ZombieLimbs zombieLeg = new ZombieLimbs(ZombieLimbs.Limb.LEG);
			adjacent.addItem(zombieLeg);
			result += "\n" + this.name + " lost a leg.";
		}
		return result;
	}

	/**
	 * Drop any weapon the Zombie is holding.
	 * When Zombie loses an arm, it has 50% chance of dropping the weapon it is holding,
	 * when Zombie loses both arms, it will definitely drops any weapon it is holding.
	 */
	private void dropWeapon(){
		boolean drop = rand.nextBoolean() && this.numberOfArms == 1;

		if((drop || this.numberOfArms == 0) && !this.getInventory().isEmpty()){
			for(Item item: getInventory()){
				if(item.hasCapability(ItemCapability.AS_WEAPON)){
					removeItemFromInventory(item);
					break;
				}
			}
		}
	}
}
