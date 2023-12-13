package inf101.rogue101;

import java.io.PrintWriter;
import java.io.StringWriter;

import inf101.gfx.Screen;
import inf101.gfx.gfxmode.IBrush;
import inf101.gfx.textmode.Printer;
import inf101.gfx.textmode.TextMode;
import inf101.grid.Location;
import inf101.rogue101.game.EmojiFactory;
import inf101.rogue101.game.Game;
import inf101.rogue101.game.GameGraphics;
import inf101.rogue101.map.MapReader;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class VisualizeRabbitStrategy extends Application{


	// you might want to tune these options
	public static final boolean MAIN_USE_BACKGROUND_GRID = true;
	public static final boolean MAP_AUTO_SCALE_ITEM_DRAW = true;
	public static final boolean MAP_DRAW_ONLY_DIRTY_CELLS = false;
	public static final TextMode MAIN_TEXT_MODE = TextMode.MODE_80X25;

	public static final boolean DEBUG_TIME = false;

	public static final int LINE_MAP_BOTTOM = 20;
	public static final int LINE_STATUS = 21;
	public static final int LINE_MSG1 = 22;
	public static final int LINE_MSG2 = 23;
	public static final int LINE_MSG3 = 24;
	public static final int LINE_DEBUG = 25;
	public static final int COLUMN_MAP_END = 40;
	public static final int COLUMN_RIGHTSIDE_START = 41;

	public static void launch(String[] args) {
		Application.launch(args);
	}

	private Screen screen;
	private IBrush painter;
	private Printer printer;

	private Game game;

	private boolean grid = MAIN_USE_BACKGROUND_GRID;
	private boolean autoNextTurn = false;
	private Timeline bigStepTimeline;
	private Timeline smallStepTimeline;

	int moves=0;
	Location previous;
	
	private void setup() {
		//
		GameGraphics graphics = new GameGraphics(painter, printer);
		game = new Game(graphics,MapReader.readItems(MapReader.CARROT_HUNT));
		previous = game.getCurrentLocation();
		game.draw();

		//
		bigStepTimeline = new Timeline();
		bigStepTimeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame kf = new KeyFrame(Duration.millis(1000), (ActionEvent event) -> {
			if (autoNextTurn) {
				doTurn();
			}
		});
		bigStepTimeline.getKeyFrames().add(kf);
		// bigStepTimeline.playFromStart();

		//
		smallStepTimeline = new Timeline();
		smallStepTimeline.setCycleCount(1);
		kf = new KeyFrame(Duration.millis(1), (ActionEvent event) -> {
			doTurn();
		});
		smallStepTimeline.getKeyFrames().add(kf);

		// finally, start game
		doTurn();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		screen = Screen.startPaintScene(primaryStage, Screen.CONFIG_PIXELS_STEP_SCALED); // Screen.CONFIG_SCREEN_FULLSCREEN_NO_HINT);

		printer = screen.createPrinter();
		painter = screen.createPainter();
		printer.setTextMode(MAIN_TEXT_MODE, true);

		// Font with emojis – need separate download
		if (EmojiFactory.USE_EMOJI) {
			if (Printer.FONT_SYMBOLA.getFont().getName().equals("Symbols")) {
				printer.setFont(Printer.FONT_SYMBOLA);
			} else {
				EmojiFactory.USE_EMOJI = false;
			}
		}

		if (grid)
			printer.drawCharCells();
		printer.setAutoScroll(false);
		screen.setKeyPressedHandler((KeyEvent event) -> {
			KeyCode code = event.getCode();
			if (event.isShortcutDown()) {
				if (code == KeyCode.Q) {
					System.exit(0);
				} else if (code == KeyCode.R) {
					printer.cycleMode(true);
					if (grid)
						printer.drawCharCells();
					game.draw();
					printer.redrawDirty();
					return true;
				} else if (code == KeyCode.A) {
					screen.cycleAspect();
					if (grid)
						printer.drawCharCells();
					return true;
				} else if (code == KeyCode.G) {
					grid = !grid;
					if (grid)
						printer.drawCharCells();
					else
						screen.clearBackground();
					return true;
				} else if (code == KeyCode.F) {
					screen.setFullScreen(!screen.isFullScreen());
					return true;
				} else if (code == KeyCode.L) {
					printer.redrawTextPage();
					return true;
				}
			} else if (code == KeyCode.ENTER) {
				try {
					doTurn();
				} catch (Exception e) {
					printer.printAt(1, 25, "Exception: " + e.getMessage(), Color.RED);
					e.printStackTrace();
				}
				return true;
			} else if (code.isModifierKey() || code == KeyCode.UNDEFINED) { // ignore shift, alt, ctrl
				return true;
			} else {
				try {
					System.out.println(code);
					game.keyPressed(code);
					doTurn();
				} catch (Exception e) {
					e.printStackTrace();
					try {
						StringWriter sw = new StringWriter();
						PrintWriter writer = new PrintWriter(sw);
						e.printStackTrace(writer);
						writer.close();
						String trace = sw.toString().split("\n")[0];
						game.getIMessageView().displayDebug("Exception: " + trace);
					} catch (Exception e2) {
						System.err.println("Also got this exception trying to display the previous one");
						e2.printStackTrace();
						game.getIMessageView().displayDebug("Exception: " + e.getMessage());
					}
				}
				printer.redrawDirty();
				return true;
			}
			return false;
		});
		/*
		 * screen.setKeyTypedHandler((KeyEvent event) -> { if (event.getCharacter() !=
		 * KeyEvent.CHAR_UNDEFINED) { printer.print(event.getCharacter()); return true;
		 * } return false; });
		 */
		setup();

		primaryStage.show();

	}

	public void doTurn() {
		long t = System.currentTimeMillis();
		boolean waitForPlayer = game.doTurn();
		if(!game.getCurrentLocation().equals(previous)) {
			moves++;
			System.out.println("moved: "+previous+" -> "+game.getCurrentLocation());
			previous = game.getCurrentLocation();
		}
		else {
			game.getIMessageView().displayDebug("Rabbit did not move");
		}
			
		long t2 = System.currentTimeMillis();
		
		if (DEBUG_TIME)
			System.out.println("doTurn() took " + (t2 - t) + "ms");
		
		game.draw();
		printer.redrawDirty();
		if (DEBUG_TIME) {
			System.out.println("draw() took " + (System.currentTimeMillis() - t2) + "ms");
			System.out.println("doTurn()+draw() took " + (System.currentTimeMillis() - t) + "ms");
			System.out.println("waiting for player? " + waitForPlayer);
		}
		
		int hp = game.getCurrentActor().getCurrentHealth();

		if(hp>0) {
			game.getIMessageView().displayStatus("Rabbit has "+hp+" energy left.");
		}
		else {
			game.getIMessageView().displayStatus("Rabbit is out of energy after "+moves+" moves.");
		}
		if (!waitForPlayer)
			smallStepTimeline.playFromStart(); // this will kickstart a new turn in a few milliseconds
	}
}
