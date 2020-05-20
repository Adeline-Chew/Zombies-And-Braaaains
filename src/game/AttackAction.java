package game;

import java.util.Random;

import edu.monash.fit2099.engine.*;

/**
 * Special game.Action for attacking other Actors.
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
		int damage;
		Weapon weapon = actor.getWeapon();

		if(weapon == null){
			return actor + " misses " + target + ".";
		}

		damage = weapon.damage();
		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";
		target.damage(damage, map);


		if (!target.isConscious()) {
			Item corpse;
			if(target.hasCapability(ZombieCapability.DEAD)){
				corpse = new HumanCorpse(target.toString());
			}
			else {
				corpse = new PortableItem("dead " + target, '%');
			}
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

}

