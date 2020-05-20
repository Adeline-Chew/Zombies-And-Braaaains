package game;

import edu.monash.fit2099.engine.Item;
import java.util.Random;

/**
 * An edible item which can heal player and damaged humans
 *
 * @author Adeline Chew Yao Yi and Tey Kai Ying
 */

public class Food extends Item {
    private int foodValue;
    private Random rand = new Random();

    /**
     * Constructor. The food's value is generated randomly upon construction
     * and it has capability EDIBLE
     *
     * @param name the Food's display name
     *
     */
    public Food(String name) {
        super(name, 'E', true);
        this.addCapability(ItemCapability.EDIBLE);
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
