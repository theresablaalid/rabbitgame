package inf101.rogue101.objects;

import java.util.Comparator;

public class IItemComparator implements Comparator<IItem>{

	@Override
	public int compare(IItem o1, IItem o2) {
		if(o1==null && o2 == null)
			return 0;
		if(o1==null)
			return -1;
		if(o2==null)
			return 1;
		return Integer.compare(o1.getSize(), o2.getSize());
	}
}