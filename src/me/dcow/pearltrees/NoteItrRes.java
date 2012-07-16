/*
 * Copyright 2012 David Cowden

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
