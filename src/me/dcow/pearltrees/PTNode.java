package me.dcow.pearltrees;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

abstract class PTNode {

	Resource pData;
	
	protected PTNode(RDFNode pearlRDFNode) {
		pData = pearlRDFNode.asResource();
	}
}
