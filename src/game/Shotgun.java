package game;

import edu.monash.fit2099.engine.WeaponItem;

public class Shotgun extends WeaponItem {
    final String [] DIRECTIONS = {"North", "North-East", "East", "South-East", "South", "South-West", "West", "North-West"};

    public Shotgun() {
        super("Shotgun", 'G', 20, "fires");
        addCapability(ItemCapability.SHOTGUN);
        for(String direction : DIRECTIONS)
            this.allowableActions.add(new ShotDirectionAction(direction));
    }

}
