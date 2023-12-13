package inf101.rogue101.map;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Scanner;

import inf101.grid.Grid;
import inf101.grid.IGrid;
import inf101.grid.Location;
import inf101.rogue101.game.ItemFactory;
import inf101.rogue101.objects.IItem;

/**
 * A class to read a map from a file. After the file is read, the map is stored
 * as an {@link #IGrid} whose entries are characters. This can then be used to
 * be passed to the constructor in {@link #BDMap}.
 *
 * The first line of the file should could contain two numbers. First the width
 * and then the height of the map. After that follows a matrix of characters
 * describing which object goes where in the map. '*' for wall, ' ' for empty,
 * 'P' for the player, 'b' for a bug, 'r' for a rock, and '#' for sand. For
 * example
 *
 * <pre>
 * {@code
 * 5 6
 * *## p
 * * rr#
 * *####
 * *   *
 * *   d
 *    b
 * }
 * </pre>
 *
 * @author larsjaffke (original boulderdash version, 2017)
 * @author anya (Rogue101 update, 2018)
 * @author Martin Vatshelle (Rogue101 simplified, 2021)
 */
public class MapReader {

	public static String BUILTIN_MAP = "40 20\n" //
			+ "########################################\n" //
			+ "#...... ..C.R ......R.R......... ..R...#\n" //
			+ "#.R@R...... ..........RC..R...... ... .#\n" //
			+ "#.......... ..R......R.R..R........R...#\n" //
			+ "#R. R......... R..R.........R......R.RR#\n" //
			+ "#... ..R........R......R. R........R.RR#\n" //
			+ "###############################....R..R#\n" //
			+ "#. ...R..C. ..R.R..........C.RC....... #\n" //
			+ "#..C.....R..... ........RR R..R.....R..#\n" //
			+ "#...R..R.R....G.........R .R..R........#\n" //
			+ "#.R.....R........RRR.......R.. .C....R.#\n" //
			+ "#.C.. ..R.  ..G..R.RC..C....R...R..C. .#\n" //
			+ "#. R..............R R..R........C.....R#\n" //
			+ "#........###############################\n" //
			+ "# R.........R...C....R.....R...R.......#\n" //
			+ "# R......... R..R.G......R......R.RR..*#\n" //
			+ "#. ..R........R.....R.  ....C...R.RR...#\n" //
			+ "#....RC..R........R......R.RCA.....R...#\n" //
			+ "#.C.... ..... ......... .R..R....R...R.#\n" //
			+ "########################################\n" //
	;
	public static String mapTrap(Character sym) {
		return "3 3\n" //
			 + "###\n" //
			 + "#"+ sym+"#\n" //
			 + "###\n";
	}

	public static String playerTrapWith(Character sym) {
		return "5 5\n" //
				+ "#####\n" //
				+ "#   #\n" //
				+ "# @ #\n" //
				+ "#"+sym+"  #\n" //
				+ "#####\n";
	}

	public static String TEST_MAP = "40 5\n" //
			+ "########################################\n" //
			+ "#...... ..C.R ......R.R......... ..R...#\n" //
			+ "#.R@R...... ..........RC..R...... ... .#\n" //
			+ "#... ..R........R......R. R........R.RR#\n" //
			+ "########################################\n";

	
	public static String CARROT_HUNT = "40 10\n" //
			+ "########################################\n" //
			+ "#...... ..C.. ......C.C....#.... ..CCCC#\n" //
			+ "#.  RC   .. ....   .####..C#..... .CCCC#\n" //
			+ "#       ...........  #.C. C#.......CCCC#\n" //
			+ "#      C..........C###.C.###.......CCCC#\n" //
			+ "#      C...#....C..#C# ............CCCC#\n" //
			+ "#      C...#C...C..###.C..##.......CCCC#\n" //
			+ "#      C ..#....C..  #.C. C#C......CCCC#\n" //
			+ "#        .. ....C..  #.C. C#.......CCCC#\n" //
			+ "########################################\n";	
	
	/**
	 * This method fills the previously initialized {@link #symbolMap} with the
	 * characters read from the file.
	 */
	private static void fillMap(Grid<Character> symbolMap, Scanner in) {
		//we need to go through each location on the grid
		Iterator<Location> locIter = symbolMap.locations();

		//we read line by line and for each character on the line
		while (in.hasNextLine()) {
			for(Character c : in.nextLine().toCharArray()) {
				Location loc = locIter.next();
				symbolMap.set(loc, c);
			}
		}
	}

	/**
	 * Load map from file.
	 * <p>
	 * Files are search for relative to the folder containing the MapReader class.
	 *
	 * @return the dungeon map as a grid of characters read from the file, or null
	 *         if it failed
	 */
	public static IGrid<Character> loadChars(String path) {
		InputStream stream = MapReader.class.getResourceAsStream(path);
		Grid<Character> symbolMap = null;
		if (stream == null)
			return null;
		try (Scanner in = new Scanner(stream, "UTF-8")) {
			symbolMap = readChars(in);
		}
		try {
			stream.close();
		} catch (IOException e) {
		}
		return symbolMap;
	}

	/**
	 * @return reads a map as a grid of characters read from the input string,
	 *         or null if it failed
	 */
	public static IGrid<Character> readChars(String input) {
		Grid<Character> symbolMap = null;
		try (Scanner in = new Scanner(input)) {
			symbolMap = readChars(in);
		}
		return symbolMap;
	}

	public static Grid<Character> readChars(Scanner in) {
		int width = in.nextInt();
		int height = in.nextInt();
		Grid<Character> symbolMap = new Grid<Character>(height, width, ' ');
		in.nextLine();
		fillMap(symbolMap, in);
		return symbolMap;
	}
	
	public static IGrid<IItem> readItems(String input) {
		IGrid<Character> symbolMap = readChars(input);
		return toItemMap(symbolMap);
	}
	
	public static IGrid<IItem> loadItems(String file) {
		IGrid<Character> symbolMap = loadChars(file);
		return toItemMap(symbolMap);
	}
	
	/**
	 * This method converts a Character grid to an IItem grid by calling
	 * the ItemFactory for each location.
	 * The returned grid will have the same dimensions as the input.
	 * 
	 * @param symbolMap a grid of symbols
	 * @return a grid of IItems 
	 */
	public static IGrid<IItem> toItemMap(IGrid<Character> symbolMap){
		IGrid<IItem> itemMap = new Grid<IItem>(symbolMap.numRows(), symbolMap.numColumns());
		
		for(Location loc : symbolMap.locations()) {
			try {
				IItem item = ItemFactory.createItem(symbolMap.get(loc));
				itemMap.set(loc, item);
			}catch(IllegalArgumentException e) {
				System.err.println("Failed to add "+symbolMap.get(loc)+" to the map.");
			}
		}
		return itemMap;		
	}
}
