package game;

import java.util.ArrayList;

/**
 * Portable item contains an amount of ammunition.
 *
 */

public class AmmunitionBox extends PortableItem {
    private ArrayList<Ammunition> bulletBox = new ArrayList<>();

    /**
     * Constructor. 5 Ammunition are added.
     */
    public AmmunitionBox() {
        super("a box of bullets", 'A');
        for(int i = 0; i < 5; i++){
            bulletBox.add(new Ammunition());
        }

        addCapability(ItemCapability.BULLET);
    }

    /**
     * Accessor for the bullet box.
     *
     * @return an ArrayList of ammunition
     */
    public ArrayList<Ammunition> getAmmunition(){return bulletBox;}
}
