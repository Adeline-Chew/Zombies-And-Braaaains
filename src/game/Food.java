package game;

import edu.monash.fit2099.engine.Item;
import java.util.Random;

/**
 * An edible item which can heal player and damaged humans
 *
 * @author Tey Kai Ying
 */

public class Food extends Item {
    private int foodValue;
    private Random rand = new Random();

    /**
     * Constructor. The food's value is generated randomly upon construction
     *
     * @param name the Food's display name
     * @param portable a boolean indicating whether the item can be carried
     */
    public Food(String name, boolean portable) {
        super(name, 'E', portable);
        int randomInt = rand.nextInt(15);
        if(randomInt != 0){
            foodValue = randomInt;
        }
        else{
            foodValue = 20;
        }
    }

    /**
     * Accessor for food value
     *
     * @return the Food's value
     */
    public int getFoodValue() {
        return foodValue;
    }
}
