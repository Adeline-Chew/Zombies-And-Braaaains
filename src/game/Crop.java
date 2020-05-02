package game;

import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

public class Crop extends Item {
    private int foodValue;
    private int turn = 0;

    public Crop(String name, boolean portable, int value) {
        super(name, 'c', portable);
        foodValue = value;
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
