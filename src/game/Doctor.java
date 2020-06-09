package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;

import java.util.Random;

/**
 * A Doctor.
 *
 * This doctor has same characteristics as human, but also
 * has the abilities to cure Humans by injecting a vaccine.
 *
 * @author Adeline Chew Yao Yi and Tey Kai Ying
 *
 */

public class Doctor extends Human{

    private Behaviour[] behaviours = {
            new CureBehaviour()
    };

    /**
     * The constructor creates default Doctor
     *
     * @param name the Doctor's display name
     */
    public Doctor(String name) {
        super(name, 'D', 100);
    }

    /**
     * Firstly check if the doctor is damaged, it has 50% chance to heal itself.
     * Next the doctor will perform cure behaviour action if it is not null.
     *
     * @param actions list of possible Actions
     * @param lastAction previous game.Action, if it was a multiturn action
     * @param map the map where the current Doctor is
     * @param display the Display where the Doctor's utterances will be displayed
     */
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {

        // doctor can self heal
        if(this.hitPoints < this.maxHitPoints && Math.random() < 0.5){
            int healPoint = new Random().nextInt(16) + 15;
            heal(healPoint);
        }

        for (Behaviour behaviour : behaviours) {
            Action action = behaviour.getAction(this, map);
            if (action != null)
                return action;
        }
        return super.playTurn(actions, lastAction, map, display);
    }
}
