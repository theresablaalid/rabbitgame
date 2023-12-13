package inf101.rogue101.objects;

import inf101.gfx.textmode.Printer;
import javafx.scene.paint.Color;

public class Gold implements IItem {

	public static final char SYMBOL = 'G';
	
	@Override
	public int getCurrentHealth() {
		return 1;
	}

	@Override
	public int getDefence() {
		return 8;
	}

	@Override
	public int getMaxHealth() {
		return 1;
	}

	@Override
	public String getLongName() {
		return "very gold Gold";
	}

	@Override
	public String getShortName() {
		return "gold";
	}

	@Override
	public int getSize() {
		return 4;
	}

	@Override
	public String getEmoji() {
		return Printer.coloured("🌟", Color.YELLOW);
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

