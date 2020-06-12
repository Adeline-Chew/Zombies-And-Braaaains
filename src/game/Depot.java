package game;

import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

import java.util.ArrayList;

/**
 * Class represents Depot that stores Ammunition.
 */

public class Depot extends Building {
    private ArrayList<AmmunitionBox> storage;

    /**
     * Constructor.
     */
    public Depot() {
        super('d');
        storage = new ArrayList<>();
        refillAmmunition();
    }

    @Override
    public void changeGroundStatus() {

    }

    /**
     * If Player standing on Depot, add a box of Ammunition on the current location.
     *
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        AmmunitionBox box = storage.get(storage.size() - 1);
        if(location.containsAnActor() && location.getActor() instanceof Player) {
            location.addItem(box);
            storage.remove(storage.size() - 1);
        }

        else if(!location.containsAnActor() && !location.getItems().isEmpty()){
            for(Item item: location.getItems()){
                if(item.hasCapability(ItemCapability.BULLET)){
                    storage.add((AmmunitionBox) item);
                    location.removeItem(item);
                    break;
                }
            }
        }


        if(storage.size() == 0){
            refillAmmunition();
        }
    }

    /**
     * Refill 3 ammunition boxes when it is empty.
     */
    private void refillAmmunition() {
        for(int i = 0; i < 3; i++)
            storage.add(new AmmunitionBox());
    }
}
