/**
 * 
 */
package inf101.rogue101.objects;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import inf101.GetStarted;

/**
 * @author Martin Vatshelle
 *
 */
public class CarrotTest {

	Carrot carrot = new Carrot();

	/**
	 * Test that Carrot defence is 0.
	 */
	@Test
	void testGetDefence() {
    	assertTrue(GetStarted.hasRead);
		assertEquals(0, carrot.getDefence());;
	}

	/**
	 * Test that Carrot max health is 10.
	 */
	@Test
	void testGetMaxHealth() {
    	assertTrue(GetStarted.hasRead);
		assertEquals(5, carrot.getMaxHealth());
	}

	/**
	 * Test that Carrot short name is carrot.
	 */
	@Test
	void testGetShortName() {
    	assertTrue(GetStarted.hasRead);
		assertEquals("carrot", carrot.getShortName());
	}

	/**
	 * Test that Carrot long name contains carrot.
	 */
	@Test
	void testGetLongName() {
    	assertTrue(GetStarted.hasRead);
		assertTrue(carrot.getLongName().contains(carrot.getShortName()));
	}

	/**
	 * Test that size of new Carrots is 2.
	 */
	@Test
	void testGetSize() {
    	assertTrue(GetStarted.hasRead);
		assertEquals(2, carrot.getSize());
	}

	/**
	 * Test method for {@link inf101.rogue101.objects.Carrot#handleDamage(int)}.
	 */
	@Test
	void testHandleDamage() {
    	assertTrue(GetStarted.hasRead);
		Carrot carrot = new Carrot();
		int damage = carrot.handleDamage(1);
		assertEquals(1,damage);
		assertEquals(carrot.getMaxHealth()-1,carrot.getCurrentHealth());
		damage = carrot.handleDamage(carrot.getMaxHealth());
		assertEquals(carrot.getMaxHealth()-1, damage);
	}

	/**
	 * Test that Carrot symbol is C.
	 */
	@Test
	void testGetSymbol() {
    	assertTrue(GetStarted.hasRead);
		assertEquals('C',Carrot.SYMBOL);
	}

}
