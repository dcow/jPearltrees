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
import com.hp.hpl.jena.vocabulary.RDF;

public abstract class Pearl extends PTNode implements Comparable<Pearl> {
	
	protected Pearl(RDFNode ptNode) {
		super(ptNode);
	}
	
	public RDFNode toRDF() {
		return pData;	
	}
	
	protected String getURI() {
		return pData.getURI();
	}
	
	public String getTitle() {
		return pData.getProperty(DC_11.title).getString();
	}
	
	public String getEntryDate() {
		return pData.getProperty(PT.inTreeSince).getString();
	}
	
	public int getLeftPos() {
		return pData.getProperty(PT.leftPos).getInt();
	}
	
	public int getRightPos() {
		return pData.getProperty(PT.rightPos).getInt();
	}
	
	public String getType() {
		return pData.getPropertyResourceValue(RDF.type).getLocalName();
	}
	
	public NoteIterator getNotes() {
		return new NoteItrRes(pData.getModel().listResourcesWithProperty(PT.Tree, getURI()));
	}
	
	// Visitor method..
	/**
	 * This method is called when you want an implementing Pearl's
	 * callback method in the PearlHandler to be called on this
	 * pearl.
	 * @param ph PearlHandler implementing a concrete pearl callback.
	 */
	abstract public void accept(PearlHandler ph);
	
	@Override
	public int compareTo(Pearl o) {
		return getLeftPos() - o.getLeftPos();
	}
}
