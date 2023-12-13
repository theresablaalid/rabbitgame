package inf101.rogue101.game;

import inf101.gfx.textmode.Printer;
import inf101.rogue101.objects.IItem;
import javafx.scene.paint.Color;

public class ColorEmojiFactory extends EmojiFactory {

	Color color;
	
	public ColorEmojiFactory(Color color) {
		this.color = color;
	}
	
	@Override
	public String getEmoji(IItem item) {
		return Printer.coloured(super.getEmoji(item), color);
	}
}
