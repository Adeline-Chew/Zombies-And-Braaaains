package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.Location;

import java.util.Random;

public class HumanCorpse extends PortableItem {
    private int turn = 0;
    private Random rand = new Random();

    public HumanCorpse(String name){
        super(name, '%');
    }

    @Override
    public void tick(Location currentLocation) {
        boolean revive = rand.nextBoolean();

        turn++;

        if((turn > 5 && turn < 10 && revive) || turn == 10) {
            corpseRevive(currentLocation);
            currentLocation.removeItem(this);
        }

    }

    @Override
    public void tick(Location currentLocation, Actor actor){
        boolean revive = rand.nextBoolean();

        turn++;

        if((turn > 5 && turn < 10 && revive) || turn == 10) {
            corpseRevive(actor.getRandomAdjacent(currentLocation.map()));
            actor.removeItemFromInventory(this);
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
