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

import me.dcow.pearltrees.vocabulary.PT;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.vocabulary.DC_11;

public class Note extends PTNode {
	
	protected Note(RDFNode ptNode) {
		super(ptNode);
	}
	
	public String getCreator() {
		return pData.getProperty(DC_11.creator).getString();
		// TODO: change so that we lookup the creator and return their
		// name from the soic profile.
	}
	
	public String getDate() {
		return pData.getProperty(DC_11.date).getString();
	}

	public String getText() {
		return pData.getProperty(PT.noteText).getString();
	}

	public Pearl getParentPearl() {
		return Clam.makePearl(pData.getPropertyResourceValue(PT.parentPearl));
	}	
	
}

