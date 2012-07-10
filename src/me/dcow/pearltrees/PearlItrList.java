package me.dcow.pearltrees;

import java.util.Iterator;
import java.util.ListIterator;

class PearlItrList implements PearlIterator{

	Iterator<? extends Pearl> it;
	
	protected PearlItrList(Iterator<Pearl> pit) {
		it = pit;
	}
	
	protected PearlItrList(ListIterator<Pearl> plt) {
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
