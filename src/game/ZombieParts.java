package game;

import edu.monash.fit2099.engine.WeaponItem;

public class ZombieParts extends WeaponItem {
    private int damage;

    public ZombieParts(String name, char displayChar, int damage, String verb) {
        super(name, displayChar, damage, verb);
        this.damage = damage;
    }

    public void increaseDamage(){
        damage += 5;
    }

}
