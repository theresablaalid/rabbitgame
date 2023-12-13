package inf101.rogue101.game;

import inf101.rogue101.objects.IItem;

public class EmojiFactory {

	/**
	 * Set to true if using emojis – you must download and add the TrueType font
	 * file manually.
	 * <p>
	 * The font can be downloaded from
	 * <a href="https://dn-works.com/wp-content/uploads/2020/UFAS-Fonts/Symbola.zip)
	 * <p>
	 * (Put the Symbola.ttf file in src/main/java/inf101/v20/gfx/fonts/ – do
	 * 'Refresh' on your project after adding the file)
	 * 
	 * <p>
	 * Overview
	 * <a href="https://dn-works.com/wp-content/uploads/2020/UFAS-Docs/Symbola.pdf)
	 * <p>
	 * 
	 */
	public static boolean USE_EMOJI = true;
	
	public String getEmoji(IItem item) {
		if (USE_EMOJI) {
			return item.getEmoji();
		} else {
			return item.getGraphicTextSymbol();
		}

	}
}
