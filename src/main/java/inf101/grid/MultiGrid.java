package inf101.grid;

import java.util.ArrayList;
import java.util.List;

public class MultiGrid<T> extends Grid<List<T>> implements IMultiGrid<T> {

	public MultiGrid(int width, int height) {
		super(width, height, (l) -> new ArrayList<T>());
	}

}
