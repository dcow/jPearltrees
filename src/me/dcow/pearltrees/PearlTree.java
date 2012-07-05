package me.dcow.pearltrees;

import java.util.ArrayList;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * 
 * @author David
 *
 */
public class PearlTree {

	private RootPearl			ptRoot;
	private ArrayList<Pearl> 	ptPearls;
	
	/**
	 * Construct a PearlTree for the PearlTree rooted at rootNode.
	 * @param rootNode
	 */
	protected PearlTree(Resource rootNode) {
		// Check that rootNode refers to a pt:RootNode
		// TODO: check?
		
		// Store the root of the tree..
		ptRoot = new RootPearl(rootNode);
		
		ptPearls = new ArrayList<Pearl>(ptRoot.getRightPos()/2);
		
		// Get the children..
		PearlIterator pit = ptRoot.listChildPearls();
		
		Pearl p;
		while (pit.hasNext()) {
			p = pit.next();
			ptPearls.add(p.getLeftPos(), p);
		}
		
	}
	
	public PearlIterator listSubPearls() {
		return new PearlItrArray(ptPearls);
	}
	
	protected PearlTree(RDFNode rootNode) {
		this(rootNode.asResource());
	}
	
}
