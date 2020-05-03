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
	private int noOfLimbs;
	private int noOfLegs;
	private Behaviour[] behaviours = {
			new AttackBehaviour(ZombieCapability.ALIVE),
			new HuntBehaviour(Human.class, 10),
			new WanderBehaviour()
	};
	private Random rand = new Random();

	public Zombie(String name) {
		super(name, 'Z', 100, ZombieCapability.UNDEAD);
		noOfLegs = 2;
		noOfLimbs = 2;
	}

	@Override
	public IntrinsicWeapon getIntrinsicWeapon() {
		return new IntrinsicWeapon(10, "punches");
	}

	/**
	 * If a Zombie can attack, it will.  If not, it will chase any human within 10 spaces.  
	 * If no humans are close enough it will wander randomly.
	 * 
	 * @param actions list of possible Actions
	 * @param lastAction previous Action, if it was a multiturn action
	 * @param map the map where the current Zombie is
	 * @param display the Display where the Zombie's utterances will be displayed
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);
			if (action != null)
				return action;
		}
		return new DoNothingAction();	
	}


	public String lostParts(Actor target, GameMap map) {
		String result = "";
		DropItemAction drop;
		int prob = rand.nextInt(5);

		if (noOfLimbs != 0) {
			noOfLimbs--;
			ZombieParts arm = new ZombieParts("FallenArm", 'A', 12, "knocks") ;
			drop = new DropItemAction(arm);
			drop.execute(target, map);
			result = target.toString() + " lost a limb";
		}

		if (prob < 3 && noOfLegs != 0) {
			noOfLegs--;
			ZombieParts leg = new ZombieParts("FallenLeg", 'L', 15, "strikes");
			drop = new DropItemAction(leg);
			drop.execute(target, map);
			result = target.toString() + " lost a leg";
		}

		return result;
	}

}
