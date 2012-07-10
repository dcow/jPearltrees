package me.dcow.pearltrees;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * A PearlTree is one root node and all of its child nodes, each of which
 * is a type of Pearl.  A PearlTree is generally only a few levels deep 
 * However, a PearlTree can contain references to other PearlTrees 
 * (traditionally subtrees) in the form of 
 * AliasPearls. If an AliasPearl refers to a tree defined within the RDF/XML 
 * available, it is considered a RefPearl and the referenced PearlTree can 
 * be retrieved.  In this way, the entire PearlTree graph can be traversed.
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
			pearl = Clam.makePearl(rit.nextResource());
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
		return new PearlItrList(ptPearls.iterator()); 
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
