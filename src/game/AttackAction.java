package game;

import edu.monash.fit2099.engine.*;

/**
 * Special game.Action for attacking other Actors.
 *
 * @author Adeline Chew Yao Yi & Tey Kai Ying
 */
public class AttackAction extends Action {

	/**
	 * The Actor that is to be attacked
	 */
	protected Actor target;

	/**
	 * Constructor.
	 * 
	 * @param target the Actor to attack
	 */
	public AttackAction(Actor target) {
		this.target = target;
	}

	/**
	 * For actor to attack target.
	 * If target is died in this attack, it will become a corpse and drop all of the items in inventory.
	 *
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return Result of the attack.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		Weapon weapon = actor.getWeapon();
		String result;

		// Attack the target
		try {
			result = actor + " " + weapon.verb() + " " + target + " for " + weapon.damage() + " damage.";
			result += target.damage(weapon.damage(), map);
		}
		catch (NullPointerException e){
			return actor + " misses " + target + ".";
		}


		// If target died
		if (!target.isConscious()) {
			Item corpse;
			if(target.hasCapability(ZombieCapability.DEAD)){
				corpse = new HumanCorpse(target.toString());
			}
			else {
				corpse = new PortableItem("dead " + target, '%');
			}
			map.locationOf(target).addItem(corpse);

			// Drops all of the items in target's inventory
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

