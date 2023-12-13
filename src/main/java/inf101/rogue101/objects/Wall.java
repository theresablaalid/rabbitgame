package inf101.rogue101.objects;

import inf101.gfx.textmode.BlocksAndBoxes;

public class Wall implements IItem {
	/**
	 * char representation of this type 
	 */
	public static final char SYMBOL = '#';
	private int hp = getMaxHealth();

	@Override
	public int getCurrentHealth() {
		return hp;
	}

	@Override
	public int getDefence() {
		return 10000;
	}

	@Override
	public int getMaxHealth() {
		return 10000;
	}
	
	@Override
	public String getShortName() {
		return getLongName();
	}

	@Override
	public String getLongName() {
		return "wall";
	}

	@Override
	public int getSize() {
		return 10000;
	}

	@Override
	public String getGraphicTextSymbol() {
		return BlocksAndBoxes.BLOCK_FULL;
	}

	@Override
	public int handleDamage(int amount) {
		hp -= amount;
		return amount;
	}
	
	@Override
	public char getSymbol() {
		return SYMBOL;
	}
}
