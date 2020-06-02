package game;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.FancyGroundFactory;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.World;

/**
 * The main class for the zombie apocalypse game.
 *
 */
public class Application {

	public static void main(String[] args) {
		World world = new World(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Fence(), new Tree());
		
		List<String> map = Arrays.asList(
		"................................................................................",
		"................................................................................",
		"....................................##########..................................",
		"..........................###########........#####..............................",
		"............++...........##......................########.......................",
		"..............++++.......#..............................##......................",
		".............+++...+++...#...............................#......................",
		".........................##..............................##.....................",
		"..........................#...............................#.....................",
		".........................##...............................##....................",
		".........................#...............................##.....................",
		".........................###..............................##....................",
		"...........................####......................######.....................",
		"..............................#########.........####............................",
		"............+++.......................#.........#...............................",
		".............+++++....................#.........#...............................",
		"...............++........................................+++++..................",
		".............+++....................................++++++++....................",
		"............+++.......................................+++.......................",
		"................................................................................",
		".........................................................................++.....",
		"........................................................................++.++...",
		".........................................................................++++...",
		"..........................................................................++....",
		"................................................................................");
		GameMap gameMap = new GameMap(groundFactory, map );
		world.addGameMap(gameMap);
		int x, y;
		Random rand = new Random();

		Actor player = null;
		try {
			player = new Player("Player", '@', 200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		world.addPlayer(player, gameMap.at(42, 15));
		
		// place a simple weapon
		gameMap.at(74, 20).addItem(new Plank());
		gameMap.at(42, 15).addItem(new Plank());
		gameMap.at(54, 10).addItem(new Plank());
		gameMap.at(30, 20).addItem(new Plank());
		gameMap.at(30, 18).addItem(new Plank());
		

		gameMap.at(30, 20).addActor(new Zombie("Groan"));
		gameMap.at(31,  18).addActor(new Zombie("Boo"));
		gameMap.at(35,  15).addActor(new Zombie("Uuuurgh"));
		gameMap.at(34, 14).addActor(new Zombie("Mortalis"));
		gameMap.at(1, 10).addActor(new Zombie("Gaaaah"));
		gameMap.at(62, 12).addActor(new Zombie("Aaargh"));

		String[] zombies = {"Z1", "Z2", "Z3", "Z4"};
		for(String name: zombies){
			Zombie zombie = new Zombie(name);
			x = rand.nextInt(80);
			y = rand.nextInt(25);
			if(!gameMap.at(x, y).containsAnActor() && gameMap.at(x, y).canActorEnter(zombie)){
				gameMap.at(x, y).addActor(zombie);
			}
		}

		// Place some random farmers
		String[] farmers = {"Farmer-1", "Farmer-2", "Farmer-3", "Farmer-4", "Farmer-5", "Farmer-6"};
		for(String name:farmers){
			Farmer farmer = new Farmer(name);
			x = rand.nextInt(80);
			y = rand.nextInt(25);
			if(!gameMap.at(x, y).containsAnActor() && gameMap.at(x, y).canActorEnter(farmer)){
				gameMap.at(x, y).addActor(farmer);
			}
		}

		// Place some random humans
		String[] humans = {"Carlton", "May", "Vicente", "Andrea", "Wendy",
				"Elina", "Winter", "Clem", "Jacob", "Jaquelyn"};

		for (String name : humans) {
			do {
				x = (int) Math.floor(Math.random() * 20.0 + 30.0);
				y = (int) Math.floor(Math.random() * 7.0 + 5.0);
			}
			while (gameMap.at(x, y).containsAnActor());
			gameMap.at(x,  y).addActor(new Human(name));
		}
		//Place some random humans outside the fence
		String[] humans1 = {"Paul", "Dennis", "Nathan", "Scott", "Josie"};
		for(String name: humans1){
			Human human = new Human(name);
			x = rand.nextInt(80);
			y = rand.nextInt(25);
			if(!gameMap.at(x, y).containsAnActor() && gameMap.at(x, y).canActorEnter(human)){
				gameMap.at(x, y).addActor(human);
			}
		}

		FancyGroundFactory newGroundFactory = new FancyGroundFactory(new Lane(), new Dirt(), new Fence(), new Tree());

		List<String> town = Arrays.asList(
				"________________________________________________________________________________",
				"________________________________________________________________________________",
				"________________________________________________________________________________",
				"________+++++___________________________________________________________________",
				"________________________________________________________________________________",
				"________________________________________________________________________________",
				"________________________________________________________________________________",
				"________________________________________________________________________________",
				"________________________________________________________________________________",
				"________________________________#####___________________________________________",
				"________________________________________________________________________________",
				"________________________________________________________________________________",
				"________________________________________________________________________________",
				"________________________________________________________________________________",
				"________________________________________________________________________________",
				"________________________________________________________________________________",
				"________________________________________________________________________________",
				"________________________________________________________________________________",
				"________________________________________________________________________________",
				"________________________________________________________________________________",
				"..........______________________________________________________________________",
				"..........______________________________________________________________________",
				".........._____________________________________________+++++____________________",
				"..........______________________________________________________________________",
				"..........______________________________________________________________________");
		GameMap townMap = new GameMap(newGroundFactory, town);
		world.addGameMap(townMap);

		// place a vehicle
		gameMap.at(40, 11).addItem(new Vehicle(townMap));
		townMap.at(40, 11).addItem(new Vehicle(gameMap));

		// place some random zombies
		String[] townZombies = {"ZZ1", "ZZ2", "ZZ3", "ZZ4", "ZZ5"};
		for(String name: townZombies){
			Zombie zombie = new Zombie(name);
			x = rand.nextInt(80);
			y = rand.nextInt(25);
			if(!townMap.at(x, y).containsAnActor() && townMap.at(x, y).canActorEnter(zombie)){
				townMap.at(x, y).addActor(zombie);
			}
		}

		// Place some random farmers
		String[] townFarmers = {"TownFarmer1", "TownFarmer2", "TownFarmer3"};
		for(String name:townFarmers){
			Farmer farmer = new Farmer(name);
			x = rand.nextInt(80);
			y = rand.nextInt(25);
			if(!townMap.at(x, y).containsAnActor() && townMap.at(x, y).canActorEnter(farmer)){
				townMap.at(x, y).addActor(farmer);
			}
		}

		// Place some random humans
		String[] townHumans = {"TownHuman1", "TownHuman2", "TownHuman3", "TownHuman4", "TownHuman5",
				"TownHuman6", "TownHuman7", "TownHuman8", "TownHuman9", "TownHuman10"};

		for (String name : townHumans) {
			do {
				x = (int) Math.floor(Math.random() * 20.0 + 30.0);
				y = (int) Math.floor(Math.random() * 7.0 + 5.0);
			}
			while (townMap.at(x, y).containsAnActor());
			townMap.at(x,  y).addActor(new Human(name));
		}

		world.run();
	}
}
