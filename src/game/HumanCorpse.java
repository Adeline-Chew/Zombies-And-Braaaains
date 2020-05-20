package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.Location;

import java.util.Random;

/**
 * A Human corpse.
 *
 * Human corpse will return from dead within 5 - 10 turns.
 *
 * @author Adeline Chew Yao Yi, Tey Kai Ying
 *
 */

public class HumanCorpse extends PortableItem {
    private int turn = 0;
    private Random rand = new Random();

    /**
     * A Constructor creates human corpse.
     * @param name Display name of the dead Human.
     */
    public HumanCorpse(String name){
        super(name, '%');
    }

    /**
     * Count the turn of the game.
     * After 5 turns, the corpse has 50% of chance to turn into Zombie.
     * In 10th turn, the corpse must be turned into Zombie.
     *
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {
        boolean revive = rand.nextBoolean();

        turn++;

        if((turn > 5 && turn < 10 && revive) || turn == 10) {
            corpseRevive(currentLocation);
            currentLocation.removeItem(this);
        }
    }

    /**
     * Count the turn of the game when the corpse is carrying by an actor.
     * After 5 turns, the corpse has 50% of chance to turn into Zombie.
     * In 10th turn, the corpse must be turned into Zombie.
     *
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor){
        boolean revive = rand.nextBoolean();

        turn++;

        if((turn > 5 && turn < 10 && revive) || turn == 10) {
            corpseRevive(actor.getRandomAdjacent(currentLocation.map()));
            actor.removeItemFromInventory(this);
        }
    }

    /**
     * Turn the HumanCorpse into Zombie at a specified location.
     * @param here Location of where the HumanCorpse turns into Zombie.
     */
    private void corpseRevive(Location here){
        Display display = new Display();
        String retVal;

        // Create a new zombie
        here.addActor(new Zombie("Zombie " + this.name));

        // Display message
        retVal = this.name + " has turned into Zombie.\n" ;

        display.println(retVal);
    }
}
