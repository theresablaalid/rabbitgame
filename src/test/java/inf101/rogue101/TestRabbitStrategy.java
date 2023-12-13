package inf101.rogue101;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import inf101.GetStarted;
import inf101.grid.Location;
import inf101.rogue101.game.Game;
import inf101.rogue101.map.MapReader;
import inf101.rogue101.objects.Carrot;
import inf101.rogue101.objects.Rabbit;

@TestInstance(Lifecycle.PER_CLASS)
public class TestRabbitStrategy {

	double movesAvg = 0;
	int movesTotal = 0;
	int NUMBER_OF_SIMULATIONS = 10;

	/**
	 * This test sets up a game with lots of Carrots and 1 rabbit The game runs as
	 * long as the Rabbit has more energy. The object of the rabbit is to
	 * survive as many moves as possible.
	 * 
	 * @throws Exception
	 */
	@BeforeAll
	void setUp() throws Exception {
		movesAvg = 0;
		for (int i = 0; i < NUMBER_OF_SIMULATIONS; i++) {
			runSimulation();
		}		
		movesAvg = movesTotal / NUMBER_OF_SIMULATIONS;
	}

    @BeforeEach
    void beforeEach() {
    	assertTrue(GetStarted.hasRead);
    }

    private void runSimulation() {
		Game game = new Game(MapReader.CARROT_HUNT);
		Rabbit rabbit = (Rabbit) game.getCurrentActor();
		Location previous = game.getCurrentLocation();
		int previousHP = rabbit.getCurrentHealth();
		int carrots = 0;
		int eaten = 0;
		int moves = 0;
		int turnsLeft = 1000;
		while (rabbit.getCurrentHealth() > 0 && turnsLeft>0) {
			game.doTurn();
			turnsLeft--;
			if (game.getCurrentActor() == rabbit) {
				if (game.containsItem(game.getCurrentLocation(),Carrot.class)) {
					carrots++;
				}
				if (!game.getCurrentLocation().equals(previous)) {
					moves++;
					previous = game.getCurrentLocation();
				}
				else {
					if(previousHP>=rabbit.getCurrentHealth())
						System.err.println("Move from "+previous+" to "+game.getCurrentLocation()+" does not count.");
					else
						eaten += previousHP-rabbit.getCurrentHealth();
				}
				previousHP = rabbit.getCurrentHealth();

			} else {
				System.err.println("Strange, how has other actors entered the game?");
				fail("Not allowed to reproduce");
			}
			assertTrue(moves<=5*carrots+10,"Rabbit is moving without burning energy. Made "+moves+" moves and ate "+carrots+" carrots.");
		}
		movesTotal += moves;
	}

	private void checkLevel(int target) {
		assertTrue(movesAvg > target,"Rabbit made "+movesAvg+" moves but needed i to pass.");
	}

	@Test
	void level1() {
		checkLevel(10);
	}

	@Test
	void level2() {
		checkLevel(15);
	}

	@Test
	void level3() {
		checkLevel(20);
	}

	@Test
	void level4() {
		checkLevel(25);
	}

	@Test
	void level5() {
		checkLevel(30);
	}

	@Test
	void level6() {
		checkLevel(40);
	}

	@Test
	void level7() {
		checkLevel(60);
	}

	@Test
	void level8() {
		checkLevel(80);
	}

	@Test
	void level9() {
		checkLevel(100);
		System.out.println("Congratulations! You made "+movesAvg+" moves.");
	}

	@Test
	void checkRabbitTooSmart() {
		assertTrue(movesAvg<200,"Rabbit is too smart, makes game unrealistic!");
	}

}
