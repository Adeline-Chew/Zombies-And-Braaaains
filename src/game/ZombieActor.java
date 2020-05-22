package game;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

import java.util.Random;

/**
 * Base class for Actors in the Zombie World
 *
 * @author Adeline Chew Yao Yi and Tey Kai Ying
 *
 */
public abstract class ZombieActor extends Actor {
	private final Random rand = new Random();
	
	public ZombieActor(String name, char displayChar, int hitPoints, ZombieCapability team) {
		super(name, displayChar, hitPoints);
		
		addCapability(team);
		addCapability(ZombieCapability.WALK);
		addCapability(ZombieCapability.HOLD);
	}

	/**
	 * Returns a collection of the Actions that the otherActor can do to the current Actor.
	 * Add AttackAction if the otherActor is the contrary side of this actor.
	 *
	 * @param otherActor the Actor that might be performing attack
	 * @param direction  String representing the direction of the other Actor
	 * @param map        current GameMap
	 * @return A collection of Action.
	 */
	@Override
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		Actions list = super.getAllowableActions(otherActor, direction, map);
		if (otherActor.hasCapability(ZombieCapability.UNDEAD) != this.hasCapability(ZombieCapability.UNDEAD))
			list.add(new AttackAction(this));
		return list;
	}

	/**
	 * Do some damage to the Actor.
	 * @param points number of hit points to deduct.
	 * @param map Game map of this game
	 * @return Display the current Hit Points of Actor.
	 */
	@Override
	public String damage(int points, GameMap map){
		String result;
		hurt(points);
		result = this.name + " gets hurt. Hit points: " + this.hitPoints;
		return result;
	}

	/**
	 * Return random adjacent location of the actor's location.
	 * @param map Game map of this game.
	 * @return location adjacent to the actor.
	 */
	@Override
	public Location getRandomAdjacent(GameMap map){
		// Randomly get an adjacent location around Actor
		int exits = map.locationOf(this).getExits().size() - 1;
		return map.locationOf(this).getExits().get(rand.nextInt(exits)).getDestination();
	}
}
