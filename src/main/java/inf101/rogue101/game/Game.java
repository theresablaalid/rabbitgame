package inf101.rogue101.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import inf101.grid.GridDirection;
import inf101.grid.IGrid;
import inf101.grid.Location;
import inf101.rogue101.map.GameMap;
import inf101.rogue101.map.IGameMap;
import inf101.rogue101.map.IMapView;
import inf101.rogue101.map.MapReader;
import inf101.rogue101.objects.Amulet;
import inf101.rogue101.objects.IActor;
import inf101.rogue101.objects.IItem;
import inf101.rogue101.objects.IPlayer;
import inf101.rogue101.objects.Portal;
import javafx.scene.input.KeyCode;

/**
 * A game implementation for Rogue 101
 * 
 * @author anya
 * @author Anna Eilertsen - anna.eilertsen@uib.no
 * @author Martin Vatshelle - martin.vatshelle@uib.no
 *
 */
public class Game implements IGame {
	/**
	 * All the IActors that have things left to do this turn
	 */
	private List<IActor> actors = new ArrayList<IActor>();
	
	/**
	 * The game map. {@link IGameMap} gives us a few more details than
	 * {@link IMapView} (write access to item lists); the game needs this but
	 * individual items don't.
	 */
	private IGameMap map;
	
	/**
	 * The actor who gets to perform an action this turn
	 */
	private IActor currentActor;
	
	/**
	 * The current location of the current actor
	 */
	private Location currentLocation;
	
	private int movePoints = 0;
	private int numPlayers = 0;
	GameGraphics graphics;

	public Game() {
		this(getDefaultMap());
	}

	public Game(String mapString) {
		this(MapReader.readItems(mapString));
	}

	public Game(IGrid<IItem> inputGrid) {
		this(new GameGraphics(),inputGrid);
	}
	
	public Game(GameGraphics graphics) {
		this(graphics,getDefaultMap());
	}

	public Game(GameGraphics graphics,IGrid<IItem> inputGrid) {
		this.graphics = graphics;
		this.map = new GameMap(inputGrid);
		nextPlayer();
	}

	static IGrid<IItem> getDefaultMap() {
		// NOTE: in a more realistic situation, we will have multiple levels (one map
		// per level), and (at least for a Roguelike game) the levels should be
		// generated
		//
		// inputGrid will be filled with characters indicating what (if anything)
		// should be placed at that map square

		IGrid<IItem> grid;
		try {
			grid = MapReader.loadItems("maps/level1.txt");
		} catch (Exception e) {
			System.err.println("Map not found – falling back to builtin map");
			grid = MapReader.readItems(MapReader.BUILTIN_MAP);
		} 
		return grid;
	}

	@Override
	public boolean addItem(IItem item, Location loc) {
		if (item != null) {
			map.add(loc, item);
			// also keep track of whether we need to redraw this cell
			graphics.reportChange(loc);
			return true;
		}
		return false;
	}

	@Override
	public Optional<IItem> removeItem(IItem item, Location loc) {
		if (item == null || !map.has(currentLocation, item))
			return Optional.ofNullable(null);
		if (!Game.pickUpSucceeds(currentActor, item))
			return Optional.ofNullable(null);
		map.remove(currentLocation, item);
		return Optional.ofNullable(item);
	}

	@Override
	public boolean attack(IActor attacker, GridDirection dir) {
		if(!hasTurn(attacker))
			return false;
		
		//choose target
		Location to = getCurrentLocation().getNeighbor(dir);
		List<IActor> actorsOnTargetLoc = map.getActors(to);
		if (actorsOnTargetLoc.isEmpty()) //just like move
			return true;
		if(actorsOnTargetLoc.size()>1) { 
			throw new IllegalMoveException("Can not attack multiple target at once.");
		}
		actorsOnTargetLoc.get(0);
		IActor target = actorsOnTargetLoc.get(0);

		Location newLoc = attack(attacker,dir,target);
		return to.equals(newLoc);
	}

	private boolean hasTurn(IActor actor) {
		return movePoints>0 && actor == getCurrentActor();
	}

	@Override
	public Location attack(IActor attacker, GridDirection dir, IItem target) {
		Location from = map.getLocation(attacker);
		if(!hasTurn(attacker))
			return from;

		Location to = from.getNeighbor(dir);
		if(!map.has(to, target)) {
			throw new IllegalMoveException("Target isn't there!");
		}
		
		graphics.reportChange(from);
		graphics.reportChange(to);
		
		if (Game.attackSucceeds(attacker, target))
			target.handleDamage(attacker.getDamage());

		map.clean(from);
		map.clean(to);

		if (target.isDestroyed()) {
			return move(attacker,dir);
		} else {
			movePoints--;
			return currentLocation;
		}
	}

	private void nextPlayer() {
		if (actors.isEmpty()) {
			// no one in the queue, we're starting a new turn!
			// first collect all the actors:
			beginTurn();
		}
		// get the next player or non-player in the queue
		currentActor = actors.remove(0);
		currentLocation = map.getLocation(currentActor);		
	}
	
	/**
	 * Begin a new game turn, or continue to the previous turn
	 *
	 * @return True if the game should wait for more user input
	 */
	public boolean doTurn() {
		do {
			nextPlayer();
			graphics.reportChange(currentLocation);
			if (currentActor.isDestroyed()) { // skip if it's dead
				continue;
			}

			if (currentLocation == null) {
				graphics.displayDebug("doTurn(): Whoops! Actor has disappeared from the map: " + currentActor);
			}
			movePoints = 1; // everyone gets to do one thing

			if (currentActor instanceof IPlayer) {
				if (currentActor.getCurrentHealth() <= 0) {
					// a dead human player gets removed from the game
					// TODO: you might want to be more clever here
					graphics.displayStatus(currentActor.getShortName() + " died.");
				} else {
					currentActor.doTurn(new GameView(this, currentActor));
					Location newLocation = map.getLocation(currentActor);
					graphics.reportChange(newLocation);
				}
				// For the human player, we need to wait for input, so we just return.
				// Further keypresses will cause keyPressed() to be called, and once the human
				// makes a move, it'll lose its movement point and doTurn() will be called again
				//
				// NOTE: currentActor and currentLocation are set to the IPlayer (above),
				// so the game remembers who the player is whenever new keypresses occur. This
				// is also how e.g., getLocalItems() work – the game always keeps track of
				// whose turn it is.
				return true;
			} else if (currentActor instanceof IActor) {

				try {
					// computer-controlled players do their stuff right away
					currentActor.doTurn(new GameView(this, currentActor));
				} catch (Exception e) {
					// actor did something wrong
					// do nothing, leave this IActor
				}
				Location newLocation = map.getLocation(currentActor);
				graphics.reportChange(newLocation);

				// remove any dead items from current location
				map.clean(currentLocation);
				map.clean(newLocation);
			} else {
				graphics.displayDebug("doTurn(): Hmm, this is a very strange actor: " + currentActor);
			}


		} while (numPlayers > 0); // we can safely repeat if we have players, since we'll return (and break out of
		// the loop) once we hit the player

		return true;
	}

	/**
	 * Go through the map and collect all the actors.
	 */
	private void beginTurn() {
		numPlayers = 0;

		for(Location loc : map.locations()) {
			List<IItem> list = map.getAllModifiable(loc);
			Iterator<IItem> li = list.iterator(); // manual iterator lets us remove() items
			List<IItem> toRemove = new ArrayList<IItem>();
			while (li.hasNext()) { // this is what "for(IItem item : list)" looks like on the inside
				IItem item = li.next();
				if (item.getCurrentHealth() < 0) {
					// normally, we expect these things to be removed when they are destroyed, so
					// this shouldn't happen
					graphics.formatDebug("beginTurn(): found and removed leftover destroyed item %s '%s' at %s%n",
							item.getLongName(), item.getGraphicTextSymbol(), loc);

					toRemove.add(item);
				} else if (item instanceof IPlayer) {
					actors.add(0, (IPlayer) item); // we let the human player go first
					numPlayers++;
				} else if (item instanceof IActor) {
					actors.add((IActor) item); // add other actors to the end of the list
				}
				
			}		
		}
	}

	@Override
	public boolean canGo(IActor actor, GridDirection dir) {
		Location from = map.getLocation(actor);
		Location to = from.getNeighbor(dir);
		if (containsItem(to, Portal.class) && actor instanceof IPlayer) {
			IPlayer currentPlayer = (IPlayer) currentActor;
			if (currentPlayer.hasItem(Amulet.getInstance())) {
				return true;
			}
		}
		return map.canGo(currentLocation, dir);
	}

	@Override
	public IMessageView getIMessageView() {
		return graphics;
	}


	public void draw() {
		graphics.drawDirty(map);
	}

	@Override
	public int getHeight() {
		return map.getHeight();
	}

	public List<IItem> getLocalNonActorItems() {
		return map.getItems(currentLocation);
	}

	@Override
	public Location getCurrentLocation() {
		if(currentActor==null)
			beginTurn();
		return currentLocation;
	}

	/**
	 * Return the game map. {@link IGameMap} gives us a few more details than
	 * {@link IMapView} (write access to item lists); the game needs this but
	 * individual items don't.
	 */
	@Override
	public IGameMap getMap() {
		return map;
	}

	@Override
	public List<GridDirection> getPossibleMoves(IActor actor) {
		return map.getPossibleMoves(map.getLocation(actor));
	}

	@Override
	public List<Location> getReachable() {
		return map.getReachable(currentLocation, 5);
	}

	@Override
	public int getWidth() {
		return map.getWidth();
	}

	public void keyPressed(KeyCode code) {
		// only an IPlayer/human can handle keypresses, and only if it's the human's
		// turn
		// NB: all codes are for the large letter, even if a small one was pushed
		if (currentActor instanceof IPlayer) {
			if (currentActor.getCurrentHealth() <= 0) {
				graphics.displayMessage("Sorry, you're dead!");
			} else {
				IGameView gv = new GameView(this, currentActor);
				((IPlayer) currentActor).keyPressed(gv, code); // do your thing
			}
		}
	}

	@Override
	public Location move(IActor actor, GridDirection dir) {
		if(!hasTurn(actor))
			throw new IllegalMoveException("You're out of moves!");
		if (!canGo(actor,dir))
			throw new IllegalMoveException("You cannot go there!");

		Location to = currentLocation.getNeighbor(dir);
		
		//winning condition
		if (containsItem(to, Portal.class) && actor instanceof IPlayer) {
			IPlayer currentPlayer = (IPlayer) currentActor;
			Portal portal = (Portal) map.getActors(to).get(0);
			if (currentPlayer.hasItem(Amulet.getInstance())) {
				graphics.displayStatus("Congratulations, you reached the portal with the " + Amulet.getInstance().getShortName()
						+ " and won the game!");
				portal.open();
				map.remove(to, portal);
				return to;
			}
		}

		map.remove(currentLocation, actor);
		map.add(to, actor);
		currentLocation = to;
		movePoints--;
		return to;
	}

	@Override
	public boolean rangedAttack(IActor attacker, GridDirection dir, IItem target) {
		int attackdamage = attacker.getAttack();
		int defence = target.getDefence();
		if (attackdamage > defence) {
			target.handleDamage(attackdamage - defence);
			return true;
		}
		
		return false;
	}

	@Override
	public IActor getCurrentActor() {
		if(currentActor==null)
			beginTurn();
		return currentActor;
	}

	public Location setCurrent(IActor actor) {
		currentLocation = map.getLocation(actor);
		if (currentLocation != null) {
			currentActor = actor;
			movePoints = 1;
		}
		return currentLocation;
	}

	public IActor setCurrent(Location loc) {
		List<IActor> list = map.getActors(loc);
		if (!list.isEmpty()) {
			currentActor = list.get(0);
			currentLocation = loc;
			movePoints = 1;
		}
		return currentActor;
	}



	/**
	 * An {@link IActor} succeeds at picking up a target item if the actors attack score 
	 * is strictly larger than the target's size
	 * 
	 * @param actor the {@link IActor} trying to pick up the item 
	 * @param item   the {@link IItem} to be picked up
	 * @return true if picking up succeeds, false otherwise
	 */
	public static boolean pickUpSucceeds(IActor actor, IItem item) {
		return actor.getAttack() > item.getSize();
	}

	/**
	 * An attack succeeds if the attacker's attack score is strictly larger than,
	 * the target's defense score.
	 * 
	 * @param attacker the attacking {@link IActor}
	 * @param target   the target {@link IItem}
	 * @return true if the attack succeeds, false otherwise
	 */
	public static boolean attackSucceeds(IActor attacker, IItem target) {
		return attacker.getAttack() > target.getDefence();
	}

	@Override
	public <T extends IItem> boolean containsItem(Location loc, Class<T> c) {
		return map.containsItem(loc, c);
	}

	@Override
	public Optional<IItem> pickUpItem(IActor actor, IItem item) {
		if(!hasTurn(actor))
			return Optional.ofNullable(null);
		movePoints--;
		return removeItem(item, currentLocation);
	}
}
