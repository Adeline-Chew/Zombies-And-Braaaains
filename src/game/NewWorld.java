package game;

import edu.monash.fit2099.engine.*;

import java.util.Random;

public class NewWorld extends World {
    private int turn;
    private MamboMarie mamboMarie = new MamboMarie();

    /**
     * Constructor.
     *
     * @param display the Display that will display this World.
     */
    public NewWorld(Display display) {
        super(display);
    }

    @Override
    public void run() {
        Random rand = new Random();
        boolean isAppear = Math.random() <= 0.05;

        if (player == null)
            throw new IllegalStateException();

        // initialize the last action map to nothing actions;
        for (Actor actor : actorLocations) {
            lastActionMap.put(actor, new DoNothingAction());
        }

        // This loop is basically the whole game
        while (stillRunning()) {
            GameMap playersMap = actorLocations.locationOf(player).map();
            playersMap.draw(display);

            for(GameMap map: gameMaps){
                if(isAppear && map.contains(player) && !map.contains(mamboMarie)){
                    int x, y;
                    do{
                        x = 0;
                        y = rand.nextInt(map.getYRange().max());
                    }
                    while(map.at(x, y).containsAnActor() || !map.at(x, y).canActorEnter(mamboMarie));
                    map.addActor(mamboMarie, map.at(x, y));
                    display.println("Mambo Marie appears in game.");
                }
            }

            // Process all the actors.
            for (Actor actor : actorLocations) {
                if (stillRunning())
                    processActorTurn(actor);
            }

            // Tick over all the maps. For the map stuff.
            for (GameMap gameMap : gameMaps) {
                gameMap.tick();
            }

            // After 30 turns, Mambo Marie will vanish
            if(actorLocations.contains(mamboMarie)) {
                turn++;
                if (turn % 30 == 0) {
                    actorLocations.remove(mamboMarie);
                    display.println("Mambo Marie was temporary disappears.");
                }
            }
        }
        display.println(endGameMessage());

    }

    @Override
    protected boolean stillRunning() {
        boolean containHuman = false, containMarie = false, containZombie = false;
        boolean continueGame = true;

        for(Actor actor : actorLocations){
            if(actor.hasCapability(ZombieCapability.ALIVE)){
                containHuman = true;
            }
        }

        if(actorLocations.contains(mamboMarie)){
            containMarie  = true;
        }

        for(Actor actor : actorLocations){
            if(actor.hasCapability(ZombieCapability.UNDEAD)){
                containZombie = true;
            }
        }

        if(!containHuman && !super.stillRunning()){
            display.println("Player loses");
            continueGame = false;
        }
        else if(!containZombie && !containMarie) {
            display.println("Player wins");
            continueGame = false;
        }

        if(!super.stillRunning()){
            continueGame = false;
        }
        
        return continueGame;
    }
}
