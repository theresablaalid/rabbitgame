package inf101.rogue101.objects;

import inf101.GetStarted;
import inf101.grid.GridDirection;
import inf101.grid.Location;
import inf101.rogue101.game.Game;
import inf101.rogue101.game.GameView;
import inf101.rogue101.game.IGameView;
import inf101.rogue101.game.ItemFactory;
import inf101.rogue101.map.MapReader;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

public class PlayerTest {

	private Game game;
    private IGameView board;
    private IPlayer player;
    private Location loc;

    @BeforeEach
    void beforeEach() {
    	assertTrue(GetStarted.hasRead);
       game = new Game(MapReader.TEST_MAP);
        player = (IPlayer) game.setCurrent(new Location(2, 3));
        board = new GameView(game, player);
        loc = game.getCurrentLocation();
    }

    @Test
    void PlayerDoesMoves() {
        player.keyPressed(board, KeyCode.UP);
        assertEquals(loc.getNeighbor(GridDirection.NORTH), game.getCurrentLocation());
    }

    @Test
    void PlayerTakesDamage() {
        var hp = player.getCurrentHealth();
        player.handleDamage(10);
        assertNotEquals(hp, player.getCurrentHealth());
    }

    @Test
    void PlayerCanPickUpAndDropItem() {
        loc = this.loc.getNeighbor(GridDirection.NORTH);
        assertEquals(1, game.getMap().getAll(loc).size());

        game.setCurrent(player);
        player.keyPressed(board, KeyCode.UP);
        assertEquals(2, game.getMap().getAll(loc).size());

        game.setCurrent(player);
        player.keyPressed(board, KeyCode.P);
        assertEquals(1, game.getMap().getAll(loc).size());

        game.setCurrent(player);
        player.keyPressed(board, KeyCode.D);
        assertEquals(2, game.getMap().getAll(loc).size());
    }
    
    @Test
    void PlayerPickUpHasItem() {
        loc = this.loc.getNeighbor(GridDirection.NORTH);
        assertEquals(1, game.getMap().getAll(loc).size());

        player.keyPressed(board, KeyCode.UP);
        assertEquals(2, game.getMap().getAll(loc).size());

        List<IItem> localNonActorItemList = board.getLocalNonActorItems();
		assertFalse(localNonActorItemList.isEmpty(), "Something is wrong with the test object, there are no items to pick up.");

		IItem localItem = localNonActorItemList.get(0);
		
		game.setCurrent(player);
        player.keyPressed(board, KeyCode.P);
        assertTrue(player.hasItem(localItem), "Player should have picked up the item: " + localItem);
    }

    @Test
    void PlayerPickUpDropHasItem() {
        Location neighbor = this.loc.getNeighbor(GridDirection.NORTH);
        assertEquals(1, game.getMap().getAll(neighbor).size());

        player.keyPressed(board, KeyCode.UP);
        assertEquals(2, game.getMap().getAll(neighbor).size());

        List<IItem> localNonActorItemList = board.getLocalNonActorItems();
		assertFalse(localNonActorItemList.isEmpty(), "Something is wrong with the test object, there are no items to pick up.");

		IItem localItem = localNonActorItemList.get(0);
		
		game.setCurrent(player);
        player.keyPressed(board, KeyCode.P);
        assertTrue(player.hasItem(localItem), "Player should have picked up the item: " + localItem);
        
        game.setCurrent(player);
        player.keyPressed(board, KeyCode.D);
        assertFalse(player.hasItem(localItem), "Player should have dropped the item: " + localItem);
    }
    
    @Test
    void PlayerPickUpHasItemReferenceEqualityItem() {
        loc = this.loc.getNeighbor(GridDirection.NORTH);
        assertEquals(1, game.getMap().getAll(loc).size());

        player.keyPressed(board, KeyCode.UP);
        assertEquals(2, game.getMap().getAll(loc).size());

        List<IItem> localNonActorItemList = board.getLocalNonActorItems();
		assertFalse(localNonActorItemList.isEmpty(), "Something is wrong with the test object, there are no items to pick up.");

		IItem localItem = localNonActorItemList.get(0);
		IItem notLocalItem = ItemFactory.createItem(localItem.getSymbol());
		game.setCurrent(player);
        player.keyPressed(board, KeyCode.P);
        assertTrue(player.hasItem(localItem), "Player should have picked up the item: " + localItem);
        assertFalse(player.hasItem(notLocalItem), "Wrong item equality.");
    }
    
    @Test
    void PlayerCanPickUpAndDropMoreItems() {
        loc = this.loc.getNeighbor(GridDirection.NORTH);

        assertEquals(1, game.getMap().getAll(loc).size());

        player.keyPressed(board, KeyCode.UP);
        assertEquals(2, game.getMap().getAll(loc).size());

        game.setCurrent(player);
        player.keyPressed(board, KeyCode.P);
        assertEquals(1, game.getMap().getAll(loc).size());

        game.setCurrent(player);
        player.keyPressed(board, KeyCode.RIGHT);
        loc = game.getCurrentLocation();
        assertEquals(2, game.getMap().getAll(loc).size());

        game.setCurrent(player);
        player.keyPressed(board, KeyCode.P);
        assertEquals(1, game.getMap().getAll(loc).size());

        game.setCurrent(player);
        player.keyPressed(board, KeyCode.D);
        assertEquals(2, game.getMap().getAll(loc).size());

        game.setCurrent(player);
        player.keyPressed(board, KeyCode.D);
        assertEquals(3, game.getMap().getAll(loc).size());
    }
}
