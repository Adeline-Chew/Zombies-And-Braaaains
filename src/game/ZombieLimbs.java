package game;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * A primary weapon item from Zombie's limbs
 *
 */

public class ZombieLimbs extends WeaponItem {

    /**
     * Constructor. A zombie limb has capability AS_WEAPON and CRAFTABLE
     *
     * @param limb a capability to decide whether it is arm or leg
     */
    public ZombieLimbs(Limb limb){
        super("Zombie Limb", 'L', 15, "smacks");
        addCapability(ItemCapability.AS_WEAPON);
        addCapability(ItemCapability.CRAFTABLE);
        addCapability(limb);
    }

    enum Limb{
        LEG,
        ARM
    }
}
