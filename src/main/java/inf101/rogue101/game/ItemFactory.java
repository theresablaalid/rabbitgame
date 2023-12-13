package inf101.rogue101.game;

import inf101.rogue101.objects.*;

public class ItemFactory {

	public static IItem createItem(char symbol) {
		switch(symbol) {
		case Wall.SYMBOL:
			return new Wall();
		case Rabbit.SYMBOL:
			return new Rabbit();
		case Carrot.SYMBOL:
			return new Carrot();
		case Player.SYMBOL:
			return new Player();
		case Spider.SYMBOL:
			return new Spider();
		case Amulet.SYMBOL:
			return Amulet.getInstance();
		case Portal.SYMBOL:
			return new Portal();
		case ' ':
			return null;
		case Dust.SYMBOL:
			return new Dust();
		case Armour.SYMBOL:
			return new Armour();
		case Gold.SYMBOL:
			return new Gold();
		default : 	
			throw new IllegalArgumentException("createItem: Don't know how to create a '" + symbol + "'");
		}
	}
}
