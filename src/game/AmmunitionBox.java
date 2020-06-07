package game;

import java.util.ArrayList;

public class AmmunitionBox extends PortableItem {
    ArrayList<Ammunition> bulletBox = new ArrayList<>();

    public AmmunitionBox(String name, String bulletType) {
        super(name, 'A');
        if(bulletType.equals("s")){
            for(int i = 0; i < 5; i++){
                bulletBox.add(new SniperAmmunition());
            }
        }
        else if(bulletType.equals("g")){
            for(int i = 0; i < 5; i++){
                bulletBox.add(new ShotgunAmmunition());
            }
        }
        addCapability(ItemCapability.BULLET);
    }

    public ArrayList<Ammunition> getAmmunition(){return bulletBox;}
}
