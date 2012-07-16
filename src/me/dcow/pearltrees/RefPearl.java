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

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDFS;

/**
 * A RefPearl is a Pearl that contains a reference to another PearlTree
 * which is a direct subtree of the RefPearl's parent Tree.
 * @author David
 *
 */
public class RefPearl extends AliasPearl {

	protected RefPearl(RDFNode ptNode) {
		super(ptNode);
		
	}
	
	/*
	 * Return the RDF resource pointed to by this RefPearl.
	 */
	protected Resource getTreeRef() {
		return pData.getPropertyResourceValue(RDFS.seeAlso);
	}
	
	/**
	 * Return the PearlTree which this RefPearl points to..
	 * @return PearlTree constructed from reference contained in this RefPearl.
	 */
	public PearlTree getPearlTree() {
		return new PearlTree(pData.getPropertyResourceValue(RDFS.seeAlso));
	}

	@Override
	public void accept(PearlHandler ph) {
		ph.onPearl(this);
	}
	
}
