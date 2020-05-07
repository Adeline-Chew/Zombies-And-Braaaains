package game;

import edu.monash.fit2099.engine.Item;
import java.util.Random;

public class Food extends Item {
    private int foodValue;
    private Random rand = new Random();

    public Food(String name, boolean portable) {
        super(name, 'E', portable);
        int randomInt = rand.nextInt(15);
        if(randomInt != 0){
            foodValue = randomInt;
        }
    }

    public int getFoodValue() {
        return foodValue;
    }
}
