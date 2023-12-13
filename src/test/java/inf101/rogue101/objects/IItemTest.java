package inf101.rogue101.objects;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import inf101.GetStarted;
import inf101.rogue101.game.ItemFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class IItemTest {

	/**
	 * Tests that getArticle returns one of the three possible values.
	 * This test does not test that the grammatical choice of a vs. an is correct
	 */
	@Test
	void testGetArticle() {
		runTest(this::testGetArticle);
	}
	void testGetArticle(IItem item) {
		assertTrue(item.getArticle().equals("a") || item.getArticle().equals("an") || item.getArticle().equals(""),"for "+item);
	}

	/**
	 * This tests that newly created items have full health (are new)
	 */
	@Test
	void testNewGetCurrentHealth() {
    	assertTrue(GetStarted.hasRead);
		runTestNewOnly(this::testNewGetCurrentHealth);
	}
	void testNewGetCurrentHealth(IItem item) {
		assertTrue(item.getCurrentHealth()==item.getMaxHealth(),"for "+item);
	}
	
	/**
	 * This test tests that defence is a non negative integer
	 */
	@Test
	void testGetDefence() {
		runTest(this::testGetDefence);
	}
	void testGetDefence(IItem item) {
		assertTrue(item.getDefence()>=0,"for "+item);
	}

	/**
	 * This test checks that health status is a number between 0.0 and 1.0
	 */
	@Test
	void testGetHealthStatus() {
		runTest(this::testGetHealthStatus);
	}
	void testGetHealthStatus(IItem item) {
		if(item.getCurrentHealth() == item.getMaxHealth())
			assertTrue(item.getHealthStatus()==1.0,"for "+item);
		else if(item.getCurrentHealth() <= 0)
			assertTrue(item.getHealthStatus()>=0.0,"for "+item + " with health status " + item.getHealthStatus() + " and maxHealth " + item.getMaxHealth());
		else
			assertTrue(item.getHealthStatus()>0.0 && item.getHealthStatus()<1.0,"for "+item + " with health status " + item.getHealthStatus() + " and maxHealth " + item.getMaxHealth());
	}

	/**
	 * Tests that max health is at least 1
	 */
	@Test
	void testGetMaxHealth() {
		runTest(this::testGetMaxHealth);
	}
	void testGetMaxHealth(IItem item) {
		assertTrue(item.getMaxHealth()>=1,"Item "+item.getShortName()+" has <=0 max health");
	}

	/**
	 * Checks that this item returns a non empty name
	 */
	@Test
	void testGetName() {
		runTest(this::testGetShortName);
		runTest(this::testGetLongName);
	}
	void testGetShortName(IItem item) {
		assertFalse(item.getShortName().isEmpty(),"Short name is empty for "+item);
	}
	void testGetLongName(IItem item) {
		assertFalse(item.getLongName().isEmpty(),"Long name is empty for "+item);
	}

	/**
	 * Tests that size is larger than 0
	 */
	@Test
	void testGetSize() {
		runTest(this::testGetSize);
	}
	void testGetSize(IItem item) {
		assertTrue(item.getSize()>0,"for "+item);
	}

	/**
	 * Tests that handle damage never increases the health
	 */
	@Test
	void testHandleDamage() {
		runTest(this::testHandleDamage);
	}
	void testHandleDamage(IItem item) {
		for(IActor source : IActorTest.getInstances()) {
			int hp = item.getCurrentHealth();
			if(hp > 0) {
				int damage = item.handleDamage(source.getDamage());
				assertTrue(damage >= 0, "damage >= 0");
				System.out.println(item+" took "+damage+" damage");
				assertTrue(item.getCurrentHealth()<=hp-damage,"for "+item);
				assertTrue(item.isDestroyed() || item.getCurrentHealth()==(hp-damage),"for "+item);
				assertTrue(damage <= hp,"Item "+item+" took "+damage+" damage with "+hp+" health");
			}
		}
	}

	@Test
	void testIsDestroyed() {
		runTest(this::testIsDestroyed);
	}
	void testIsDestroyed(IItem item) {
		assertNotEquals(item.getCurrentHealth()>=0,item.isDestroyed(),"for "+item);
	}

	@Test
	void testCompareToReverse() {
		runTest(this::testCompareToReverse);
	}
	void testCompareToReverse(IItem item) {
		for(IItem other : getTestData(false)) {
			IItemComparator comp = new IItemComparator();
			assertEquals(-comp.compare(other, item), comp.compare(item, other),"for "+item);
		}
	}

	@Test
	void testCompareToCycle() {
		runTest(this::testCompareToCycle);
	}
	void testCompareToCycle(IItem item) {
		IItemComparator comp = new IItemComparator();
		for(IItem a : getTestData(false)) {
			for(IItem b : getTestData(false)) {
				if(comp.compare(a, b) ==0)
					assertEquals(comp.compare(item, b), comp.compare(item, a),"for "+item);
				else if(comp.compare(item, a)>=0 && comp.compare(item, b)<=0)
					assertTrue(comp.compare(a, b)<=0,"for "+item);
			}
		}
	}

	@Test
	void testItemFactoryCreatesDust() {
    	assertTrue(GetStarted.hasRead);
		assertTrue(ItemFactory.createItem('.') instanceof Dust);
	}
	
	public static final char GOLD_SYMBOL = 'G';

	@Test
	void testItemFactoryCreatesGold() {
    	assertTrue(GetStarted.hasRead);
		IItem gold = ItemFactory.createItem(GOLD_SYMBOL);
		assertNotNull(gold);
	}

	/**
	 * This method runs a test on a list of IItems
	 * @param test
	 */
	void runTest(Consumer<IItem> test) {
    	assertTrue(GetStarted.hasRead);
		for(IItem item : getTestData(false))
			test.accept(item);
	}

	/**
	 * This method runs a test on a list of IItems
	 * @param test
	 */
	void runTestNewOnly(Consumer<IItem> test) {
		for(IItem item : getTestData(true))
			test.accept(item);
	}
	
	/**
	 * This method returns a list of new IItems of different types
	 * @return
	 */
	List<IItem> getTestData(boolean newItemsOnly) {
		char[] itemSymbols = { Dust.SYMBOL, Wall.SYMBOL, Carrot.SYMBOL, Rabbit.SYMBOL, Spider.SYMBOL, Gold.SYMBOL };

		List<IItem> list = new ArrayList<>();

		for (char symbol : itemSymbols) {
			try {
				IItem item = ItemFactory.createItem(symbol);
				list.add(item);
	
				if(!newItemsOnly) {
					// create a damaged version
					item = ItemFactory.createItem(symbol);
					item.handleDamage(item.getMaxHealth() / 2);
					list.add(item);
		
					// create a dead version
					item = ItemFactory.createItem(symbol);
					item.handleDamage(item.getMaxHealth() * 2);
					list.add(item);
				}
			} catch (IllegalArgumentException e) {
				System.out.println("Can not test "+symbol);
			}
			
		}
		return list;
	}
}
