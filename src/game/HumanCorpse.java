package game;

import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

import java.util.Random;

public class HumanCorpse extends Item {
    private int turn;
    private Random rand = new Random();

    public HumanCorpse(String name){
        super(name, '%', false);
    }

    @Override
    public void tick(Location currentLocation) {
        boolean revive = rand.nextBoolean();
        super.tick(currentLocation);

        turn++;

        if((turn > 5 && turn < 10 && revive) || turn == 10){
            corpseRevive(currentLocation);
        }
    }

    public void corpseRevive(Location here){
        Display display = new Display();
        String retVal;

        // Create a new zombie
        here.addActor(new Zombie("Zombie " + this.name));

        // Display message
        retVal = this.name + " has turned into Zombie.\n" ;

        // Remove human corpse
        here.removeItem(this);

        display.println(retVal);
    }
}
