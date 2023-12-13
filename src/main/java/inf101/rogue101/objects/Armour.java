package inf101.rogue101.objects;

import inf101.gfx.textmode.Printer;
import javafx.scene.paint.Color;

public class Armour implements IItem{
	
	public static final char SYMBOL = 'x';

	@Override
	public int getCurrentHealth() {
		return 0;
	}

	@Override
	public int getDefence() {
		return 15;
	}

	@Override
	public int getMaxHealth() {
		return 3;
	}

	@Override
	public String getLongName() {
		return "Strong armour";
	}

	@Override
	public String getShortName() {
		return "armour";
	}

	@Override
	public int getSize() {
		return 3;
	}

	public String getEmoji() {
	return Printer.coloured("🛡️", Color.GREEN);
	}

	@Override
	public int handleDamage(int amount) {
		return 0;
	}

	@Override
	public char getSymbol() {
		return SYMBOL;
	}

}
