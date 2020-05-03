package edu.monash.fit2099.demo.conwayslife;

import edu.monash.fit2099.engine.*;

/**
 * Class representing the Player.
 */
public class Player extends Actor {

	private Menu menu = new Menu();
	private char[] chars = {'+', 't', 'T'};

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
	}

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();

		actions.add(new SleepAction());
		actions.add(new SowAction());
		return menu.showMenu(this, actions, display);
	}

	private class SleepAction extends Action {

		private int sleepTime = 200;

		@Override
		public String execute(Actor actor, GameMap map) {
			sleepTime--;
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
			return actor + " is sleeping.";
		}

		@Override
		public String menuDescription(Actor actor) {
			return "Sleep a while";
		}

		@Override
		public Action getNextAction() {
			if (sleepTime > 0)
				return this;

			return null;
		}
	}

	private class SowAction extends Action{
		public String execute(Actor actor, GameMap map) {
			String s = "";

			int x = map.locationOf(actor).x();
			int y = map.locationOf(actor).y();

			if (map.at(x+1, y).getDisplayChar() == '.') {
				s = "Sowing";
			}

			return s;
		}
		@Override
		public String menuDescription(Actor actor) {
			return "Sow a seed";
		}

	}
}
