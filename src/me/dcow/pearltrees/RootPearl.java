package me.dcow.pearltrees;

import com.hp.hpl.jena.rdf.model.RDFNode;

public class RootPearl extends Pearl {
	
	public RootPearl(RDFNode node) {
		super(node);
	}
	
	public PearlIterator listChildPearls() {
		return new PearlItrStmt(rNode.getModel().listStatements(null, PT.parentTree, rNode));
	}
}
