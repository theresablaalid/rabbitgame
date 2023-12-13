package inf101.gfx.gfxmode;

import java.util.ArrayList;
import java.util.List;

import inf101.gfx.IPaintLayer;
import inf101.gfx.Screen;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

public class BrushPainter implements IPaintLayer, IBrush {

	static class TurtleState {
		protected Point pos;
		protected Direction dir;
		protected Direction inDir;
		protected double penSize = 1.0;
		protected Paint ink = Color.BLACK;

		public TurtleState() {
		}

		public TurtleState(TurtleState s) {
			pos = s.pos;
			dir = s.dir;
			inDir = s.inDir;
			penSize = s.penSize;
			ink = s.ink;
		}
	}

	private final Screen screen;
	private final double width;
	private final double height;
	private final GraphicsContext context;
	private final List<TurtleState> stateStack = new ArrayList<>();

	private TurtleState state = new TurtleState();
	private final Canvas canvas;
	private boolean path = false;

	public BrushPainter(double width, double height) {
		this.screen = null;
		this.canvas = null;
		this.context = null;
		this.width = width;
		this.height = height;
		stateStack.add(new TurtleState());
		state.dir = new Direction(1.0, 0.0);
		state.pos = new Point(width / 2, height / 2);
	}

	public BrushPainter(Screen screen, Canvas canvas) {
		if (screen == null || canvas == null)
			throw new IllegalArgumentException();
		this.screen = screen;
		this.canvas = canvas;
		this.context = canvas.getGraphicsContext2D();
		this.width = screen.getWidth();
		this.height = screen.getHeight();
		stateStack.add(new TurtleState());
		state.dir = new Direction(1.0, 0.0);
		state.pos = new Point(screen.getWidth() / 2, screen.getHeight() / 2);
		context.setLineJoin(StrokeLineJoin.BEVEL);
		context.setLineCap(StrokeLineCap.SQUARE);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T as(Class<T> clazz) {
		if (clazz == GraphicsContext.class)
			return (T) context;
		if (clazz == getClass())
			return (T) this;
		else
			return null;
	}

	@Override
	public void clear() {
		if (context != null)
			context.clearRect(0, 0, getWidth(), getHeight());
	}

	@Override
	public IBrush curveTo(Point to, double startControl, double endAngle, double endControl) {
		Point c1 = state.pos.move(state.dir, startControl);
		Point c2 = to.move(Direction.fromDegrees(endAngle + 180), endControl);
		if (context != null) {
			if (!path) {
				// context.save();
				context.setStroke(state.ink);
				context.setLineWidth(state.penSize);
				context.beginPath();
				context.moveTo(state.pos.getX(), state.pos.getY());
			}
			context.bezierCurveTo(c1.getX(), c1.getY(), c2.getX(), c2.getY(), to.getX(), to.getY());
		}
		state.inDir = state.dir;
		state.pos = to;
		state.dir = Direction.fromDegrees(endAngle);

		if (!path && context != null) {
			context.stroke();
			// context.restore();
		}
		return this;
	}

	@Override
	public void debugTurtle() {
		System.err.println("[" + state.pos + " " + state.dir + "]");
	}

	@Override
	public IBrush draw(double dist) {
		Point to = state.pos.move(state.dir, dist);
		return drawTo(to);
	}

	@Override
	public IBrush draw(Point relPos) {
		Point to = state.pos.move(relPos);
		return drawTo(to);
	}

	@Override
	public IBrush drawTo(double x, double y) {
		Point to = new Point(x, y);
		return drawTo(to);
	}

	@Override
	public IBrush drawTo(Point to) {
		if (path && context != null) {
			context.setStroke(state.ink);
			context.setLineWidth(state.penSize);
			context.lineTo(to.getX(), to.getY());
		} else {
			line(to);
		}
		state.inDir = state.dir;
		state.pos = to;
		return this;
	}

	@Override
	public double getAngle() {
		return state.dir.toDegrees();
	}

	@Override
	public Direction getDirection() {
		return state.dir;
	}

	@Override
	public double getHeight() {
		return height;
	}

	@Override
	public Point getPos() {
		return state.pos;
	}

	public Screen getScreen() {
		return screen;
	}

	@Override
	public double getWidth() {
		return width;
	}

	@Override
	public IBrush jump(double dist) {
		state.inDir = state.dir;
		state.pos = state.pos.move(state.dir, dist);
		if (path && context != null)
			context.moveTo(state.pos.getX(), state.pos.getY());
		return this;
	}

	@Override
	public IBrush jump(Point relPos) {
		// TODO: state.inDir = state.dir;
		state.pos = state.pos.move(relPos);
		if (path && context != null)
			context.moveTo(state.pos.getX(), state.pos.getY());

		return this;
	}

	@Override
	public IBrush jumpTo(double x, double y) {
		state.inDir = state.dir;
		state.pos = new Point(x, y);
		return this;
	}

	@Override
	public IBrush jumpTo(Point to) {
		state.inDir = state.dir;
		state.pos = to;
		return this;
	}

	@Override
	public void layerToBack() {
		if (screen != null)
			screen.moveToBack(this);
	}

	@Override
	public void layerToFront() {
		if (screen != null)
			screen.moveToFront(this);
	}

	@Override
	public IBrush line(Point to) {
		if (context != null) {
			// context.save();
			context.setStroke(state.ink);
			context.setLineWidth(state.penSize);
			context.strokeLine(state.pos.getX(), state.pos.getY(), to.getX(), to.getY());
			// context.restore();
		}
		return this;
	}

	@Override
	public IPainter restore() {
		if (stateStack.size() > 0) {
			state = stateStack.remove(stateStack.size() - 1);
		}
		return this;
	}

	@Override
	public IPainter save() {
		stateStack.add(new TurtleState(state));
		return this;
	}

	@Override
	public IPainter setInk(Paint ink) {
		state.ink = ink;
		return this;
	}

	@Override
	public IBrush setPenSize(double pixels) {
		if (pixels < 0)
			throw new IllegalArgumentException("Negative: " + pixels);
		state.penSize = pixels;
		return this;
	}

	@Override
	public IShape shape() {
		ShapePainter s = new ShapePainter(context);
		return s.at(getPos()).rotation(getAngle()).strokePaint(state.ink);
	}

	@Override
	public IBrush turn(double degrees) {
		state.dir = state.dir.turn(degrees);
		return this;
	}

	@Override
	public IBrush turnAround() {
		return turn(180);
	}

	@Override
	public IBrush turnLeft() {
		return turn(90);
	}

	@Override
	public IBrush turnLeft(double degrees) {
		if (degrees < 0)
			throw new IllegalArgumentException("Negative: " + degrees + " (use turn())");
		state.dir = state.dir.turn(degrees);
		return this;
	}

	@Override
	public IBrush turnRight() {
		return turn(-90);
	}

	@Override
	public IBrush turnRight(double degrees) {
		if (degrees < 0)
			throw new IllegalArgumentException("Negative: " + degrees + " (use turn())");
		state.dir = state.dir.turn(-degrees);
		return this;
	}

	@Override
	public IBrush turnTo(double degrees) {
		state.dir = state.dir.turnTo(degrees);
		return this;
	}

	@Override
	public IBrush turnTowards(double degrees, double percent) {
		state.dir = state.dir.turnTowards(new Direction(degrees), percent);
		return this;
	}

	@Override
	public IBrush turtle() {
		BrushPainter painter = screen != null ? new BrushPainter(screen, canvas) : new BrushPainter(width, height);
		painter.stateStack.set(0, new TurtleState(state));
		return painter;
	}

	public IBrush beginPath() {
		if (path)
			throw new IllegalStateException("beginPath() after beginPath()");
		path = true;
		if (context != null) {
			context.setStroke(state.ink);
			context.beginPath();
			context.moveTo(state.pos.getX(), state.pos.getY());
		}
		return this;
	}

	public IBrush closePath() {
		if (!path)
			throw new IllegalStateException("closePath() without beginPath()");
		if (context != null)
			context.closePath();
		return this;
	}

	public IBrush endPath() {
		if (!path)
			throw new IllegalStateException("endPath() without beginPath()");
		path = false;
		if (context != null)
			context.stroke();
		return this;
	}

	public IBrush fillPath() {
		if (!path)
			throw new IllegalStateException("fillPath() without beginPath()");
		path = false;
		if (context != null) {
			context.save();
			context.setFill(state.ink);
			context.fill();
			context.restore();
		}
		return this;
	}

	@Override
	public Paint getInk() {
		return state.ink;
	}
}
