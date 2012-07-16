package me.dcow.pearltrees;

import me.dcow.pearltrees.vocabulary.PT;

import com.hp.hpl.jena.rdf.model.RDFNode;

public class RootPearl extends Pearl {
	
	protected RootPearl(RDFNode ptNode) {
		super(ptNode);
	}
	
	public PearlIterator listChildPearls() {
		return new PearlItrStmt(pData.getModel().listStatements(null, PT.parentTree, pData));
	}
	
	public void accept(PearlHandler ph) {
		ph.onPearl(this);
	}
}
