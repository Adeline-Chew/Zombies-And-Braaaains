package edu.monash.fit2099.interfaces;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * This interface provides the ability to add methods to Actor, without modifying code in the engine,
 * or downcasting references in the game.   
 */

public interface ActorInterface {
    String damage(int points, GameMap map);
    Location getRandomAdjacent(GameMap map);
}
