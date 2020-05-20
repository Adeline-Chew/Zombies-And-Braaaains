package game;

import edu.monash.fit2099.engine.*;


/**
 * Class representing an ordinary human.
 * This Human can attack Zombie, eat food, pick up item and wander around.
 * 
 * @author Adeline Chew Yao Yi & Tey Kai Ying
 *
 */
public class Human extends ZombieActor {
	private final Behaviour[] behaviours = {
			new AttackBehaviour(ZombieCapability.UNDEAD),
			new EatBehaviour(),
			new PickUpBehaviour(),
			new WanderBehaviour(),
	};

	/**
	 * The default constructor creates default Humans
	 * 
	 * @param name the human's display name
	 */
	public Human(String name) {
		super(name, 'H', 50, ZombieCapability.ALIVE);
		addCapability(ZombieCapability.WALK);
	}
	
	/**
	 * The protected constructor can be used to create subtypes
	 * of Human, such as the Player
	 * 
	 * @param name the human's display name
	 * @param displayChar character that will represent the Human in the map 
	 * @param hitPoints amount of damage that the Human can take before it dies
	 */
	protected Human(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints, ZombieCapability.ALIVE);
		addCapability(ZombieCapability.WALK);
	}

	/**
	 * If there is Zombie around Human, he will attack.
	 * If not, he will eat food if he lost some hit points and there is some foods in his inventory.
	 * Otherwise, if there is an item on the location Human is standing on, he will pick up.
	 * Else, Human will wander around.
	 *
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return Human's action for current turn.
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// FIXME humans are pretty dumb, maybe they should at least run away from zombies?

		// if this human has lower health points, it should be considered as damaged
		if(this.hitPoints < this.maxHitPoints && !this.hasCapability(ZombieCapability.DAMAGED)){
			this.addCapability(ZombieCapability.DAMAGED);
		}

		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);
			if (action != null)
				return action;
		}
		return new DoNothingAction();
	}

	/**
	 * Returns true if the Human has positive hit points.
	 * Change the capability of Human if he is dead.
	 *
	 * @return true if and only if hitPoints is positive.
	 */
	@Override
	public boolean isConscious(){
		boolean alive = super.isConscious();
		if(!alive) {
			this.removeCapability(ZombieCapability.ALIVE);
			this.addCapability(ZombieCapability.DEAD);
		}
		return alive;
	}

}
