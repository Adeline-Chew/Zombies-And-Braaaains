package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * A crop sowed by Farmer that will grow to become food
 *
 * @author Tey Kai Ying
 */

public class Crop extends Ground {
    private int turn = 0;

    /**
     * Constructor. Crop has capability UNRIPE upon construction.
     *
     */
    public Crop() {
        super('c');
        this.addCapability(CropCapability.UNRIPE);
    }

    /**
     * Crop will grow and ripe after 20 turns. A capability RIPEN will be added.
     *
     * @param currentLocation location of the ground
     */
     @Override
    public void tick(Location currentLocation){
        super.tick(currentLocation);

        turn++;

        if(turn == 20){
            displayChar = 'C';
            this.addCapability(CropCapability.RIPEN);

        }

     }

    /**
     * Decrease the turns of the crop to ripe by 10 turns.
     *
     */
     public void speedGrowth(){
        if(turn + 10 >= 20){
            turn = 20;
        }
        else{
            turn += 10;
        }
     }

     enum CropCapability{
        UNRIPE, RIPEN
     }
}
