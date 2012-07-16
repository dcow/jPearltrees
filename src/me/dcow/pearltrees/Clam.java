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
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

/* * * 
 * Clams make Pearls, right?..
 * Clam is a factory for Pearls.  Given the RDF resource of a Pearl,
 * the makePearl method will return a Pearl of the appropriate type.
 */
class Clam {
	protected static Pearl makePearl(RDFNode resNode) {
		Pearl p = null;
		
		// Construct the proper concrete type of Pearl..
		if (resNode != null) {
			Resource r = resNode.asResource();
			if      (r.hasProperty(RDF.type, PT.RootPearl))
				p = new RootPearl(resNode);
			else if (r.hasProperty(RDF.type, PT.PagePearl)) 
				p = new PagePearl(resNode);
			else if (r.hasProperty(RDF.type, PT.RefPearl))
				p = new RefPearl(resNode);
			else if (r.hasProperty(RDF.type, PT.AliasPearl))
				p = new AliasPearl(resNode);
		}
		return p;
	}
	
	protected static Note makeNote(RDFNode note) {
		return new Note(note);
	}
}