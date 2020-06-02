package game;

import edu.monash.fit2099.engine.WeaponItem;

public class Shotgun extends WeaponItem {
    final String [] DIRECTIONS = {"North"};

    public Shotgun() {
        super("Shotgun", 'G', 20, "fires");
        this.allowableActions.add(new ShotDirectionAction("North"));
    }


}
