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
        super("Dead " + name, '!');
        this.name = name;
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
        boolean revive = rand.nextBoolean(), success = false;

        turn++;

        if((turn > 5 && turn < 10 && revive) || turn == 10) {
            while (!success) {
                try {
                    corpseRevive(currentLocation);
                    success = true;
                    currentLocation.removeItem(this);
                } catch (Exception e) {
                    // Get adjacent location
                    currentLocation = currentLocation.getExits().get(0).getDestination();
                }
            }
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
        boolean revive = rand.nextBoolean(), success = false;

        turn++;

        if((turn > 5 && turn < 10 && revive) || turn == 10) {
            while (!success) {
                try {
                    corpseRevive(actor.getRandomAdjacent(currentLocation.map()));
                    success = true;
                    actor.removeItemFromInventory(this);
                } catch (Exception e) {
                    actor.getRandomAdjacent(currentLocation.map());
                }
            }
        }
    }

    /**
     * Turn the HumanCorpse into Zombie at a specified location.
     * @param here Location of where the HumanCorpse turns into Zombie.
     */
    private void corpseRevive(Location here) throws Exception {
        Display display = new Display();
        String retVal;

        // Create a new zombie
        try {
            here.addActor(new Zombie("Zombie " + this.name));
        }
        catch (IllegalArgumentException e){
            throw new Exception();
        }

        // Display message
        retVal = this.name + " has turned into Zombie.\n" ;

        display.println(retVal);
    }
}
