package me.dcow.pearltrees;

import com.hp.hpl.jena.rdf.model.ResIterator;

class NoteItrRes implements NoteIterator {

	ResIterator it;
	
	protected NoteItrRes(ResIterator resIterator) {
		it = resIterator;
	}
	
	public boolean hasNext() {
		return it.hasNext();
	}

	public void remove() {
		it.remove();
	}

	public Note next() {
		return new Note(it.next());
	}

	public Note nextNote() {
		return new Note(it.nextResource());
	}

}
