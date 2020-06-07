package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;

public abstract class RangedWeapon extends WeaponItem {
    protected Menu menu = new Menu();
    protected ArrayList<Ammunition> ammunition = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param name        name of the item
     * @param displayChar character to use for display when item is on the ground
     * @param damage      amount of damage this weapon does
     * @param verb        verb to use for this weapon, e.g. "hits", "zaps"
     */
    public RangedWeapon(String name, char displayChar, int damage, String verb) {
        super(name, displayChar, damage, verb);
    }

    public String getName(){return name;}

    public abstract Action subMenu(Actor actor, GameMap map, Display display);

    public int getAmountOfBullet(){
        return ammunition.size();
    }

    public void shoot(){
        ammunition.remove(ammunition.size() - 1);
    }

    public void loadAmmunition(AmmunitionBox box){
        ammunition.addAll(box.getAmmunition());
    }
}
