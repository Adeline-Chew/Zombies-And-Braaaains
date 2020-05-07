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
		boolean hit = rand.nextBoolean();
		double hitRate = Math.random();
		Weapon weapon = actor.getWeapon();
		int damage = weapon.damage();

		if(actor instanceof Zombie) {
			int probability = rand.nextInt(5);
			hit = probability == 0 || probability == 1;	// biting probability is less than punching (40%)

			if (weapon.verb().equals("bites")) {
				damage =  damage + (int) (10 * (1 - hitRate));
				actor.heal(5);
			}
			else if(((Zombie) actor).getNumberOfArms() == 1){
				hit = rand.nextBoolean();	// probability of punching is halved
				boolean drop = rand.nextBoolean();
				if(!actor.getInventory().isEmpty() && drop){
					actor.removeItemFromInventory((WeaponItem) actor.getWeapon());
				}
			}
			else if(((Zombie) actor).getNumberOfArms() == 0){
				hit = false; // cannot punch anymore
				actor.removeItemFromInventory((WeaponItem) actor.getWeapon());
			}
		}


		if (!hit) {	// player and zombie
			return actor + " misses " + target + ".";
		}

		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";

		target.hurt(damage);

		if(target.isConscious() && target instanceof Zombie){
			result += "\n" + playerAttack(target, map);
		}

		if (!target.isConscious()) {
			Item corpse;
			if(target instanceof Human){
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

	public String playerAttack(Actor target, GameMap map){
		Zombie z;
		String result = "Weak damage to " + target.toString();
		boolean partsOff = rand.nextInt(4) == 0;

		if(partsOff){
			z = (Zombie) target;
			result = z.lostParts(target.toString(), map);
		}
		return result;
	}
}

