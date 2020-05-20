package game;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

import java.util.Random;

/**
 * Base class for Actors in the Zombie World
 * @author ram
 *
 */
public abstract class ZombieActor extends Actor {
	private final Random rand = new Random();
	
	public ZombieActor(String name, char displayChar, int hitPoints, ZombieCapability team) {
		super(name, displayChar, hitPoints);
		
		addCapability(team);
	}
	
	@Override
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		Actions list = super.getAllowableActions(otherActor, direction, map);
		if (otherActor.hasCapability(ZombieCapability.UNDEAD) != this.hasCapability(ZombieCapability.UNDEAD))
			list.add(new AttackAction(this));
		return list;
	}

	@Override
	public String damage(int points, GameMap map){
		String result;
		hurt(points);
		result = this.name + " lost " + points + " hit points.";
		return result;
	}

	@Override
	public Location getRandomAdjacent(GameMap map){
		// Randomly get an adjacent location around Actor
		int exits = map.locationOf(this).getExits().size() - 1;
		return map.locationOf(this).getExits().get(rand.nextInt(exits)).getDestination();
	}
}
