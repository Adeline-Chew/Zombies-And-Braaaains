package game;

import java.util.ArrayList;

public class AmmunitionBox extends PortableItem {
    ArrayList<Ammunition> bulletBox = new ArrayList<>();

    public AmmunitionBox() {
        super("Box of bullets", 'A');
        for(int i = 0; i < 5; i++){
            bulletBox.add(new Ammunition());
        }

        addCapability(ItemCapability.BULLET);
    }

    public ArrayList<Ammunition> getAmmunition(){return bulletBox;}
}
