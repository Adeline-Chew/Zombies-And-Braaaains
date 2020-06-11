package game;

import edu.monash.fit2099.engine.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * The main class for the zombie apocalypse game.
 *
 */
public class Application {

	public static void main(String[] args) {
		World world = new NewWorld(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Fence(), new Tree(), new Hospital());
		
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
		"...........................................h.............................++.....",
		"........................................................................++.++...",
		".........................................................................++++...",
		"..........................................................................++....",
		"................................................................................");
		GameMap gameMap = new GameMap(groundFactory, map );
		world.addGameMap(gameMap);

		Actor player = null;
		try {
			player = new Player("Player", '@', 200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		world.addPlayer(player, gameMap.at(42, 15));

		gameMap.locationOf(player).addItem(new Shotgun());
		gameMap.locationOf(player).addItem(new SniperRifle());
		gameMap.locationOf(player).addItem(new AmmunitionBox());
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
		putActors(gameMap, zombies, 'Z');

		// Place some random farmers
		String[] farmers = {"Farmer-1", "Farmer-2", "Farmer-3", "Farmer-4", "Farmer-5", "Farmer-6"};
		putActors(gameMap, farmers, 'F');

		// Place some random humans
		String[] humans = {"Carlton", "May", "Vicente", "Andrea", "Wendy",
				"Elina", "Winter", "Clem", "Jacob", "Jaquelyn"};
		putActors(gameMap, humans, 'H');

		//Place some random humans outside the fence
		String[] humans1 = {"Paul", "Dennis", "Nathan", "Scott", "Josie"};
		putActors(gameMap, humans1, 'H');

		// Place some doctors in compound map
		String[] doctors = {"Doctor-1", "Doctor-2", "Doctor-3"};
		putActors(gameMap, doctors, 'D');


		FancyGroundFactory newGroundFactory = new FancyGroundFactory(new Lane(), new Dirt(), new Fence(), new Tree(), new Depot(), new Hospital());

		List<String> town = Arrays.asList(
				"________________________________________________________________________________",
				"________________________________________________________________________________",
				"________________________________________________________________________________",
				"________+++++___________________________________________________________________",
				"________________________________________________________________________________",
				"________________________________________________________________________________",
				"________________________________________________________________________________",
				"________________________________________________________________________________",
				"_____________________________d___________________d______________________________",
				"__________________________________##########____________________________________",
				"________________________________________________________________________________",
				"__________________h_____________________________________________________________",
				"________________________________________________________________________________",
				"__________________________________##########____________________________________",
				"_____________________________d___________________d______________________________",
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

		// place a vehicle in both maps
		gameMap.at(40, 11).addItem(new Vehicle(townMap));
		townMap.at(40, 11).addItem(new Vehicle(gameMap));

		// Place some farmers in town map
		townMap.at(0, 24).addActor(new Farmer("TownFarmer1"));
		townMap.at(1, 24).addActor(new Farmer("TownFarmer2"));
		townMap.at(2, 24).addActor(new Farmer("TownFarmer3"));
		townMap.at(4, 24).addActor(new Farmer("TownFarmer4"));

		// Place some random humans in town map
		String[] townHumans = {"TownHuman1", "TownHuman2", "TownHuman3", "TownHuman4", "TownHuman5",
				"TownHuman6", "TownHuman7", "TownHuman8", "TownHuman9", "TownHuman10"};
		putActors(townMap, townHumans, 'H');

		// Place some doctors in town map
		String[] townDoctors = {"TownDoctor1", "TownDoctor2", "TownDoctor3", "TownDoctor4"};
		putActors(townMap, townDoctors, 'D');

		townMap.at(44,11).addItem(new Shotgun());
		townMap.at(45, 13).addItem(new Shotgun());
		townMap.at(45, 9).addItem(new SniperRifle());
		townMap.at(36, 11).addItem(new SniperRifle());

		world.run();
	}

	private static void putActors(GameMap map, String[] actors, char type) {
		Random rand = new Random();
		int x, y;
		for (String name : actors) {
			do {
				x = rand.nextInt(map.getXRange().max());
				y = rand.nextInt(map.getYRange().max());
			}
			while (map.at(x, y).containsAnActor());
			if(type == 'Z'){
				map.at(x,  y).addActor(new Zombie(name) );
			}
			else if(type == 'F'){
				map.at(x,  y).addActor(new Farmer(name));
			}
			else if(type == 'H'){
				map.at(x,  y).addActor(new Human(name));
			}
			else if(type == 'D'){
				map.at(x,  y).addActor(new Doctor(name));
			}
		}
	}
}
