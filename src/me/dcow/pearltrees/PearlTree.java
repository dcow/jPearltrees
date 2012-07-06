package me.dcow.pearltrees;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * 
 * @author David Cowden
 *
 */
public class PearlTree {

	private PriorityQueue<Pearl> ptPearls;
	
	/**
	 * Construct a PearlTree for the PearlTree rooted at rootNode.
	 * @param rootNode
	 */
	protected PearlTree(Resource treeResource) {
		// Check that treeResource refers to a pt:Tree
		// TODO: check?
		
		// Find the Pearls in the tree..
		ResIterator rit = treeResource.getModel()
						.listResourcesWithProperty(PT.parentTree, treeResource);
		
		// Create an ArrayList for the sub-pearls..
		//// TODO: figure out a way to get the size of the rit and use an ArrayList
		////       instead.  Last attempt failed...
		ptPearls = new PriorityQueue<Pearl>();
		
		Pearl pearl;
		while(rit.hasNext()) {
			pearl = Pearltrees.Clam.makePearl(rit.nextResource());
			ptPearls.add(pearl);
		}
		
	}
	
	protected PearlTree(RDFNode rootNode) {
		this(rootNode.asResource());
	}
	
	/**
	 * Return the RootPearl which represents the root of the PearlTree.
	 * @return RootPearl of the PearlTree
	 */
	public RootPearl getRootPearl() {
		return (RootPearl) ptPearls.peek();
	}
	
	/**
	 * List the Pearls in this PearlTree.  The RootPearl is the
	 * first pearl.
	 * @return PearlIterator over the Pearls in this PearlTree
	 */
	public PearlIterator listTreePearls() {
		return new PearlItrArray(ptPearls.iterator()); 
	}
	
	/**
	 * Get the underlying List of pearls in this PearlTree.  Pearls
	 * will be returned in the order, specified by the nested
	 * set values, of each pearl.
	 * @return
	 */
	public List<Pearl> getTreePearls() {
		return new ArrayList<Pearl>(ptPearls);
	}
}
