package me.dcow.pearltrees;

import java.util.ArrayList;
import java.util.Iterator;

public class PearlItrArray implements PearlIterator{

	Iterator<Pearl> it;
	
	PearlItrArray(ArrayList<Pearl> p) {
		it = p.iterator();
	}
	
	@Override 
	public boolean hasNext() {
		return it.hasNext();
	}

	@Override 
	public void remove() {
		it.remove();
	}

	@Override 
	public Pearl next() {
		return it.next();
	}

	@Override 
	public Pearl nextPearl() {
		return it.next();
	}

}
