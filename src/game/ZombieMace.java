package game;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * A weapon item crafted from Zombie's legs
 */

public class ZombieMace extends WeaponItem {
    /**
     * Constructor. A zombie mace has capability AS_WEAPON
     *
     */
    public ZombieMace(){
        super("Zombie Mace", 'M', 25, "smacks");
        addCapability(ItemCapability.AS_WEAPON);
    }
}
