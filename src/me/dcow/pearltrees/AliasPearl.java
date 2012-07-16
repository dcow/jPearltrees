package me.dcow.pearltrees;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.vocabulary.RDFS;

/**
 * An AliasPearl is a Pearl that references another PearlTree.
 * The referenced tree may be a tree from any other user,
 * including the same user who the AliasPearl's parent tree 
 * came from.  If an AliasPearl referes to a direct sub-tree
 * of its parent tree, then the Alias Pearl may be further
 * classified as a RefPearl.
 * @author David
 *
 */
public class AliasPearl extends Pearl {

	protected AliasPearl(RDFNode ptNode) {
		super(ptNode);
	}
	
	
	/**
	 * Return the URI of the PearlTree this AliasPearl refers to.
	 * @return URI of the PearlTree resource referred to.
	 */
	public String getPearlURI() {
		return pData.getPropertyResourceValue(RDFS.seeAlso).getURI();
	}

	public void accept(PearlHandler ph) {
		ph.onPearl(this);
	}	
}
