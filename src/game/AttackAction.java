package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Weapon;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

	/**
	 * The Actor that is to be attacked
	 */
	protected Actor target;
	/**
	 * Random number generator
	 */
	protected Random rand = new Random();

	/**
	 * Constructor.
	 * 
	 * @param target the Actor to attack
	 */
	public AttackAction(Actor target) {
		this.target = target;
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		boolean hit = rand.nextBoolean();
		double hitRate = Math.random();
		Weapon weapon = actor.getWeapon();
		int damage = weapon.damage();

		if(hit && weapon.verb().equals("bites")){
			damage += weapon.damage() * (int) (1 - hitRate);
			if(actor instanceof Zombie)		// check if this actor is Zombie
				actor.heal(5);
		}

		if (!hit) {
			return actor + " misses " + target + ".";
		}

		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";

		target.hurt(damage);

		if(target.isConscious() && target.getDisplayChar() == 'Z'){
			result += "\n" + playerAttack(target, map);
		}

		if (!target.isConscious()) {
			Item corpse = new PortableItem("dead " + target, '%');
			map.locationOf(target).addItem(corpse);
			
			Actions dropActions = new Actions();
			for (Item item : target.getInventory())
				dropActions.add(item.getDropAction());
			for (Action drop : dropActions)		
				drop.execute(target, map);
			map.removeActor(target);	
			
			result += System.lineSeparator() + target + " is killed.";
		}

		return result;
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " attacks " + target;
	}

	public String playerAttack(Actor target, GameMap map){
		Zombie z;
		String result = "Weak damage to " + target.toString();
		boolean partsOff = rand.nextInt(4) == 0;

		if(partsOff){
			z = (Zombie) target;
			result = z.lostParts(target, map);
		}
		return result;
	}
}

