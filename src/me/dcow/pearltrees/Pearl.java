package me.dcow.pearltrees;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.vocabulary.DC_11;

public class Pearl {
	
	protected RDFNode rNode;
	
	protected Pearl(RDFNode n) {
		rNode = n;
	}
	
	public RDFNode toRDF() {
		return rNode;	
	}
	
	public String getTitle() {
		return rNode.asResource().getProperty(DC_11.title).getString();
	}
	
	public String getEntryDate() {
		return rNode.asResource().getProperty(PT.inTreeSince).getString();
	}
	
	public int getLeftPos() {
		return rNode.asResource().getProperty(PT.leftPos).getInt();
	}
	
	public int getRightPos() {
		return rNode.asResource().getProperty(PT.rightPos).getInt();
	}
	
	
	@Override
	public String toString() {
		return rNode.toString();
	}
}
