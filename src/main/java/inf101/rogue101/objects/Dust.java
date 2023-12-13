package inf101.rogue101.objects;

import inf101.gfx.textmode.BlocksAndBoxes;

/**
 * Støv i Rogue101-spillet. 
 * 
 * Støv har ingen annen funksjon enn å dekke gulvet. 
 * 
 * En potensiell utvidelse er at støv flytter seg når man beveger seg gjennom det
 * og oppstår hvis ingen har gått gjennom ruten på to runder. 
 * 
 * @author Anna Eilertsen - anna.eilertsen@uib.no
 *
 */
public class Dust implements IItem {
	/**
	 * char representation of this type 
	 */
	public static final char SYMBOL = '.';


	@Override
	public String getArticle() {
		return "";
	}
	
	@Override
	public int getCurrentHealth() {
		return 1;
	}

	@Override
	public int getDefence() {
		return 0;
	}

	@Override
	public int getMaxHealth() {
		return 1;
	}

	@Override
	public String getShortName() {
		return "dust";
	}
	
	@Override
	public String getLongName() {
		return "lots of dust";
	}

	@Override
	public int getSize() {
		return 1;
	}

	@Override
	public String getGraphicTextSymbol() {
		return BlocksAndBoxes.BLOCK_HALF;
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
