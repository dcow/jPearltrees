package me.dcow.pearltrees;

import java.util.Iterator;
import java.util.ListIterator;

public class PearlItrArray implements PearlIterator{

	Iterator<Pearl> it;
	
	PearlItrArray(Iterator<Pearl> pit) {
		it = pit;
	}
	
	PearlItrArray(ListIterator<Pearl> plt) {
		it = plt;
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
