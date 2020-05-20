package game;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * A weapon item crafted from Zombie's arms
 *
 * @author Adeline Chew Yao Yi and Tey Kai Ying
 */

public class ZombieClub extends WeaponItem {

    /**
     * Constructor. A zombie club has capability AS_WEAPON
     */
    public ZombieClub(){
        super("Zombie Club", 'C', 22, "smacks");
        addCapability(ItemCapability.AS_WEAPON);
    }

}
