package game;

import edu.monash.fit2099.engine.WeaponItem;

public class ZombieLimbs extends WeaponItem {
    Limb parts;

    public ZombieLimbs(Limb limb){
        super("Zombie Limb", 'L', 15, "smacks");
        addCapability(ItemCapability.AS_WEAPON);
        addCapability(ItemCapability.CRAFTABLE);
        this.parts = limb;
    }

    enum Limb{
        LEG,
        ARM
    }
}
