package inf101.grid;

import inf101.GetStarted;
import inf101.util.IGenerator;
import inf101.util.generators.GridGenerator;
import inf101.util.generators.LocationGenerator;
import inf101.util.generators.StringGenerator;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GridTest {
    private static final int N = 10000;

    private IGenerator<String> strGen = new StringGenerator();
    private IGenerator<IGrid<String>> gridGen = new GridGenerator<String>(strGen);

    public <T> void fillProperty1(IGrid<T> grid, T val) {
        grid.fill(val);
        for (Location l : grid.locations()) {
            assertEquals(val, grid.get(l));
        }
    }

    public <T> void fillProperty2(IGrid<T> grid, Function<Location, T> fun) {
        grid.fill(fun);
        for (Location l : grid.locations()) {
            assertEquals(fun.apply(l), grid.get(l));
        }
    }

    @Test
    public void fillTest1() {
    	assertTrue(GetStarted.hasRead);
        for (int i = 0; i < N / 10; i++) {
            IGrid<String> grid = gridGen.generate();

            String s = strGen.generate();
            fillProperty1(grid, s);
        }
    }

    @Test
    public void fillTest2() {
    	assertTrue(GetStarted.hasRead);
        for (int i = 0; i < N / 10; i++) {
            IGrid<String> grid = gridGen.generate();

            fillProperty2(grid, (l) -> l.toString());
        }
    }

    /**
     * A set on (x1,y1) doesn't affect a get on a different (x2,y2)
     */
    public <T> void setGetIndependentProperty(IGrid<T> grid, Location l1, Location l2, T val) {
        if (!l1.equals(l2)) {
            T s = grid.get(l2);
            grid.set(l1, val);
            assertEquals(s, grid.get(l2));
        }
    }

    @Test
    public void setGetIndependentTest() {
    	assertTrue(GetStarted.hasRead);
        for (int j = 0; j < 10; j++) {
            IGrid<String> grid = gridGen.generate();
            IGenerator<Location> lGen = new LocationGenerator(grid);

            for (int i = 0; i < N; i++) {
                Location l1 = lGen.generate();
                Location l2 = lGen.generate();
                String s = strGen.generate();

                setGetIndependentProperty(grid, l1, l2, s);
            }
        }
    }

    /**
     * get(x,y) is val after set(x, y, val)
     */
    public <T> void setGetProperty(IGrid<T> grid, Location l, T val) {
        grid.set(l, val);
        assertEquals(val, grid.get(l));
    }

    /**
     * Test that get gives back the same value after set.
     */
    @Test
    public void setGetTest() {
    	assertTrue(GetStarted.hasRead);
        for (int j = 0; j < 10; j++) {
            IGrid<String> grid = gridGen.generate();
            IGenerator<Location> lGen = new LocationGenerator(grid);

            for (int i = 0; i < N; i++) {
                Location l = lGen.generate();
                String s = strGen.generate();

                setGetProperty(grid, l, s);
            }
        }
    }

    @Test
    public void uniqueLocations() {
    	assertTrue(GetStarted.hasRead);
        for (int i = 0; i < N / 10; i++) {
        }
    }

}
