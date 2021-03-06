package game;

import edu.monash.fit2099.engine.Ground;

/**
 * Abstract class represents Building in this game.
 */

public abstract class Building extends Ground{

    /**
     * Constructor.
     *
     * @param displayChar character to display for this type of terrain
     */
    public Building(char displayChar) {
        super(displayChar);
    }
}
