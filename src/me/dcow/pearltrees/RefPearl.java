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
