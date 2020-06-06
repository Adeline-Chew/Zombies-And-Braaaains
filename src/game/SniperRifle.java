package game;


import edu.monash.fit2099.engine.*;

import java.util.*;

public class SniperRifle extends RangedWeapon {
    private SniperAmmunition[] sniperAmmunition;
    private final HashMap<Double, Integer> sniperDamage = new HashMap<>();

    public SniperRifle(){
        super("Sniper Rifle", 'S', 20, "fires");
        sniperAmmunition = new SniperAmmunition[20];
        this.addCapability(ItemCapability.RANGED_WEAPON);
        sniperDamage.put(0.75, 20);
        sniperDamage.put(0.90, 40);
        sniperDamage.put(1.00, 200);
    }

    @Override
    public Action subMenu(Actor actor, GameMap map, Display display){
        Actions actions = new Actions();

        for(Location location: scanTarget(map.locationOf(actor), actor)){
            actions.add(new ChooseTargetAction(location.getActor(), this, display));
        }

        return menu.showMenu(actor, actions, display);
    }

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

    private ArrayList<Location> search(ArrayList<ArrayList<Location>> layer) {
        ArrayList<Location> locations = new ArrayList<>();
        for (ArrayList<Location> path : layer) {
            if (containsTarget(path.get(path.size() - 1))) {
                locations.add(path.get(path.size() - 1));
            }
        }
        return locations;
    }

    private boolean containsTarget(Location here) {
        return (here.getActor() != null && here.getActor().hasCapability(ZombieCapability.UNDEAD));
    }

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

    @Override
    public void lostAmmunition() {

    }

    public HashMap<Double, Integer> getSniperDamage(){
        return sniperDamage;
    }

}
