package me.dcow.pearltrees;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Map;


import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.DC_11;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

public class PearlTree {
	
	// Model containing the RDF data:
	private Model mModel;
	// Root resource of the Pearltree:
	private Property mRootProp;
	
	/**
	 * 
	 * @param pearlTreeXML
	 */
	public PearlTree(InputStream pearlTreeXML) {
		// Parse RDF..
		  // Create an empty model.
		mModel = ModelFactory.createDefaultModel();
		  // Read in the RDF data from the specified XML file.
		mModel.read(pearlTreeXML, null);	
		  // Obtain the node describing the root of the Pearltree.
		mRootProp = mModel.createProperty(PT.PearlTreeNS, "rootTree");
		
		//mRoot = mModel.getResource();
	}
	
	/**
	 * 
	 * @param filenameOrURI
	 */
	public PearlTree(String filenameOrURI) {
		// Open the XML RDF file..
		this(FileManager.get().open(filenameOrURI));
	}
	
	
	/**
	 * 
	 * @param out
	 */
	public void listNameSpaces(OutputStream out) {
		PrintStream outps = new PrintStream(out);
		// Get the map so we can print the values AND the keys.
		Map<String, String> preMap = mModel.getNsPrefixMap();
		Iterator<String> it = preMap.keySet().iterator();
		String key;
		while (it.hasNext()) {
			key = it.next();
			outps.println(String.format("%10s : %s", key, preMap.get(key)));
		}
	}
	
	
	/**
	 * Return the root of the Pearltree as an RDFNode.
	 * @return
	 */
	public Pearl getRoot() {
		return new Pearl(mModel.listObjectsOfProperty(mRootProp).nextNode());
	}
	
	/**
	 * Write out the entire Pearltree rooted at rootNode in its directory like structure.
	 * The Pearltree is written with the name of each pearl on a new line.  The name is preceeded
	 * by its depth number of instances of fieldDelimiter.  Properties of each Pearl are then 
	 * printed after a fieldDelimiter after the name and are separated by more fieldDelimiters.
	 * @param out OutputStream to write the Pearltree to.
	 * @param rootNode 
	 * @param fieldDelimiter String to place between fields.
	 */
	public void printTree(OutputStream out, Pearl rootNode, String fieldDelimiter) {
		PrintStream outps = new PrintStream(out);
		if (rootNode != null)
			_printTree(outps, rootNode, "", fieldDelimiter, "", false);
	}
	
	private void _printTree(PrintStream outps, Pearl rootNode, 
							String levelPrefix, String fieldDelimiter, String entryMarker, boolean expanded) {
		// Print the rootPearl out..
		Statement rootNodeTitle = rootNode.toRDF().asResource().getProperty(DC_11.title);
		outps.println(levelPrefix + ((rootNodeTitle != null) ? 
				rootNodeTitle.getObject().toString() : ""));
		
		// Iterate over all the Pearls that are a child of the RootPearl..
		StmtIterator sit = mModel.listStatements(null, PT.parentTree, rootNode.toRDF());
		Resource childPearl;
		while (sit.hasNext()) {
			// Save the reference..
			childPearl = sit.nextStatement().getSubject();
			
			// Check if the pearl is a refPearl, if so, recursively follow:
			if (childPearl.hasProperty(RDF.type, PT.RefPearl)) {
				Statement newRoot;
				if ((newRoot = childPearl.getProperty(RDFS.seeAlso)) != null ) {
					// It is a reference or alias Pearl.
					// Follow reference Pearls recursively..
					_printTree(outps, new Pearl(newRoot.getObject()), 
							fieldDelimiter + levelPrefix, fieldDelimiter, entryMarker, expanded);
				}
			} else {
				// Write the pearl to  
				// It is a PagePearl or AliasPearl, get and Print the properties..
				writePearl(outps, childPearl, levelPrefix, fieldDelimiter, entryMarker, expanded);
			}
		}
	}
	
	private static void writePearl(PrintStream outps, Resource pearl, 
			String levelPrefix, String fieldDelimiter, String entryMarker, boolean expanded) {
		
		Statement title, identifier, leftPos, rightPos, inTreeSince;
		title 		= pearl.getProperty(DC_11.title);
		identifier  = (pearl.hasProperty(RDF.type, PT.AliasPearl)) ? 
				pearl.getProperty(RDFS.seeAlso) : pearl.getProperty(DC_11.identifier);
		leftPos		= pearl.getProperty(PT.leftPos);
		rightPos	= pearl.getProperty(PT.rightPos);
		inTreeSince = pearl.getProperty(PT.inTreeSince);
		
		// Print the property values..
		if (expanded) {
			outps.println(levelPrefix + fieldDelimiter + entryMarker + statementToPropObj(title));
			outps.println(levelPrefix + fieldDelimiter + entryMarker + statementToPropObj(identifier));
			outps.println(levelPrefix + fieldDelimiter + entryMarker + statementToPropObj(inTreeSince));
			outps.println();
		} else {
			outps.print(levelPrefix);
			outps.print(fieldDelimiter + statementToPropObj(title));
			outps.print(fieldDelimiter + statementToPropObj(identifier));
			outps.print(fieldDelimiter + statementToPropObj(inTreeSince));
			outps.println();
		}
		
		
	}
	
	private static String statementToPropObj(Statement s) {
		return (s != null) ? 
				s.getPredicate().getLocalName() + " : " + s.getObject().toString() : "";
		
		/*
		return (s != null) ? String.format("%18s : %s", 
								s.getPredicate().getLocalName(), 
								s.getObject().toString()) : "";
		*/

	}
	
	
	/**
	 * Print the entire Pearltree in it its directory-like structure in an easy to view
	 * format.
	 * @param out
	 */
	public void writePearlTree(OutputStream out) {
		PrintStream outps = new PrintStream(out);
		_printTree(outps, getRoot(), "", "\t","* ", true);
	}
	
	/**
	 * Write the Jena model representation of the Pearltrees data to the given 
	 * OutputStream.
	 * @param out
	 */
	public void writeRAW(OutputStream out) {
		mModel.write(out);
	}
	
	/**
	 * Write the Jena model representation of the RDF data to standard out.
	 */
	public void writeRAW() {
		writeRAW(System.out);
	}
}
