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
        addCapability(ItemCapability.RANGED_WEAPON);
    }

    /**
     * Accessor for the name of the weapon.
     *
     * @return a string represents the name of the weapon
     */
    public String getName(){return name;}

    /**
     * Display the submenu describing the operation of the weapon.
     *
     * @param actor actor performs the action
     * @param map the map where the current actor is
     * @param display the Display where the weapon's utterances will be displayed
     * @return an action selected from the menu by Player
     */
    public abstract Action subMenu(Actor actor, GameMap map, Display display);

    /**
     * Accessor for the amount of bullet contained in the weapon.
     *
     * @return the size of ammunition list of the weapon
     */
    public int getAmountOfBullet(){
        return ammunition.size();
    }

    /**
     * Remove a bullet after shooting.
     *
     */
    public void shoot(){
        ammunition.remove(ammunition.size() - 1);
    }

    /**
     * Load all bullets from the ammunition box into the weapon.
     *
     * @param box an AmmunitionBox containing bullets
     */
    public void loadAmmunition(AmmunitionBox box){
        ammunition.addAll(box.getAmmunition());
    }
}
