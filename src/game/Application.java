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

		Actor player = new Player("Player", '@', 1000);
		world.addPlayer(player, gameMap.at(42, 15));

		// Place farmers
//		gameMap.at(32, 10).addActor(new Farmer("Old-man"));
//		gameMap.at(40, 16).addActor(new Farmer("Farmer-2"));
//		gameMap.at(20,5).addActor(new Farmer("Farmer-3"));

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

		
		// place a simple weapon
		gameMap.at(74, 20).addItem(new Plank());
		gameMap.at(42, 15).addItem(new Plank());
		gameMap.at(54, 10).addItem(new Plank());
		gameMap.at(30, 20).addItem(new Plank());
		gameMap.at(30, 18).addItem(new Plank());
		
		// FIXME: Add more zombies!
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


		world.run();
	}
}
