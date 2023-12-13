package inf101.rogue101.objects;

import inf101.gfx.gfxmode.IBrush;

/**
 * An {@link IItem} is something that can be placed on the map or into a
 * container of items.
 * <p>
 * An {@link IActor} is a special case of {@link IItem} used for things that are
 * “alive”, such as the player or AI-controlled monsters/NPCs.
 * <p>
 * By default, all items have a hit point / health system (they can be damaged),
 * and can be picked up. So you could potentially destroy an item or pick up a
 * monster.
 *
 * @author anya
 */
public interface IItem {


	/**
	 * Draw this item on the screen.
	 * <p>
	 * The turtle-painter will be positioned in the centre of the cell. You should
	 * avoid drawing outside the cell (size is indicated by the <code>w</code> and
	 * <code>h</code> parameters, so you can move ±w/2 and ±h/2 from the initial
	 * position). If you want to start in the lower left corner of the cell, you can
	 * start by doing <code>painter.jump(-w/2,h/2)</code> ((0,0) is in the top-left
	 * corner of the screen, so negative X points left and positive Y points down).
	 * <p>
	 * If this method returns <code>true</code>, the game will <em>not</em> print
	 * {@link #getGraphicTextSymbol()} in the cell.
	 * <p>
	 * All calls to <code>painter.save()</code> must be matched by a call to
	 * <code>painter.restore()</code>.
	 *
	 * @param painter
	 *            A turtle-painter for drawing
	 * @param w
	 *            The width of the cell
	 * @param h
	 *            The height of the cell
	 * @return False if the letter from {@link #getGraphicTextSymbol()} should be
	 *         drawn instead
	 */
	default boolean draw(IBrush painter, double w, double h) {
		return false;
	}

	/**
	 * @return "a" or "an", depending on the name to be used for printing
	 *         descriptions to screen
	 */
	default String getArticle() {
		return "a";
	}

	/**
	 * Get current remaining health points.
	 * <p>
	 * An object's <em>health points</em> determines how much damage it can take
	 * before it is destroyed / broken / killed.
	 *
	 * @return Current health points for this item
	 */
	int getCurrentHealth();

	/**
	 * The defence score determines how hard an object/actor is to hit or grab.
	 *
	 * @return Defence score of this object
	 */
	int getDefence();

	/**
	 * Get item health as a 0.0..1.0 proportion.
	 *
	 * <li><code>getHealth() >= 1.0</code> means perfect condition
	 * <li><code>getHealth() <= 0.0</code> means broken or dead
	 * <li><code>0.0 < getHealth() < 1.0</code> means partially damaged
	 *
	 * @return Health, in the range 0.0 to 1.0
	 */
	default double getHealthStatus() {
		if(getCurrentHealth() <= 0)
			return 0;
		double current = getCurrentHealth();
		double max = getMaxHealth();
		return current / max;
	}

	/**
	 * Get maximum health points.
	 *
	 * An object's <em>health points</em> determines how much damage it can take
	 * before it is destroyed / broken / killed.
	 *
	 * @return Max health points for this item
	 */
	int getMaxHealth();

	/**
	 * Get a (user-friendly) name for the item
	 * <p>
	 * Used for things like
	 * <code>"You see " + getArticle() + " " + getLongName()</code>
	 *
	 * @return Item's name in a format that can be used in a descriptive sentence
	 */
	String getLongName();

	/**
	 * Get a shorter (user-friendly) name for the item
	 * <p>
	 * Used for things like <code>"Holding " + getShortName()</code>
	 *
	 * @return Item's name in short format that can be used in a list
	 */
	String getShortName();

	/**
	 * Get the size of the object.
	 * <p>
	 * The size determines how much space an item will use if put into a container.
	 *
	 * @return Size of the item
	 */
	int getSize();

	/**
	 * Get the map symbol of this item.
	 * <p>
	 * The symbol will be used on a text-only map for a textual "graphical"
	 * representation of the item.
	 *
	 * @return A single-codepoint string with the item's symbol
	 */
	default String getGraphicTextSymbol() {
		return "" + getSymbol();
	}

	/**
	 * Get the map emoji symbol of this item.
	 * <p>
	 * The symbol can be used for a textual "graphical"
	 * representation of the item when emoji compatible font is available.
	 * <p>
	 * The symbol should be a single Unicode codepoint (i.e.,
	 * <code>getSymbol().codePointCount(0, getSymbol().length()) == 1</code>). In
	 * most cases this means that the symbol should be a single character (i.e.,
	 * getSymbol().length() == 1); but there are a few Unicode characters (such as
	 * many emojis and special symbols) that require two Java <code>char</code>s.
	 *
	 * @return A single-codepoint string with the item's symbol
	 */
	default String getEmoji() {
		return getGraphicTextSymbol();
	}

	/**
	 * Inform the item that it has been damaged
	 * As actors of the game interact with this item they may cause damage to it.
	 * Some items easily take dameage and some may take less damage than 
	 * <code>amount</code> due to armour/protection effects.
	 * 
	 * An item can never take more damage than the health points the item has available.
	 * Also an item may never take less than 0 damage.
	 * 
	 * The current health points should decrease by the amount of damage taken.
	 * @see #isDestroyed()
	 * 
	 * @param amount - How much damage the item should take
	 *
	 * @return Amount of damage actually taken – always less or equal to {@link #getCurrentHealth()}
	 */
	int handleDamage(int amount);

	/**
	 * Items may stay on the board with health 0 (as dead remains).
	 * 
	 * @return True if this item has been destroyed and should be removed from the
	 *         map
	 */
	default boolean isDestroyed() {
		return getCurrentHealth() < 0;
	}

	/**
	 * 
	 * @return the char-representation of an item 
	 */
	char getSymbol();

}
