package inf101.rogue101.objects;

import inf101.GetStarted;
import inf101.grid.Location;
import inf101.rogue101.game.Game;
import inf101.rogue101.map.MapReader;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpiderTest {

    @Test
    void ConstructorTest() {
    	assertTrue(GetStarted.hasRead);
        Spider spider = new Spider();
        assertEquals(spider.getMaxHealth(), spider.getCurrentHealth());
    }

    @Test
    void SpiderKillsPlayer() {
    	assertTrue(GetStarted.hasRead);
        Game game = new Game(MapReader.playerTrapWith('S'));
        IPlayer player = (IPlayer) game.setCurrent(new Location(2, 2));

        for (int i = 0; i < 1000; i++) 
        	game.doTurn();

        assertEquals(-1, player.getCurrentHealth());
    }

//    @Test
//    void SpiderHasShield() {
//        Game game = new Game(MapReader.mapTrap('S'));
//        Spider spider = (Spider) game.setCurrent(new Location(1, 1));
//        assertEquals(10, spider.getCurrentHealth());
//        spider.handleDamage(5);
//        assertEquals(6, spider.getCurrentHealth());
//    }
}
