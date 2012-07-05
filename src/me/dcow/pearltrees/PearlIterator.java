package me.dcow.pearltrees;

import java.util.Iterator;

public interface PearlIterator extends Iterator<Pearl> {

	@Override
	public Pearl next();
	
	public Pearl nextPearl();
}
