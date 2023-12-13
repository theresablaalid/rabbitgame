package inf101.rogue101.objects;

import inf101.gfx.gfxmode.DrawHelper;
import inf101.gfx.gfxmode.IBrush;
import inf101.gfx.textmode.Printer;
import inf101.rogue101.game.EmojiFactory;
import javafx.scene.paint.Color;

/**
 * En gulrot i Rogue 101-spillet.  
 * 
 * Gulrøtter dør hvis de blir spist. 
 * 
 * @author Anna Eilertsen - anna.eilertsen@uib.no
 *
 */
public class Carrot implements IItem {
	/**
	 * char representation of this type 
	 */
	public static final char SYMBOL = 'C';
	private int hp = getMaxHealth();

	@Override
	public boolean draw(IBrush painter, double w, double h) {
		if (!EmojiFactory.USE_EMOJI) {
			DrawHelper.drawCarrot(painter, h, w, getHealthStatus());
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int getCurrentHealth() {
		return hp;
	}

	@Override
	public int getDefence() {
		return 0;
	}

	@Override
	public int getMaxHealth() {
		return 5;
	}

	@Override
	public String getShortName() {
		return "carrot";
	}

	@Override
	public String getLongName() {
		return "juicy carrot";
	}

	@Override
	public int getSize() {
		return 2;
	}
	
	@Override
	public String getEmoji() {
		return Printer.coloured("🥕", Color.DARKORANGE);
	}
	
	@Override
	public int handleDamage(int amount) {
		if(amount >= getCurrentHealth()) {
			int damage = getCurrentHealth();
			hp -= damage;
			return damage;
		}
		if(amount<0) {
			throw new IllegalArgumentException("Amount cannot be zero or lower");
		}
		amount -= Math.min(amount, getDefence());
		int damage = Math.min(amount, hp);
		hp -= damage;
		return damage;
	}
	
	@Override
	public char getSymbol() {
		return SYMBOL;
	}
}
