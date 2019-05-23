package utils.app;

import java.util.ArrayList;
import java.util.Iterator;

public class ParametrsIterator implements Iterator<Parametr> {
	private ArrayList<Parametr> params;
	private int index = 0;
	
	public ParametrsIterator(ArrayList<Parametr> params) {
		this.params = params;
	}
	
	@Override
	public boolean hasNext() {
		return (index < params.size());
	}

	@Override
	public Parametr next() {
		Parametr current = params.get(index);
		
		if (hasNext())
			index++;

		return current;
		
	}

}
