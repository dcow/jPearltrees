package me.dcow.pearltrees;

import java.util.ArrayList;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * 
 * @author David Cowden
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
		
		// Create an ArrayList big enough for the child Pearls..
		ptPearls = new ArrayList<Pearl>(ptRoot.getRightPos()/2);
		ptPearls.add(ptRoot.getLeftPos(), ptRoot);
		
		// Get the child Pearls..
		PearlIterator pit = ptRoot.listChildPearls();
		
		// Order the Pearls properly in the list.
		Pearl p;
		while (pit.hasNext()) {
			p = pit.next();
			ptPearls.add(p.getLeftPos(), p);
		}
		
	}
	
	protected PearlTree(RDFNode rootNode) {
		this(rootNode.asResource());
	}
	
	
	/**
	 * List the Pearls in this PearlTree.  The RootPearl is the
	 * first pearl.
	 * @return PearlIterator over the Pearls in this PearlTree
	 */
	public PearlIterator listTreePearls() {
		return new PearlItrArray(ptPearls.listIterator(1)); 
	}
	
	/**
	 * List the subPearls of the rootPearl in their order specified
	 * by the Pearltrees export.
	 * @return PearlIterator
	 */
	public PearlIterator listSubPearls() {
		return new PearlItrArray(ptPearls.listIterator(2));
	}
	
}
