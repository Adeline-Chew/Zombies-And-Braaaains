package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

public class Crop extends Ground {
    private int turn = 0;

    public Crop() {
        super('c');
    }


     @Override
    public void tick(Location currentLocation){
        super.tick(currentLocation);

        turn++;

        if(turn >= 20){
            displayChar = 'C';
        }
     }

     public void speedGrowth(){
        turn += 10;
     }
}
