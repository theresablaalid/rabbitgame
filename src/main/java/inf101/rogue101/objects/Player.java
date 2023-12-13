package inf101.rogue101.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import inf101.grid.GridDirection;
import inf101.rogue101.game.IGameView;
import javafx.scene.input.KeyCode;
/**
 * En spiller i Rogue 101 
 * 
 * Spilleren navigerer labyrinten og slåss med monster. For å komme seg ut 
 * og vinne spille må spilleren gå gjennom portalen; for å åpne portalen 
 * må den finne amuletten og bære den med seg. 
 * 
 * På veien kan den plukke opp gull og slåss med monster
 * 
 * @author Anna Eilertsen - anna.eilertsen@uib.no
 *
 */
public class Player implements IPlayer {
	/**
	 * char representation of this type 
	 */
	public static final char SYMBOL = '@';
	private static final int MAXHEALTH = 100;
	private int attack;
	private int defence;
	private int damage;
	private int hp;
	private String name;
	public List<IItem> backpack = new ArrayList<>();


	public Player() {
		attack = 10;
		defence = 2;
		damage = 3;
		hp = Player.MAXHEALTH;
		name = System.getProperty("user.name");
	}
	

	@Override
	public int getAttack() {
		return attack;
	}

	@Override
	public int getDamage() {
		return damage;
	}

	@Override
	public int getCurrentHealth() {
		return hp;
	}

	/*
	 * If player picks up armour, the defence for the player increases
	 */
	@Override
	public int getDefence() {
		Armour armour = new Armour();
		for(IItem items : backpack) {
			if(items.getClass() == armour.getClass()) {
				return defence +2;
			}
		}
		return defence;
	}
	
	
	@Override
	public int getMaxHealth() {
		return Player.MAXHEALTH;
	}

	@Override
	public String getShortName() {
		return getLongName();
	}
	
	@Override
	public String getLongName() {
		return name;
	}

	@Override
	public int getSize() {
		return 5;
	}

	@Override
	public String getGraphicTextSymbol() {
			return "" + SYMBOL;
	}
	
	@Override
	public String getEmoji() {
		return hp > 0 ? "👸" : "⚱️"; // 🤴  ⚰️
	}

	@Override
	public int handleDamage(int amount) {
		amount = Math.max(1, amount - defence);
		amount = Math.min(hp + 1, amount);
		hp -= amount;
		return amount;
	}

	@Override
	public void keyPressed(IGameView game, KeyCode key) {
		System.err.println("Player moves");
		switch (key) {
		case LEFT:
			tryToMove(game, GridDirection.WEST);
			break;
		case RIGHT:
			tryToMove(game, GridDirection.EAST);
			break;
		case UP:
			tryToMove(game, GridDirection.NORTH);
			break;
		case DOWN:
			tryToMove(game, GridDirection.SOUTH);
			break;
		case P:
			pickUp(game);
			break;
		case D:
			drop(game);
			break;
		default:
			System.err.printf("Illegal key pressed. Key: %s", key);
		}
		showStatus(game);
	}

	private void tryToMove(IGameView game, GridDirection dir) {
		if (game.canGo(dir)) {
			game.displayDebug("Moving.");
			game.move(dir);
		} 
		else {
			if(game.attack(dir))
				game.displayDebug("Victory!");
			else
				game.displayDebug("Ouch! Can't go there.");
		}
	}

	private void showStatus(IGameView game) {
		String player_hp = ("Player has " + this.hp + "hp left.");
		if(!backpack.isEmpty()) {
			player_hp += "Items in backpack: " + pickedItem(game);
		}
		game.displayMessage(player_hp);
	}
	/**
	 * 
	 * pickUp should pickup the first thing it finds on the location it is on
	 * @param game
	 * 				The game the object exists in
	 * **/
	private void pickUp(IGameView game) {
		List<IItem> items = game.getLocalNonActorItems();
		if(!items.isEmpty()) {
			Collections.sort(items,new IItemComparator());
			Optional<IItem> found = game.pickUp(items.get(items.size()-1));
			if(found.isPresent()) {
				game.displayMessage("Picked up "+found.get().getShortName());
				backpack.add(found.get());
			}
				game.displayMessage("Picked up "+found.get().getShortName());
			} 	
		}
	
	/**
	 * Collect all the things that are picked up, and adds all the items in a list
	 * @param game 
	 * the game that objects exists in
	 * @return list of items
	 * */
	private String pickedItem(IGameView game) {
		List<String> pickedItemsList = new ArrayList<>();
		for(IItem s : backpack) {
			pickedItemsList.add(s.getShortName());
			}
		String itemsInBackpack = Arrays.toString(pickedItemsList.toArray()); 
		return itemsInBackpack;
			}

	private void drop(IGameView game) {
		if(!backpack.isEmpty()) {
			game.drop(backpack.remove(0)); 
		} 
	}

	@Override
	public void doTurn(IGameView game) {
	}
	
	@Override 
	public boolean isDestroyed() {
		return false; //Even when dead, the player should remain on the map
	}

	@Override
	public boolean hasItem(IItem item) {
		for(IItem items : backpack) {
			if(items == item) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public char getSymbol() {
		return SYMBOL; 
	}
}
