package inf101.gfx.gfxmode;

import javafx.scene.paint.Color;

public class DrawHelper {

	public static void drawCarrot(IBrush painter, double h, double w, double sizeInPercent) {
		painter.save();
		painter.turn(75);
		double size = sizeInPercent / 2 + 0.5;
		double length = size * h * .6;
		double width = size * h * .4;
		painter.jump(-length / 6);
		painter.shape().ellipse().width(length).height(width).fillPaint(Color.ORANGE.interpolate(Color.ORANGERED, 0.5))
				.fill();
		painter.jump(length / 2.25);
		painter.setPenSize(2).setInk(Color.FORESTGREEN);
		for (int i = -1; i < 2; i++) {
			painter.save();
			painter.turn(20 * i);
			painter.draw(length / 2.5);
			painter.restore();
		}
		painter.restore();
	}

	public static void drawSpider(IBrush painter, double h, double w, double sizeInPercent) {
		painter.save();
		painter.turn(75);
		double size = sizeInPercent / 2 + 0.5;
		double length = size * h * .4;
		double width = size * h * .4;
		painter.jump(-length / 6);
		painter.shape().ellipse().width(length).height(width).fillPaint(Color.BLACK).fill();
		// painter.jump(length / 2);
		painter.setInk(Color.BLACK);
		for (int j = -1; j < 2; j+=2) {
			painter.save();
			painter.turn(90 * j);
			for (int i = 0; i < 4; i++) {
				painter.save();
				painter.turn(-35 * i);
				painter.jump(length / 2.5);
				painter.setPenSize(1.5).draw(length / 3);
				painter.turn(Math.signum(j)*30);
				painter.setPenSize(.75).draw(length / 3);
				painter.restore();
			}
			painter.restore();
		}
		painter.restore();

	}
}
