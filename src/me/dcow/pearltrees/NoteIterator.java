package me.dcow.pearltrees;

import java.util.Iterator;

public interface NoteIterator extends Iterator<Note> {

	@Override
	public Note next();
	
	public Note nextNote();
}
