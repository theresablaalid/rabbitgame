package inf101.rogue101.objects;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class ArmourTest {

	@Test
	void ArmourGivesMoreDefence() {
		Player player = new Player();
		assertEquals(player.getDefence(), 2);
		Armour armour = new Armour();
		player.backpack.add(armour);
		assertEquals(player.getDefence(), 4);
	}
}

