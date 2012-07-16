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

abstract class PTNode {

	Resource pData;
	
	protected PTNode(RDFNode pearlRDFNode) {
		pData = pearlRDFNode.asResource();
	}
	
	@Override
	public String toString() {
		return pData.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		return pData.equals(obj);
	}
}
