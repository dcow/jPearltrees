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
