package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.Location;

import java.util.Random;

public class HumanCorpse extends PortableItem {
    private int turn;
    private Random rand = new Random();

    public HumanCorpse(String name){
        super(name, '%');
    }

    @Override
    public void tick(Location currentLocation) {
        boolean revive = rand.nextBoolean();
        super.tick(currentLocation);

        turn++;

        if((turn > 5 && turn < 10 && revive) || turn == 10) {
            // If the HumanCorpse is in Actor's inventory
            if (currentLocation.containsAnActor() && currentLocation.getActor().getInventory().contains(this)) {
                Actor actor = currentLocation.getActor();
                corpseRevive(actor.getRandomAdjacent(currentLocation.map()));
                actor.removeItemFromInventory(this);
            }
            // If the HumanCorpse is stay on the ground
            else {
                corpseRevive(currentLocation);
                currentLocation.removeItem(this);
            }
        }

    }

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
