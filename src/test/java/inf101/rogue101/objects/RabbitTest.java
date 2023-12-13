package inf101.rogue101.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import inf101.GetStarted;
import inf101.grid.Location;
import inf101.rogue101.game.Game;
import inf101.rogue101.map.IMapView;
import inf101.rogue101.map.MapReader;

/**
 * @author Martin Vatshelle
 *
 */
public class RabbitTest {

	@Test
	void testConstructor(){
    	assertTrue(GetStarted.hasRead);
		Rabbit rabbit = new Rabbit();
		assertEquals(rabbit.getMaxHealth(), rabbit.getCurrentHealth());
	}

	@Test
	void CuteRabbitIsHarmless() {
    	assertTrue(GetStarted.hasRead);
		Game game = new Game(MapReader.playerTrapWith('R'));
		IPlayer player = (IPlayer) game.setCurrent(new Location(2, 2));

		for (int i = 0; i < 1000; i++) game.doTurn();

		assertEquals(100, player.getCurrentHealth());
	}
	
	public String testDoTurnMap = "6 5\n" //
			+ "######\n" //
			+ "#    #\n" //
			+ "# RC #\n" //
			+ "#    #\n" //
			+ "######\n";
	@Test
	void testMakesMove() {
    	assertTrue(GetStarted.hasRead);
		Game game = new Game(testDoTurnMap);
		IActor rabbit = game.getMap().getActors(new Location(2,2)).get(0);
		
		itemMoved(game, rabbit);
	}

	private boolean itemMoved(Game game, IItem rabbit) {
    	assertTrue(GetStarted.hasRead);
		IMapView map = game.getMap();
		Location before = map.getLocation(rabbit);
		assertEquals(new Location(2,2), before);
		game.doTurn();
		Location after = game.getMap().getLocation(rabbit);
		
		return before.equals(after);
	}
}
