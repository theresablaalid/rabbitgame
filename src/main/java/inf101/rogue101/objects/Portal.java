package inf101.rogue101.objects;

import inf101.gfx.textmode.Printer;
import inf101.rogue101.game.IGameView;
import javafx.scene.paint.Color;

/**
 * En magisk portal i spillet Rogue101. 
 * 
 * Når du går inn i portalen vinner du spillet. 
 * Men portalen lar seg ikke åpne uten amuletten. 
 * 
 * Portalen endrer utseende hver runde. Den kan ikke angripe.
 * 
 * @author Anna Eilertsen - anna.eilertsen@uib.no
 *
 */
public class Portal implements IActor {
	/**
	 * char representation of this type 
	 */
	public static final char SYMBOL = '*';
	private int hp = getMaxHealth();
	private static String graphicTextSymbol = "*"; 

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
		return "portal";
	}

	@Override
	public int getSize() {
		return 10000;
	}

	@Override
	public String getGraphicTextSymbol() {
		return graphicTextSymbol;
	}

	@Override
	public int handleDamage(int amount) {
		return 0;
	}

	@Override
	public void doTurn(IGameView game) {
		 if (graphicTextSymbol.equals("*"))
			graphicTextSymbol = "o";
		else 
			graphicTextSymbol =  "*"; 
	}
	
	@Override
	public int getAttack() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getDamage() {
		throw new UnsupportedOperationException();
	}

	public void open() {
		hp=-1;
	}
	

	public String getEmoji() {
	return Printer.coloured("🌀", Color.GREEN);
	}
	
	@Override
	public char getSymbol() {
		return SYMBOL;
	}
}
