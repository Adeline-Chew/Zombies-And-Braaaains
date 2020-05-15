package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;

/**
 * Class representing an ordinary human.
 * 
 * 
 * @author ram
 *
 */
public class Human extends ZombieActor {
	private ArrayList <Behaviour> behaviours = new ArrayList<>();

	/**
	 * The default constructor creates default Humans
	 * 
	 * @param name the human's display name
	 */
	public Human(String name) {
		super(name, 'H', 50, ZombieCapability.ALIVE);
		behaviours.add(new PickUpBehaviour());
		behaviours.add(new WanderBehaviour());
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
	}

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// FIXME humans are pretty dumb, maybe they should at least run away from zombies?
		if(this.hitPoints < this.maxHitPoints && this.behaviours.size() == 2){
			behaviours.add(0, new EatBehaviour());
		}

		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);
			if (action != null)
				return action;
		}
		return new DoNothingAction();
	}

}
