package game;

import edu.monash.fit2099.engine.*;

import java.util.Random;

/**
 * A class extends from the World to make this game more extensible.
 */

public class NewWorld extends World {
    private int turn;
    private MamboMarie mamboMarie = new MamboMarie();
    private static boolean isPlayerWin = false, isPlayerQuit = false;

    /**
     * Constructor.
     *
     * @param display the Display that will display this World.
     */
    public NewWorld(Display display) {
        super(display);
    }


    /**
     * Run the game.
     * On each iteration the gameloop does the following: - displays the player's
     * map - processes the actions of every Actor in the game.
     * Mambo Marie also can be added into map and the turn of occurrence is calculated.
     *
     */
    @Override
    public void run() {

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
            GameMap compound = gameMaps.get(0);

            // Process all the actors.
            for (Actor actor : actorLocations) {
                if (stillRunning())
                    processActorTurn(actor);
                else
                    break;
            }

            // Tick over all the maps. For the map stuff.
            for (GameMap gameMap : gameMaps) {
                gameMap.tick();
            }

            checkMamboMarie(gameMaps.get(0));
        }
        display.println(endGameMessage());

    }

    protected void checkMamboMarie(GameMap compound){
        boolean isAppear = Math.random() <= 0.05;
        Random rand = new Random();
        if(!mamboMarie.isConscious()){
            isAppear = false;
        }
        // Add Mambo Marie
        if(isAppear && !compound.contains(mamboMarie)){
            int x, y;
            do{
                x = 0;
                y = rand.nextInt(compound.getYRange().max());
            }
            while(compound.at(x, y).containsAnActor() || !compound.at(x, y).canActorEnter(mamboMarie));
            compound.addActor(mamboMarie, compound.at(x, y));
            display.println("Mambo Marie appears in game.");
        }

        // After 30 turns, Mambo Marie will vanish
        if(actorLocations.contains(mamboMarie)) {
            turn++;
            if (turn % 30 == 0) {
                actorLocations.remove(mamboMarie);
                display.println("Mambo Marie temporary disappears.");
            }
        }
    }

    /**
     * Check if the maps contain player, also check if all humans or zombies vanished.
     *
     * @return true if the game is still running
     */
    @Override
    protected boolean stillRunning() {
        boolean containHuman = false, containMarie = false, containZombie = false;
        boolean continueGame = true;

        for(Actor actor : actorLocations){
            if(gameMaps.get(0).contains(actor) && actor.hasCapability(ZombieCapability.ALIVE) && actor != player){
                containHuman = true;
            }
        }

        if(actorLocations.contains(mamboMarie)){
            containMarie  = true;
        }

        for(Actor actor : actorLocations){
            if(gameMaps.get(0).contains(actor) && actor.hasCapability(ZombieCapability.UNDEAD)){
                containZombie = true;
            }
        }

        if(!containHuman || !super.stillRunning()){
            continueGame = false;
        }
        else if(!containZombie && !containMarie) {
            isPlayerWin = true;
            continueGame = false;
        }
        
        return continueGame;
    }

    /**
     * This method is called when Player choose to quit the game.
     */
    public static void playerQuitGame(){
        isPlayerQuit = true;
        isPlayerWin = false;
    }

    /**
     * Return a string that can be displayed when the game ends.
     *
     * @return the string "Game Over"
     */
    @Override
    protected String endGameMessage() {
        String result = "";
        if(isPlayerWin){
            result = "Player wins";
        }
        else if (!isPlayerQuit){
            result = "Player loses.";
        }
        return result + "\n" + super.endGameMessage();
    }
}
