package me.dcow.pearltrees;

import com.hp.hpl.jena.rdf.model.RDFNode;

public class Pearl {
	
	RDFNode mNode;
	
	public Pearl() {
		
	}
	
	protected Pearl(RDFNode n) {
		mNode = n;
	}
	
	public RDFNode toRDF() {
		return mNode;	
	}
	
	@Override
	public String toString() {
		return mNode.toString();
	}
}
