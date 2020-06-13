package game;

import edu.monash.fit2099.engine.*;

import java.util.*;

/**
 * A type of ranged weapon.
 */

public class SniperRifle extends RangedWeapon {
    private HashMap<Double, Integer> sniperDamage = new HashMap<>();

    /**
     * Constructor.
     */
    public SniperRifle(){
        super("Sniper Rifle", 'S', 20, "bangs");
        sniperDamage.put(0.75, 20);
        sniperDamage.put(0.90, 40);
        sniperDamage.put(1.00, 200);
    }

    /**
     * Display the menu of description of actions with all the targets available.
     *
     * @param actor actor performs the action
     * @param map the map where the current actor is
     * @param display the Display where the weapon's utterances will be displayed
     * @return Action chosen by Player.
     */
    @Override
    public Action subMenu(Actor actor, GameMap map, Display display){
        Actions actions = new Actions();

        for(Location location: scanTarget(map.locationOf(actor), actor)){
            actions.add(new ChooseTargetAction(location.getActor(), this, display));
        }
        if(actions.size() == 0)
            return null;
        return menu.showMenu(actor, actions, display);
    }

    /**
     * This method adapted from HuntBehaviour. It scan and return all the Actor with UNDEAD capability within the range.
     * @param here Location of Actor.
     * @param actor Player.
     * @return ArrayList<Location> containing all the targets' location.
     */
    private ArrayList<Location> scanTarget(Location here, Actor actor){
        HashSet<Location> visitedLocations = new HashSet<>();
        ArrayList<Location> now = new ArrayList<>();
        ArrayList<Location> targets = new ArrayList<>();

        now.add(here);

        ArrayList<ArrayList<Location>> layer = new ArrayList<>();
        layer.add(now);

        int range = 10;
        for(int r = 0; r < range; r++){
            layer = getNextLayer(actor, layer, visitedLocations);
            targets.addAll(search(layer));
        }
        return targets;
    }

    /**
     * Search every exits from the parameter given to check whether the location contains target.
     */
    private ArrayList<Location> search(ArrayList<ArrayList<Location>> layer) {
        ArrayList<Location> locations = new ArrayList<>();
        for (ArrayList<Location> path : layer) {
            if (containsTarget(path.get(path.size() - 1))) {
                locations.add(path.get(path.size() - 1));
            }
        }
        return locations;
    }

    /**
     * Return true if the location contains Actor with UNDEAD capability.
     */
    private boolean containsTarget(Location here) {
        return (here.getActor() != null && here.getActor().hasCapability(ZombieCapability.UNDEAD));
    }

    /**
     * Get the next layer of exits surrounding the Actor.
     */
    private ArrayList<ArrayList<Location>> getNextLayer(Actor actor, ArrayList<ArrayList<Location>> layer, HashSet<Location> visitedLocations) {
        ArrayList<ArrayList<Location>> nextLayer = new ArrayList<>();

        for(ArrayList<Location> path : layer){
            List<Exit> exits = new ArrayList<Exit>(path.get(path.size() - 1).getExits());
            Collections.shuffle(exits);
            for (Exit exit : path.get(path.size() - 1).getExits()) {
                Location destination = exit.getDestination();
                if (!destination.getGround().canActorEnter(actor) || visitedLocations.contains(destination))
                    continue;
                visitedLocations.add(destination);
                ArrayList<Location> newPath = new ArrayList<Location>(path);
                newPath.add(destination);
                nextLayer.add(newPath);
            }
        }
        return nextLayer;
    }

    /**
     * @return Get the combinations of Sniper's damage and its probability.
     */
    public HashMap<Double, Integer> getSniperDamage(){
        return sniperDamage;
    }

}
