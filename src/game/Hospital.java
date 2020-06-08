package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * A hospital building.
 * It will create a new Doctor every 10 turns, maximum of 10 doctors will be created for each hospital.
 * Heal Humans or Player if they are standing on the hospital.
 *
 * @author Adeline Chew Yao Yi, Tey Kai Ying
 */

public class Hospital extends Building{
    private static final ArrayList<String> doctorsName = new ArrayList<>();
    private final Random rand = new Random();
    private final Display display = new Display();
    private int turn;

    /**
     * Constructor.
     *
     * @param displayChar character to display for this type of terrain
     */
    public Hospital(char displayChar) {
        super(displayChar);
        String[] name = {"Doctor A", "Doctor B", "Doctor C", "Doctor D", "Doctor E",
                "Doctor F", "Doctor G", "Doctor H", "Doctor I", "Doctor J", "Doctor K",
                "Doctor L", "Doctor M", "Doctor N", "Doctor O", "Doctor P", "Doctor Q",
                "Doctor R", "Doctor S", "Doctor T", };
        doctorsName.addAll(Arrays.asList(name));
    }

    @Override
    public void changeGroundStatus() {

    }

    /**
     * Ground can also experience the joy of time.
     * Hospital creates a new Doctor every 10 turns, maximum 10 doctors will be created.
     * Heal human or player if they are standing on it.
     *
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        // Creates doctor
        int x, y;
        turn++;
        if(turn % 10 == 0 && turn <= 100){
            do {
                x = rand.nextInt(location.map().getXRange().max());
                y = rand.nextInt(location.map().getYRange().max());
            }
            while (location.map().at(x, y).containsAnActor());
            location.map().at(x, y).addActor(new Doctor(getName()));
            display.println("Hospital trained a new Doctor.");
        }

        // Heals actor
        if(location.containsAnActor() && location.getActor().hasCapability(ZombieCapability.ALIVE)){
            Actor actor = location.getActor();
            actor.heal(50);
            display.println(actor + " gets heal in hospital.");
        }
    }

    /**
     * @return a new name for a newly created Doctor.
     */
    private String getName(){
        return doctorsName.remove(0);
    }
}
